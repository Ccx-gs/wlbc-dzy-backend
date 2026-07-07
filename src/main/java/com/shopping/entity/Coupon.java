package com.shopping.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("coupon")
public class Coupon {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String description;
    /** 适用范围：1=全场通用 2=指定分类 3=指定商品 */
    private Integer scopeType;
    /** 适用范围ID */
    private Long targetId;
    /** 折扣类型：1=满减券 2=折扣券 */
    private Integer discountType;
    /** 满减金额 或 折扣率（0.85=85折） */
    private BigDecimal discountValue;
    /** 折扣券最高减免上限 */
    private BigDecimal maxDiscount;
    /** 最低消费门槛 */
    private BigDecimal minOrderAmount;
    private Integer totalQuantity;
    private Integer issuedQuantity;
    /** 兑换所需积分（0=不可兑换） */
    private Integer pointsRequired;
    /** 每人限兑次数（0=不限） */
    private Integer perUserLimit;
    /** 领取后有效天数 */
    private Integer validDays;
    /** 状态：0=下架 1=上架 */
    private Integer status;
    @TableLogic
    private Integer isDeleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
