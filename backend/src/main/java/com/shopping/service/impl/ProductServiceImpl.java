package com.shopping.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shopping.entity.Product;
import com.shopping.mapper.ProductMapper;
import com.shopping.service.ProductService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    private static final String PAGE_KEY = "cache:product:page:";
    private static final String DETAIL_KEY = "cache:product:detail:";
    private static final String SEARCH_KEY = "search:";
    private static final String HOT_KEY = "hot_keywords";
    private static final String AUTOCOMPLETE_KEY = "autocomplete:product";
    private static final long CACHE_PAGE_TTL = 1;
    private static final long CACHE_DETAIL_TTL = 1;
    private static final long CACHE_NULL_TTL = 5;
    private static final long CACHE_SEARCH_TTL = 10;

    @PostConstruct
    public void initAutocomplete() {
        Boolean exists = stringRedisTemplate.hasKey(AUTOCOMPLETE_KEY);
        if (exists != null && exists) return;
        List<Product> products = lambdaQuery().eq(Product::getStatus, 1).select(Product::getName).list();
        if (products.isEmpty()) return;
        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
        for (Product p : products) {
            tuples.add(ZSetOperations.TypedTuple.of(p.getName(), 0.0));
        }
        stringRedisTemplate.opsForZSet().add(AUTOCOMPLETE_KEY, tuples);
        log.info("autocomplete loaded: {} products", tuples.size());
    }

    @Override
    public Set<String> autocomplete(String prefix) {
        if (prefix == null || prefix.trim().isEmpty()) return Set.of();
        try {
            return stringRedisTemplate.opsForZSet()
                    .rangeByLex(AUTOCOMPLETE_KEY,
                            org.springframework.data.domain.Range.closed(prefix, prefix + "\uffff"));
        } catch (Exception e) {
            log.warn("autocomplete failed", e);
            return Set.of();
        }
    }

    @Override
    public Page<Product> page(Integer categoryId, Integer pageNum, Integer pageSize) {
        String key = PAGE_KEY + categoryId + ":" + pageNum + ":" + pageSize;
        String cached = safeGet(key);
        if (cached != null) {
            return deserializePage(cached);
        }
        return queryPageAndCache(key, categoryId, pageNum, pageSize);
    }

    @Override
    public Product detail(Long id) {
        String key = DETAIL_KEY + id;
        String cached = safeGet(key);
        if (cached != null) {
            return cached.equals("{}") ? null : deserializeObj(cached, Product.class);
        }
        synchronized (this) {
            cached = safeGet(key);
            if (cached != null) {
                return cached.equals("{}") ? null : deserializeObj(cached, Product.class);
            }
            Product product = getById(id);
            if (product == null) {
                safeSet(key, "{}", CACHE_NULL_TTL, TimeUnit.MINUTES);
                return null;
            }
            safeSet(key, serialize(product), CACHE_DETAIL_TTL, TimeUnit.HOURS);
            return product;
        }
    }

    @Override
    public void update(Product product) {
        updateById(product);
        safeDelete(DETAIL_KEY + product.getId());
        deletePageCacheByScan();
    }

    @Override
    public Page<Product> search(String keyword, Integer pageNum, Integer pageSize) {
        String cacheKey = SEARCH_KEY + keyword + ":" + pageNum + ":" + pageSize;
        String cached = safeGet(cacheKey);
        if (cached != null) {
            return deserializePage(cached);
        }
        synchronized (this) {
            cached = safeGet(cacheKey);
            if (cached != null) {
                return deserializePage(cached);
            }
            try {
                stringRedisTemplate.opsForZSet().incrementScore(HOT_KEY, keyword, 1);
            } catch (Exception e) {
                log.warn("hot keyword increment failed", e);
            }
            LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
            wrapper.like(Product::getName, keyword).eq(Product::getStatus, 1);
            Page<Product> page = page(new Page<>(pageNum, pageSize), wrapper);
            safeSet(cacheKey, serialize(page), CACHE_SEARCH_TTL, TimeUnit.MINUTES);
            return page;
        }
    }

    @Override
    public Set<String> getHotKeywords() {
        try {
            Set<String> top = stringRedisTemplate.opsForZSet().reverseRange(HOT_KEY, 0, 9);
            return top != null ? new LinkedHashSet<>(top) : Set.of();
        } catch (Exception e) {
            log.warn("hot keywords query failed, returning empty", e);
            return Set.of();
        }
    }

    private String safeGet(String key) {
        try {
            return stringRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.warn("Redis read failed for key={}, degrading to DB", key, e);
            return null;
        }
    }

    private void safeSet(String key, String value, long timeout, TimeUnit unit) {
        try {
            stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
        } catch (Exception e) {
            log.warn("Redis write failed for key={}", key, e);
        }
    }

    private void safeDelete(String key) {
        try {
            stringRedisTemplate.delete(key);
        } catch (Exception e) {
            log.warn("Redis delete failed for key={}", key, e);
        }
    }

    private void deletePageCacheByScan() {
        try {
            stringRedisTemplate.execute((RedisCallback<Void>) connection -> {
                ScanOptions options = ScanOptions.scanOptions()
                        .match(PAGE_KEY + "*")
                        .count(100)
                        .build();
                try (Cursor<byte[]> cursor = connection.scan(options)) {
                    while (cursor.hasNext()) {
                        connection.del(cursor.next());
                    }
                }
                return null;
            });
        } catch (Exception e) {
            log.warn("SCAN delete page cache failed, cache will expire naturally in {}h", CACHE_PAGE_TTL, e);
        }
    }

    private Page<Product> queryPageAndCache(String key, Integer categoryId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        if (categoryId != null && categoryId > 0) {
            wrapper.eq(Product::getCategoryId, categoryId);
        }
        wrapper.eq(Product::getStatus, 1).orderByDesc(Product::getSales);
        Page<Product> page = page(new Page<>(pageNum, pageSize), wrapper);
        safeSet(key, serialize(page), CACHE_PAGE_TTL, TimeUnit.HOURS);
        return page;
    }

    private String serialize(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("序列化失败", e);
        }
    }

    private <T> T deserializeObj(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("反序列化失败", e);
        }
    }

    @SuppressWarnings("unchecked")
    private Page<Product> deserializePage(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<Page<Product>>() {});
        } catch (Exception e) {
            throw new RuntimeException("分页反序列化失败", e);
        }
    }
}
