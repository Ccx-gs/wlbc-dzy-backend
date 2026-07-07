package com.shopping.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/** 返回给前端的优惠券信息 */
@Data
public class CouponVO {
    /** 优惠券模板ID */
    private Long id;
    private String name;
    private String description;
    /** 适用范围：1=全场通用 2=指定分类 3=指定商品 */
    private Integer scopeType;
    /** 适用范围ID */
    private Long targetId;
    /** 适用范围名称（分类名/商品名） */
    private String targetName;
    /** 折扣类型：1=满减券 2=折扣券 */
    private Integer discountType;
    /** 满减金额或折扣率 */
    private BigDecimal discountValue;
    /** 折扣券最高减免上限 */
    private BigDecimal maxDiscount;
    /** 最低消费门槛 */
    private BigDecimal minOrderAmount;
    /** 兑换所需积分 */
    private Integer pointsRequired;
    /** 每人限兑次数（0=不限） */
    private Integer perUserLimit;
    /** 领取后有效天数 */
    private Integer validDays;
    /** 剩余可兑换数量 */
    private Integer remainQuantity;
    /** 当前用户已兑换次数 */
    private Integer userExchangedCount;
    /** 状态 */
    private Integer status;
}
