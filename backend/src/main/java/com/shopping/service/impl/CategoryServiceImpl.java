package com.shopping.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shopping.dto.CategoryTreeDTO;
import com.shopping.entity.Category;
import com.shopping.mapper.CategoryMapper;
import com.shopping.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    private static final String CACHE_KEY = "category:list";
    private static final long CACHE_TTL = 2;
    private static final long CACHE_NULL_TTL = 5;
    private static final String NULL_PLACEHOLDER = "[]";

    @Override
    public List<CategoryTreeDTO> getCategoryList() {
        String cached = stringRedisTemplate.opsForValue().get(CACHE_KEY);
        if (cached != null) {
            log.debug("category cache hit");
            return cached.equals(NULL_PLACEHOLDER) ? Collections.emptyList() : deserialize(cached);
        }
        // 双重检测锁：仅一个线程穿透到 MySQL，其余等待后从缓存读取
        synchronized (this) {
            cached = stringRedisTemplate.opsForValue().get(CACHE_KEY);
            if (cached != null) {
                log.debug("category cache hit after lock");
                return cached.equals(NULL_PLACEHOLDER) ? Collections.emptyList() : deserialize(cached);
            }
            log.info("category cache miss, querying DB");
            List<Category> all = lambdaQuery()
                    .eq(Category::getStatus, 1)
                    .orderByAsc(Category::getSortOrder)
                    .list();

            if (all == null || all.isEmpty()) {
                stringRedisTemplate.opsForValue().set(CACHE_KEY, NULL_PLACEHOLDER, CACHE_NULL_TTL, TimeUnit.MINUTES);
                return Collections.emptyList();
            }

            List<CategoryTreeDTO> tree = buildTree(all, 0L);
            String json = serialize(tree);
            stringRedisTemplate.opsForValue().set(CACHE_KEY, json, CACHE_TTL, TimeUnit.HOURS);
            return tree;
        }
    }

    @Override
    public void updateCategory(Category category) {
        updateById(category);
        stringRedisTemplate.delete(CACHE_KEY);
    }

    /**
     * 递归构建分类树，时间复杂度 O(n)，每个节点只处理一次。
     * 先按 parentId 分组（O(n)），再对根节点集合递归挂载子节点（O(n)），总体 O(n)。
     */
    private List<CategoryTreeDTO> buildTree(List<Category> all, Long parentId) {
        return all.stream()
                .filter(c -> c.getParentId().equals(parentId))
                .map(c -> {
                    CategoryTreeDTO dto = new CategoryTreeDTO();
                    dto.setId(c.getId());
                    dto.setName(c.getName());
                    dto.setParentId(c.getParentId());
                    dto.setSortOrder(c.getSortOrder());
                    dto.setChildren(buildTree(all, c.getId()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private String serialize(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("分类序列化失败", e);
        }
    }

    private List<CategoryTreeDTO> deserialize(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<CategoryTreeDTO>>() {});
        } catch (Exception e) {
            throw new RuntimeException("分类反序列化失败", e);
        }
    }
}
