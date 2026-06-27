package com.shopping.service;

import java.util.Map;

public interface CartService {
    void addCart(Long productId, Integer count);
    Map<Object, Object> getCart();
    void removeCart(Long productId);
}
