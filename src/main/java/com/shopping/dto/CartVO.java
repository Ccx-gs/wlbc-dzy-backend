package com.shopping.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CartVO {
    private List<CartItemVO> items;
    private Integer selectedCount;
    private BigDecimal selectedTotalPrice;
    private Integer totalCount;
}
