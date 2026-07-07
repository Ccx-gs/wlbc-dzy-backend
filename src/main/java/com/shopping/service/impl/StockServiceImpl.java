package com.shopping.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.shopping.service.StockService;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean deductStock(Long productId, Integer count) {
        String key = STOCK_KEY_PREFIX + productId;
        try {
            String stockStr = stringRedisTemplate.opsForValue().get(key);
            if (stockStr == null) {
                log.warn("Redis stock key not initialized for productId={}", productId);
                return false;
            }
            int stock = Integer.parseInt(stockStr);
            if (stock < count) {
                return false;
            }
            Long result = stringRedisTemplate.opsForValue().decrement(key, count);
            return result != null && result >= 0;
        } catch (Exception e) {
            log.warn("Redis deduct stock failed for productId={}, falling back to DB", productId, e);
            return false;
        }
    }

    @Override
    public void rollbackStock(Long productId, Integer count) {
        String key = STOCK_KEY_PREFIX + productId;
        try {
            stringRedisTemplate.opsForValue().increment(key, count);
        } catch (Exception e) {
            log.warn("Redis rollback stock failed for productId={}", productId, e);
        }
    }

    @Override
    public void increaseStock(Long productId, Integer count) {
        String key = STOCK_KEY_PREFIX + productId;
        try {
            stringRedisTemplate.opsForValue().increment(key, count);
        } catch (Exception e) {
            log.warn("Redis increase stock failed for productId={}", productId, e);
        }
    }

    @Override
    public Integer getRedisStock(Long productId) {
        String key = STOCK_KEY_PREFIX + productId;
        try {
            String stockStr = stringRedisTemplate.opsForValue().get(key);
            if (stockStr != null) {
                return Integer.parseInt(stockStr);
            }
        } catch (Exception e) {
            log.warn("Redis get stock failed for productId={}", productId, e);
        }
        return null;
    }

    @Override
    public void syncStock(Long productId, Integer stock) {
        String key = STOCK_KEY_PREFIX + productId;
        try {
            stringRedisTemplate.opsForValue().set(key, String.valueOf(stock));
        } catch (Exception e) {
            log.warn("Redis sync stock failed for productId={}", productId, e);
        }
    }

    @Override
    public void syncAllStock(Map<Long, Integer> stockMap) {
        try {
            for (Map.Entry<Long, Integer> entry : stockMap.entrySet()) {
                String key = STOCK_KEY_PREFIX + entry.getKey();
                stringRedisTemplate.opsForValue().set(key, String.valueOf(entry.getValue()));
            }
            log.info("All stock synced to Redis successfully: {} products", stockMap.size());
        } catch (Exception e) {
            log.error("Sync all stock to Redis failed", e);
            throw new RuntimeException("初始化库存缓存失败", e);
        }
    }

    private static final String STOCK_KEY_PREFIX = "product:stock:";
}
