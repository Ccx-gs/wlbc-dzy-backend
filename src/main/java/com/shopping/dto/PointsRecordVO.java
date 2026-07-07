package com.shopping.dto;

import lombok.Data;
import java.time.LocalDateTime;

/** 积分流水记录 VO */
@Data
public class PointsRecordVO {
    private Long id;
    /** 正=获得 负=消耗 */
    private Integer points;
    /** 类型：1=下单奖励 2=兑换优惠券 3=活动赠送 */
    private Integer type;
    private String description;
    private String relatedId;
    /** 变动后积分余额 */
    private Integer balanceAfter;
    private LocalDateTime createTime;
}
