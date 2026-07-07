package com.shopping.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shopping.common.Result;
import com.shopping.dto.AvailableCouponVO;
import com.shopping.dto.CouponVO;
import com.shopping.dto.UserCouponVO;
import com.shopping.service.CouponService;
import com.shopping.service.OrderItemCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    /** 积分商城：可兑换券列表 */
    @GetMapping("/coupon/store")
    public Result<List<CouponVO>> store() {
        return Result.ok(couponService.storeList());
    }

    /** 积分兑换优惠券 */
    @PostMapping("/coupon/exchange/{couponId}")
    public Result<Void> exchange(@PathVariable Long couponId) {
        couponService.exchange(couponId);
        return Result.ok();
    }

    /** 我的优惠券 */
    @GetMapping("/user/coupons")
    public Result<Page<UserCouponVO>> myCoupons(@RequestParam(defaultValue = "-1") Integer status,
                                                 @RequestParam(defaultValue = "1") Integer current,
                                                 @RequestParam(defaultValue = "10") Integer size) {
        return Result.ok(couponService.myCoupons(status, current, size));
    }

    /** 结算页：可用券列表 */
    @GetMapping("/user/coupons/available")
    public Result<List<AvailableCouponVO>> available(@RequestParam List<Long> productIds,
                                                      @RequestParam List<Integer> counts) {
        // 简化处理：只用 productId 组装，categoryId 由服务层查询
        return Result.ok(couponService.availableCoupons(List.of())); // 占位，由 best 接口代替
    }

    /** 结算页：最优券 */
    @PostMapping("/user/coupons/best")
    public Result<AvailableCouponVO> best(@RequestBody List<OrderItemCheck> items) {
        return Result.ok(couponService.bestCoupon(items));
    }

    /** 结算页：可用券列表（POST，传完整商品信息） */
    @PostMapping("/user/coupons/available")
    public Result<List<AvailableCouponVO>> availablePost(@RequestBody List<OrderItemCheck> items) {
        return Result.ok(couponService.availableCoupons(items));
    }
}
