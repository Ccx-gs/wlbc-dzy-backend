package com.shopping.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shopping.entity.Product;
import com.shopping.mapper.ProductMapper;
import com.shopping.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    private final StringRedisTemplate stringRedisTemplate;
    private static final String PAGE_KEY = "cache:product:page:";
    private static final String DETAIL_KEY = "cache:product:detail:";

    @Override
    public Page<Product> page(Integer categoryId, Integer pageNum, Integer pageSize) {
        String key = PAGE_KEY + categoryId + ":" + pageNum + ":" + pageSize;
        String cached = stringRedisTemplate.opsForValue().get(key);
        if (cached != null) {
            return JSONUtil.toBean(cached, Page.class);
        }

        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        if (categoryId != null && categoryId > 0) {
            wrapper.eq(Product::getCategoryId, categoryId);
        }
        wrapper.eq(Product::getStatus, 1).orderByDesc(Product::getSales);
        Page<Product> page = page(new Page<>(pageNum, pageSize), wrapper);

        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(page), 1, TimeUnit.HOURS);
        return page;
    }

    @Override
    public Product detail(Long id) {
        String key = DETAIL_KEY + id;
        String cached = stringRedisTemplate.opsForValue().get(key);
        if (cached != null) {
            return cached.equals("{}") ? null : JSONUtil.toBean(cached, Product.class);
        }

        Product product = getById(id);
        if (product == null) {
            stringRedisTemplate.opsForValue().set(key, "{}", 5, TimeUnit.MINUTES);
            return null;
        }
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(product), 1, TimeUnit.HOURS);
        return product;
    }

    @Override
    public void update(Product product) {
        updateById(product);
        stringRedisTemplate.delete(DETAIL_KEY + product.getId());
        deletePageCache();
    }

    private void deletePageCache() {
        var keys = stringRedisTemplate.keys(PAGE_KEY + "*");
        if (keys != null && !keys.isEmpty()) {
            stringRedisTemplate.delete(keys);
        }
    }
}
