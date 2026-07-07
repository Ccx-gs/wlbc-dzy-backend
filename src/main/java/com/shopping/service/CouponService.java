package com.shopping.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shopping.dto.AvailableCouponVO;
import com.shopping.dto.CouponVO;
import com.shopping.dto.UserCouponVO;
import com.shopping.entity.Coupon;

import java.math.BigDecimal;
import java.util.List;

public interface CouponService extends IService<Coupon> {

    /** 积分商城：可兑换券列表 */
    List<CouponVO> storeList();

    /** 积分兑换优惠券 */
    void exchange(Long couponId);

    /** 我的优惠券 */
    Page<UserCouponVO> myCoupons(Integer status, Integer current, Integer size);

    /** 结算页：所有可用券（含不可用的，带原因） */
    List<AvailableCouponVO> availableCoupons(List<OrderItemCheck> items);

    /** 结算页：最优券 */
    AvailableCouponVO bestCoupon(List<OrderItemCheck> items);

    /** 校验并核销优惠券，返回减免金额 */
    BigDecimal validateAndUse(Long userCouponId, Long userId, BigDecimal totalAmount,
                              List<OrderItemCheck> items, String orderNo);

    /** 退回优惠券 */
    void returnCoupon(Long userCouponId);
}
