package com.shopping.service;

import com.shopping.dto.CartVO;

import java.util.List;

public interface CartService {
    void addCart(Long productId, Integer count);
    CartVO getCart();
    void removeCart(Long productId);
    void updateCartCount(Long productId, Integer count);
    void batchRemoveCart(List<Long> productIds);
    void clearAllCart();
    void toggleSelect(Long productId);
    void selectAllCart(Boolean selectAll);
    CartVO getSelectedCartTotal();
}
