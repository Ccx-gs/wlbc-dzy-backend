package com.shopping.task;

import com.shopping.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 订单超时自动取消定时任务
 * 每 60 秒扫描一次：status=0 且 createTime < 当前-15 分钟 的订单 → status=4
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderTimeoutTask {

    private final OrderService orderService;

    @Scheduled(fixedDelay = 60_000L)
    public void autoCancelExpiredOrders() {
        try {
            int count = orderService.autoCancelExpiredOrders();
            if (count > 0) {
                log.info("超时自动取消订单：{} 笔", count);
            }
        } catch (Exception e) {
            log.error("自动取消超时订单失败", e);
        }
    }
}
