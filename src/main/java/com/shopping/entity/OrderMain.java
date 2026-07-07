package com.shopping.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("order_main")
public class OrderMain {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderNo;
    private Long userId;
    private BigDecimal totalAmount;
    private BigDecimal payAmount;
    private Integer status;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private String receiverFullAddr;
    private Double lon;
    private Double lat;
    private String remark;
    private LocalDateTime payTime;
    /** 使用的用户优惠券ID */
    private Long couponId;
    /** 优惠券减免金额 */
    private BigDecimal discountAmount;
    /** 本单获得的积分 */
    private Integer pointsEarned;
    @TableLogic
    private Integer isDeleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
