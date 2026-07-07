package com.shopping.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/** 结算页可用券（含减免金额计算结果） */
@Data
public class AvailableCouponVO extends UserCouponVO {
    /** 本单使用该券可减免的金额 */
    private BigDecimal discountAmount;
    /** 是否可用（金额/商品满足条件） */
    private Boolean usable;
    /** 不可用原因 */
    private String unusableReason;
}
