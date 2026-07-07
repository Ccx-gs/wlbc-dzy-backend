package com.shopping.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CartItemVO {
    private Long productId;
    private String productName;
    private String productImage;
    private BigDecimal unitPrice;
    private Integer count;
    private Boolean selected;
    private BigDecimal subtotal;
    private Long addTime;

    public void calcSubtotal() {
        if (unitPrice != null && count != null) {
            this.subtotal = unitPrice.multiply(BigDecimal.valueOf(count));
        }
    }
}
