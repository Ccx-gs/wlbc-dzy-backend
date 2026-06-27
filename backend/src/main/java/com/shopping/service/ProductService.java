package com.shopping.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shopping.entity.Product;

public interface ProductService extends IService<Product> {
    Page<Product> page(Integer categoryId, Integer pageNum, Integer pageSize);
    Product detail(Long id);
    void update(Product product);
}
