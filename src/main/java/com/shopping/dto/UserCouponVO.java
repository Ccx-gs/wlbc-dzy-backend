package com.shopping.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/** 用户持有的优惠券 */
@Data
public class UserCouponVO {
    /** user_coupon 主键 */
    private Long id;
    /** 优惠券模板ID */
    private Long couponId;
    private String name;
    private String description;
    /** 适用范围：1=全场通用 2=指定分类 3=指定商品 */
    private Integer scopeType;
    private Long targetId;
    private String targetName;
    /** 折扣类型：1=满减券 2=折扣券 */
    private Integer discountType;
    private BigDecimal discountValue;
    private BigDecimal maxDiscount;
    private BigDecimal minOrderAmount;
    /** 状态：0=未使用 1=已使用 2=已过期 */
    private Integer status;
    private LocalDateTime acquireTime;
    private LocalDateTime expireTime;
    private LocalDateTime useTime;
    private String orderNo;
}
