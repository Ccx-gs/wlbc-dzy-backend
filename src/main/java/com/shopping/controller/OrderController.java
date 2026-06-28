package com.shopping.controller;

import com.shopping.common.Result;
import com.shopping.dto.OrderCreateDTO;
import com.shopping.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Result<String> createOrder(@Valid @RequestBody OrderCreateDTO dto) {
        return Result.ok(orderService.createOrder(dto));
    }
}
