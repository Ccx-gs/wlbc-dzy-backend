package com.shopping.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressDTO {
    private Long id;
    @NotBlank(message = "收货人姓名不能为空")
    private String receiverName;
    @NotBlank(message = "收货人手机号不能为空")
    private String receiverPhone;
    private String province;
    private String city;
    private String district;
    @NotBlank(message = "详细地址不能为空")
    private String detailAddr;
    private Double longitude;
    private Double latitude;
    private Integer isDefault;
}
