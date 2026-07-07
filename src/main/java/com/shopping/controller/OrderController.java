package com.shopping.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shopping.common.Result;
import com.shopping.dto.OrderCreateDTO;
import com.shopping.dto.OrderListQuery;
import com.shopping.dto.OrderVO;
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

    /** 提交订单：返回订单号字符串 */
    @PostMapping
    public Result<String> createOrder(@Valid @RequestBody OrderCreateDTO dto) {
        return Result.ok(orderService.createOrder(dto));
    }

    /** 我的订单列表（分页 + 状态筛选） */
    @GetMapping("/list")
    public Result<Page<OrderVO>> listOrders(OrderListQuery query) {
        return Result.ok(orderService.listOrders(query));
    }

    /** 订单详情（含商品项） */
    @GetMapping("/{orderNo}")
    public Result<OrderVO> getOrderDetail(@PathVariable String orderNo) {
        return Result.ok(orderService.getOrderDetail(orderNo));
    }

    /** 模拟支付：status 0 -> 1 */
    @PutMapping("/{orderNo}/pay")
    public Result<Void> payOrder(@PathVariable String orderNo) {
        orderService.payOrder(orderNo);
        return Result.ok();
    }

    /** 取消订单：status 0 -> 4 */
    @PutMapping("/{orderNo}/cancel")
    public Result<Void> cancelOrder(@PathVariable String orderNo) {
        orderService.cancelOrder(orderNo);
        return Result.ok();
    }

    /** 确认收货：status 2 -> 3 */
    @PutMapping("/{orderNo}/confirm")
    public Result<Void> confirmReceive(@PathVariable String orderNo) {
        orderService.confirmReceive(orderNo);
        return Result.ok();
    }
}
