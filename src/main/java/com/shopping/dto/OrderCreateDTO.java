package com.shopping.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class OrderCreateDTO {
    @NotBlank(message = "收货人姓名不能为空")
    private String receiver_name;
    @NotBlank(message = "收货人手机号不能为空")
    private String receiver_phone;
    @NotBlank(message = "收货地址不能为空")
    private String receiver_address;
    private String remark;
    @NotEmpty(message = "订单商品不能为空")
    @Valid
    private List<OrderItemDTO> items;
}
