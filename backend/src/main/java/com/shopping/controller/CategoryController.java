package com.shopping.controller;

import com.shopping.common.Result;
import com.shopping.dto.CategoryTreeDTO;
import com.shopping.entity.Category;
import com.shopping.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/list")
    public Result<List<CategoryTreeDTO>> getCategoryList() {
        return Result.ok(categoryService.getCategoryList());
    }

    @PutMapping
    public Result<Void> updateCategory(@RequestBody Category category) {
        categoryService.updateCategory(category);
        return Result.ok();
    }
}
