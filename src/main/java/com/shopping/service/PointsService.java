package com.shopping.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shopping.dto.PointsRecordVO;

public interface PointsService {

    /** 获取当前用户积分 */
    int getPoints();

    /** 获取累计获得积分 */
    int getTotalEarned();

    /** 积分流水记录 */
    Page<PointsRecordVO> records(Integer current, Integer size);

    /** 发放积分（订单完成时调用） */
    void awardPoints(Long userId, String orderNo, int points);

    /** 消耗积分（兑换优惠券时调用） */
    void spendPoints(Long userId, int points, String description, String relatedId);
}
