package com.shopping.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shopping.entity.Product;

import java.util.List;
import java.util.Set;

public interface ProductService extends IService<Product> {
    Page<Product> page(Integer categoryId, Integer pageNum, Integer pageSize);
    Product detail(Long id);
    void update(Product product);
    Page<Product> search(String keyword, Integer pageNum, Integer pageSize);
    Set<String> getHotKeywords();
    Set<String> autocomplete(String prefix);
}
