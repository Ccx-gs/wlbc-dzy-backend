package com.shopping.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("points_record")
public class PointsRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    /** 正=获得 负=消耗 */
    private Integer points;
    /** 类型：1=下单奖励 2=兑换优惠券 3=活动赠送 */
    private Integer type;
    private String description;
    /** 关联订单号或兑换记录ID */
    private String relatedId;
    /** 变动后积分余额 */
    private Integer balanceAfter;
    private LocalDateTime createTime;
}
