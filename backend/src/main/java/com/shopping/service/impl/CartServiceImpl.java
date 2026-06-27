package com.shopping.service.impl;

import com.shopping.common.UserHolder;
import com.shopping.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final StringRedisTemplate stringRedisTemplate;
    private static final String CART_PREFIX = "cart:";

    @Override
    public void addCart(Long productId, Integer count) {
        Long userId = UserHolder.getUser().getId();
        stringRedisTemplate.opsForHash().increment(CART_PREFIX + userId, productId.toString(), count);
    }

    @Override
    public Map<Object, Object> getCart() {
        Long userId = UserHolder.getUser().getId();
        return stringRedisTemplate.opsForHash().entries(CART_PREFIX + userId);
    }

    @Override
    public void removeCart(Long productId) {
        Long userId = UserHolder.getUser().getId();
        stringRedisTemplate.opsForHash().delete(CART_PREFIX + userId, productId.toString());
    }
}
