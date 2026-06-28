package com.shopping.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shopping.common.Result;
import com.shopping.entity.Product;
import com.shopping.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/page")
    public Result<Page<Product>> page(@RequestParam(required = false) Integer category_id,
                                       @RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer size) {
        return Result.ok(productService.page(category_id, page, size));
    }

    @GetMapping("/detail/{id}")
    public Result<Product> detail(@PathVariable Long id) {
        return Result.ok(productService.detail(id));
    }

    @PutMapping
    public Result<Void> update(@RequestBody Product product) {
        productService.update(product);
        return Result.ok();
    }

    @GetMapping("/search")
    public Result<Page<Product>> search(@RequestParam String keyword,
                                         @RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "10") Integer size) {
        return Result.ok(productService.search(keyword, page, size));
    }

    @GetMapping("/hot-keywords")
    public Result<Set<String>> getHotKeywords() {
        return Result.ok(productService.getHotKeywords());
    }

    @GetMapping("/autocomplete")
    public Result<Set<String>> autocomplete(@RequestParam String prefix) {
        return Result.ok(productService.autocomplete(prefix));
    }
}
