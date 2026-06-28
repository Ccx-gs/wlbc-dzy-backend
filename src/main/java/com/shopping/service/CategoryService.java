package com.shopping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shopping.dto.CategoryTreeDTO;
import com.shopping.entity.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
    List<CategoryTreeDTO> getCategoryList();
    void updateCategory(Category category);
}
