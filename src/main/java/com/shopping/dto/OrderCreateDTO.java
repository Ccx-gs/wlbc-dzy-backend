package com.shopping.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class OrderCreateDTO {
    @NotNull(message = "收货地址ID不能为空")
    private Long addressId;
    private String remark;
    /** 使用的用户优惠券ID（可选） */
    private Long couponId;
    @NotEmpty(message = "订单商品不能为空")
    @Valid
    private List<OrderItemDTO> items;
}
