package com.shopping.dto;

import lombok.Data;

/**
 * 订单列表分页查询参数
 * status = -1 表示查询全部
 */
@Data
public class OrderListQuery {
    private Integer status = -1;
    private Integer current = 1;
    private Integer size = 10;
}
