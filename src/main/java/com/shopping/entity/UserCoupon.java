package com.shopping.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("user_coupon")
public class UserCoupon {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long couponId;
    /** 状态：0=未使用 1=已使用 2=已过期 */
    private Integer status;
    private LocalDateTime acquireTime;
    private LocalDateTime expireTime;
    private LocalDateTime useTime;
    /** 使用的订单号 */
    private String orderNo;
    @TableLogic
    private Integer isDeleted;
    private LocalDateTime createTime;
}
