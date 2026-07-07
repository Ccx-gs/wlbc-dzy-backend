package com.shopping.service;

import lombok.Data;
import java.math.BigDecimal;

/** 结算时传递的商品信息 */
@Data
public class OrderItemCheck {
    private Long productId;
    private Long categoryId;
    private BigDecimal price;
    private Integer count;
}
