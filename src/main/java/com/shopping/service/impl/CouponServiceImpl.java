package com.shopping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shopping.common.BusinessException;
import com.shopping.common.UserHolder;
import com.shopping.dto.AvailableCouponVO;
import com.shopping.dto.CouponVO;
import com.shopping.dto.UserCouponVO;
import com.shopping.dto.UserDTO;
import com.shopping.entity.Coupon;
import com.shopping.entity.UserCoupon;
import com.shopping.mapper.CouponMapper;
import com.shopping.mapper.UserCouponMapper;
import com.shopping.service.CouponService;
import com.shopping.service.OrderItemCheck;
import com.shopping.service.PointsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements CouponService {

    private final UserCouponMapper userCouponMapper;
    private final PointsService pointsService;

    private Long getUserId() {
        UserDTO user = UserHolder.getUser();
        if (user == null || user.getId() == null) {
            throw new BusinessException(401, "请先登录后操作");
        }
        return user.getId();
    }

    @Override
    public List<CouponVO> storeList() {
        Long userId = getUserId();
        List<Coupon> coupons = lambdaQuery()
                .eq(Coupon::getStatus, 1)
                .gt(Coupon::getPointsRequired, 0)
                .apply("issued_quantity < total_quantity")
                .orderByAsc(Coupon::getPointsRequired)
                .list();

        List<CouponVO> result = new ArrayList<>();
        for (Coupon c : coupons) {
            CouponVO vo = new CouponVO();
            copyCouponFields(c, vo);
            vo.setRemainQuantity(c.getTotalQuantity() - c.getIssuedQuantity());
            // 当前用户已兑换次数
            Long count = userCouponMapper.selectCount(new LambdaQueryWrapper<UserCoupon>()
                    .eq(UserCoupon::getUserId, userId)
                    .eq(UserCoupon::getCouponId, c.getId()));
            vo.setUserExchangedCount(count != null ? count.intValue() : 0);
            result.add(vo);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void exchange(Long couponId) {
        Long userId = getUserId();
        Coupon coupon = getById(couponId);
        if (coupon == null || coupon.getStatus() != 1) {
            throw new BusinessException(400, "优惠券不存在或已下架");
        }
        if (coupon.getPointsRequired() <= 0) {
            throw new BusinessException(400, "该优惠券不支持积分兑换");
        }
        if (coupon.getIssuedQuantity() >= coupon.getTotalQuantity()) {
            throw new BusinessException(400, "优惠券已被兑完");
        }
        // 检查每人限兑次数
        if (coupon.getPerUserLimit() > 0) {
            Long count = userCouponMapper.selectCount(new LambdaQueryWrapper<UserCoupon>()
                    .eq(UserCoupon::getUserId, userId)
                    .eq(UserCoupon::getCouponId, couponId));
            if (count != null && count >= coupon.getPerUserLimit()) {
                throw new BusinessException(400, "已达每人限兑次数");
            }
        }
        // 检查积分
        int currentPoints = pointsService.getPoints();
        if (currentPoints < coupon.getPointsRequired()) {
            throw new BusinessException(400, "积分不足");
        }

        // 扣积分
        pointsService.spendPoints(userId, coupon.getPointsRequired(),
                "兑换优惠券「" + coupon.getName() + "」", "exchange:" + couponId);

        // 发券
        UserCoupon uc = new UserCoupon();
        uc.setUserId(userId);
        uc.setCouponId(couponId);
        uc.setStatus(0);
        uc.setAcquireTime(LocalDateTime.now());
        uc.setExpireTime(LocalDateTime.now().plusDays(coupon.getValidDays()));
        userCouponMapper.insert(uc);

        // 更新发行数量
        Coupon update = new Coupon();
        update.setId(couponId);
        update.setIssuedQuantity(coupon.getIssuedQuantity() + 1);
        updateById(update);

        log.info("用户 {} 兑换优惠券 {}: {}", userId, couponId, coupon.getName());
    }

    @Override
    public Page<UserCouponVO> myCoupons(Integer status, Integer current, Integer size) {
        Long userId = getUserId();
        LambdaQueryWrapper<UserCoupon> wrapper = new LambdaQueryWrapper<UserCoupon>()
                .eq(UserCoupon::getUserId, userId)
                .orderByDesc(UserCoupon::getCreateTime);
        if (status != null && status >= 0) {
            wrapper.eq(UserCoupon::getStatus, status);
        }
        Page<UserCoupon> page = userCouponMapper.selectPage(new Page<>(current, size), wrapper);

        // 批量查 coupon 信息
        List<Long> couponIds = page.getRecords().stream().map(UserCoupon::getCouponId).distinct().toList();
        final Map<Long, Coupon> couponMap;
        if (couponIds.isEmpty()) {
            couponMap = Collections.emptyMap();
        } else {
            couponMap = listByIds(couponIds).stream().collect(Collectors.toMap(Coupon::getId, c -> c));
        }

        Page<UserCouponVO> result = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        result.setRecords(page.getRecords().stream().map(uc -> {
            Coupon c = couponMap.get(uc.getCouponId());
            UserCouponVO vo = new UserCouponVO();
            vo.setId(uc.getId());
            vo.setCouponId(uc.getCouponId());
            vo.setStatus(uc.getStatus());
            vo.setAcquireTime(uc.getAcquireTime());
            vo.setExpireTime(uc.getExpireTime());
            vo.setUseTime(uc.getUseTime());
            vo.setOrderNo(uc.getOrderNo());
            if (c != null) {
                vo.setName(c.getName());
                vo.setDescription(c.getDescription());
                vo.setScopeType(c.getScopeType());
                vo.setTargetId(c.getTargetId());
                vo.setDiscountType(c.getDiscountType());
                vo.setDiscountValue(c.getDiscountValue());
                vo.setMaxDiscount(c.getMaxDiscount());
                vo.setMinOrderAmount(c.getMinOrderAmount());
            }
            return vo;
        }).toList());
        return result;
    }

    @Override
    public List<AvailableCouponVO> availableCoupons(List<OrderItemCheck> items) {
        Long userId = getUserId();
        List<UserCouponVO> userCoupons = myCoupons(0, 1, 1000).getRecords(); // 所有未使用券
        return evaluateCoupons(userCoupons, items);
    }

    @Override
    public AvailableCouponVO bestCoupon(List<OrderItemCheck> items) {
        List<AvailableCouponVO> all = availableCoupons(items);
        return all.stream()
                .filter(a -> a.getUsable() != null && a.getUsable())
                .max(Comparator.comparing(AvailableCouponVO::getDiscountAmount))
                .orElse(null);
    }

    /** 计算哪些券可用，分别减免多少 */
    private List<AvailableCouponVO> evaluateCoupons(List<UserCouponVO> userCoupons, List<OrderItemCheck> items) {
        BigDecimal totalAmount = items.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 按分类汇总
        Map<Long, BigDecimal> categoryTotals = new HashMap<>();
        for (OrderItemCheck item : items) {
            if (item.getCategoryId() != null) {
                categoryTotals.merge(item.getCategoryId(),
                        item.getPrice().multiply(BigDecimal.valueOf(item.getCount())), BigDecimal::add);
            }
        }
        // 按商品汇总
        Map<Long, BigDecimal> productTotals = new HashMap<>();
        for (OrderItemCheck item : items) {
            productTotals.put(item.getProductId(),
                    item.getPrice().multiply(BigDecimal.valueOf(item.getCount())));
        }

        List<AvailableCouponVO> result = new ArrayList<>();
        for (UserCouponVO uc : userCoupons) {
            AvailableCouponVO avo = new AvailableCouponVO();
            copyFromUserCouponVO(uc, avo);

            BigDecimal applicableAmount = switch (uc.getScopeType()) {
                case 1 -> totalAmount; // 全场
                case 2 -> categoryTotals.getOrDefault(uc.getTargetId(), BigDecimal.ZERO); // 指定分类
                case 3 -> productTotals.getOrDefault(uc.getTargetId(), BigDecimal.ZERO); // 指定商品
                default -> BigDecimal.ZERO;
            };

            if (applicableAmount.compareTo(uc.getMinOrderAmount()) < 0) {
                avo.setUsable(false);
                avo.setDiscountAmount(BigDecimal.ZERO);
                String scopeLabel = switch (uc.getScopeType()) {
                    case 2 -> "指定分类商品";
                    case 3 -> "指定商品";
                    default -> "商品";
                };
                avo.setUnusableReason(String.format("%s金额 ¥%.2f 未满 ¥%.2f",
                        scopeLabel, applicableAmount, uc.getMinOrderAmount()));
            } else {
                avo.setUsable(true);
                avo.setDiscountAmount(calcDiscount(uc, applicableAmount));
            }
            result.add(avo);
        }
        // 可用在前，不可用在后；可用按减免金额降序
        result.sort((a, b) -> {
            boolean au = Boolean.TRUE.equals(a.getUsable());
            boolean bu = Boolean.TRUE.equals(b.getUsable());
            if (au != bu) return au ? -1 : 1;
            if (au) return b.getDiscountAmount().compareTo(a.getDiscountAmount());
            return 0;
        });
        return result;
    }

    /** 计算减免金额 */
    private BigDecimal calcDiscount(UserCouponVO uc, BigDecimal amount) {
        if (uc.getDiscountType() == 1) {
            // 满减券
            return uc.getDiscountValue();
        } else {
            // 折扣券
            BigDecimal discount = amount.multiply(BigDecimal.ONE.subtract(uc.getDiscountValue()));
            if (uc.getMaxDiscount() != null && discount.compareTo(uc.getMaxDiscount()) > 0) {
                discount = uc.getMaxDiscount();
            }
            return discount.setScale(2, RoundingMode.DOWN);
        }
    }

    private void copyCouponFields(Coupon src, CouponVO dst) {
        dst.setId(src.getId());
        dst.setName(src.getName());
        dst.setDescription(src.getDescription());
        dst.setScopeType(src.getScopeType());
        dst.setTargetId(src.getTargetId());
        dst.setDiscountType(src.getDiscountType());
        dst.setDiscountValue(src.getDiscountValue());
        dst.setMaxDiscount(src.getMaxDiscount());
        dst.setMinOrderAmount(src.getMinOrderAmount());
        dst.setPointsRequired(src.getPointsRequired());
        dst.setPerUserLimit(src.getPerUserLimit());
        dst.setValidDays(src.getValidDays());
        dst.setStatus(src.getStatus());
    }

    private void copyFromUserCouponVO(UserCouponVO src, AvailableCouponVO dst) {
        dst.setId(src.getId());
        dst.setCouponId(src.getCouponId());
        dst.setName(src.getName());
        dst.setDescription(src.getDescription());
        dst.setScopeType(src.getScopeType());
        dst.setTargetId(src.getTargetId());
        dst.setTargetName(src.getTargetName());
        dst.setDiscountType(src.getDiscountType());
        dst.setDiscountValue(src.getDiscountValue());
        dst.setMaxDiscount(src.getMaxDiscount());
        dst.setMinOrderAmount(src.getMinOrderAmount());
        dst.setStatus(src.getStatus());
        dst.setAcquireTime(src.getAcquireTime());
        dst.setExpireTime(src.getExpireTime());
        dst.setUseTime(src.getUseTime());
        dst.setOrderNo(src.getOrderNo());
    }

    /** 校验用户优惠券是否可用，并返回减免金额 */
    public BigDecimal validateAndUse(Long userCouponId, Long userId, BigDecimal totalAmount,
                                      List<OrderItemCheck> items, String orderNo) {
        UserCoupon uc = userCouponMapper.selectById(userCouponId);
        if (uc == null || !uc.getUserId().equals(userId)) {
            throw new BusinessException(400, "优惠券不存在");
        }
        if (uc.getStatus() != 0) {
            throw new BusinessException(400, "优惠券已使用或已过期");
        }
        if (uc.getExpireTime().isBefore(LocalDateTime.now())) {
            throw new BusinessException(400, "优惠券已过期");
        }
        Coupon coupon = getById(uc.getCouponId());
        if (coupon == null) {
            throw new BusinessException(400, "优惠券模板不存在");
        }

        // 判断适用范围
        BigDecimal applicableAmount = getApplicableAmount(items, coupon.getScopeType(), coupon.getTargetId());
        if (applicableAmount.compareTo(coupon.getMinOrderAmount()) < 0) {
            throw new BusinessException(400, "未满足优惠券使用门槛");
        }

        UserCouponVO ucvo = new UserCouponVO();
        ucvo.setDiscountType(coupon.getDiscountType());
        ucvo.setDiscountValue(coupon.getDiscountValue());
        ucvo.setMaxDiscount(coupon.getMaxDiscount());
        BigDecimal discount = calcDiscount(ucvo, applicableAmount);

        // 标记已使用
        uc.setStatus(1);
        uc.setUseTime(LocalDateTime.now());
        uc.setOrderNo(orderNo);
        userCouponMapper.updateById(uc);

        return discount;
    }

    /** 退回优惠券 */
    public void returnCoupon(Long userCouponId) {
        if (userCouponId == null) return;
        UserCoupon uc = userCouponMapper.selectById(userCouponId);
        if (uc != null && uc.getStatus() == 1) {
            uc.setStatus(0);
            uc.setUseTime(null);
            uc.setOrderNo(null);
            userCouponMapper.updateById(uc);
        }
    }

    private BigDecimal getApplicableAmount(List<OrderItemCheck> items, Integer scopeType, Long targetId) {
        return switch (scopeType) {
            case 1 -> items.stream()
                    .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getCount())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            case 2 -> items.stream()
                    .filter(i -> i.getCategoryId() != null && i.getCategoryId().equals(targetId))
                    .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getCount())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            case 3 -> items.stream()
                    .filter(i -> i.getProductId().equals(targetId))
                    .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getCount())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            default -> BigDecimal.ZERO;
        };
    }
}
