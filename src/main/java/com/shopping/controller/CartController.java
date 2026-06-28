package com.shopping.controller;

import com.shopping.common.Result;
import com.shopping.dto.CartVO;
import com.shopping.service.CartService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Validated
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public Result<Void> addCart(@RequestParam @NotNull @Min(1) Long product_id,
                                 @RequestParam @NotNull @Min(1) Integer count) {
        cartService.addCart(product_id, count);
        return Result.ok();
    }

    @GetMapping
    public Result<CartVO> getCart() {
        return Result.ok(cartService.getCart());
    }

    @DeleteMapping("/remove")
    public Result<Void> removeCart(@RequestParam @NotNull @Min(1) Long product_id) {
        cartService.removeCart(product_id);
        return Result.ok();
    }

    @PutMapping("/count")
    public Result<Void> updateCartCount(@RequestParam @NotNull @Min(1) Long product_id,
                                         @RequestParam @NotNull @Min(1) Integer count) {
        cartService.updateCartCount(product_id, count);
        return Result.ok();
    }

    @DeleteMapping("/batch-remove")
    public Result<Void> batchRemoveCart(@RequestParam @NotEmpty List<Long> product_ids) {
        cartService.batchRemoveCart(product_ids);
        return Result.ok();
    }

    @DeleteMapping("/clear")
    public Result<Void> clearAllCart() {
        cartService.clearAllCart();
        return Result.ok();
    }

    @PutMapping("/toggle-select")
    public Result<Void> toggleSelect(@RequestParam @NotNull @Min(1) Long product_id) {
        cartService.toggleSelect(product_id);
        return Result.ok();
    }

    @PutMapping("/select-all")
    public Result<Void> selectAllCart(@RequestParam(defaultValue = "true") Boolean select_all) {
        cartService.selectAllCart(select_all);
        return Result.ok();
    }

    @GetMapping("/selected-total")
    public Result<CartVO> getSelectedCartTotal() {
        return Result.ok(cartService.getSelectedCartTotal());
    }
}
