package com.shopping.task;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shopping.entity.UserCoupon;
import com.shopping.mapper.UserCouponMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponExpireTask {

    private final UserCouponMapper userCouponMapper;

    @Scheduled(cron = "0 0 2 * * ?")
    public void expireCoupons() {
        UpdateWrapper<UserCoupon> wrapper = new UpdateWrapper<>();
        wrapper.eq("status", 0)
                .lt("expire_time", LocalDateTime.now())
                .set("status", 2);
        boolean ok = userCouponMapper.update(null, wrapper) > 0;
        if (ok) {
            log.info("过期优惠券已处理");
        }
    }
}
