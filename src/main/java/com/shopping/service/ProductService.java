package com.shopping.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shopping.entity.Product;

import java.math.BigDecimal;
import java.util.Set;

public interface ProductService extends IService<Product> {
    Page<Product> page(Integer categoryId, Integer pageNum, Integer pageSize, BigDecimal minPrice, BigDecimal maxPrice);
    Product detail(Long id);
    void update(Product product);
    Page<Product> search(String keyword, Integer pageNum, Integer pageSize, BigDecimal minPrice, BigDecimal maxPrice);
    Set<String> getHotKeywords();
    Set<String> autocomplete(String prefix);
}
