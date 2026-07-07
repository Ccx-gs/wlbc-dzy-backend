package com.shopping.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shopping.dto.OrderCreateDTO;
import com.shopping.dto.OrderListQuery;
import com.shopping.dto.OrderVO;

public interface OrderService {
    /** 创建订单，返回订单号 */
    String createOrder(OrderCreateDTO dto);

    /** 我的订单分页查询 */
    Page<OrderVO> listOrders(OrderListQuery query);

    /** 订单详情（含商品项） */
    OrderVO getOrderDetail(String orderNo);

    /** 支付订单：status 0 -> 1 */
    void payOrder(String orderNo);

    /** 取消订单：status 0 -> 4 */
    void cancelOrder(String orderNo);

    /** 确认收货：status 2 -> 3 */
    void confirmReceive(String orderNo);

    /** 自动取消超时未支付订单（定时任务调用） */
    int autoCancelExpiredOrders();
}
