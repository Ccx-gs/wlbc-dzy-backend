package com.shopping.controller;

import com.shopping.common.Result;
import com.shopping.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public Result<Void> addCart(@RequestParam Long product_id, @RequestParam Integer count) {
        cartService.addCart(product_id, count);
        return Result.ok();
    }

    @GetMapping
    public Result<Map<Object, Object>> getCart() {
        return Result.ok(cartService.getCart());
    }

    @DeleteMapping("/remove")
    public Result<Void> removeCart(@RequestParam Long product_id) {
        cartService.removeCart(product_id);
        return Result.ok();
    }
}
