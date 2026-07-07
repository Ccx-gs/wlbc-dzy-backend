package com.shopping.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shopping.common.BusinessException;
import com.shopping.common.UserHolder;
import com.shopping.dto.OrderCreateDTO;
import com.shopping.dto.OrderItemDTO;
import com.shopping.dto.OrderListQuery;
import com.shopping.dto.OrderVO;
import com.shopping.dto.UserDTO;
import com.shopping.entity.Address;
import com.shopping.entity.OrderItem;
import com.shopping.entity.OrderMain;
import com.shopping.entity.Product;
import com.shopping.mapper.AddressMapper;
import com.shopping.mapper.OrderItemMapper;
import com.shopping.mapper.OrderMainMapper;
import com.shopping.service.CouponService;
import com.shopping.service.OrderItemCheck;
import com.shopping.service.OrderService;
import com.shopping.service.PointsService;
import com.shopping.service.ProductService;
import com.shopping.service.StockService;

import cn.hutool.core.util.IdUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMainMapper, OrderMain> implements OrderService {

    private final OrderItemMapper orderItemMapper;
    private final ProductService productService;
    private final StockService stockService;
    private final AddressMapper addressMapper;
    private final CouponService couponService;
    private final PointsService pointsService;

    /** 订单状态：0=待付款 3=已完成 4=已取消 */
    private static final int STATUS_PENDING_PAY = 0;
    private static final int STATUS_COMPLETED = 3;
    private static final int STATUS_CANCELLED = 4;

    /** 支付有效期：30 分钟 */
    private static final long PAY_EXPIRE_MINUTES = 30;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createOrder(OrderCreateDTO dto) {
        UserDTO user = UserHolder.getUser();
        if (user == null || user.getId() == null) {
            throw new BusinessException(401, "请先登录后操作");
        }
        Long userId = user.getId();
        String orderNo = "O" + IdUtil.getSnowflake(1, 1).nextIdStr();
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();
        List<Long> deductedProductIds = new ArrayList<>();

        try {
            for (OrderItemDTO item : dto.getItems()) {
                Product product = productService.getById(item.getProduct_id());
                if (product == null || product.getStatus() != 1) {
                    throw new BusinessException(400, "商品 " + item.getProduct_id() + " 不存在或已下架");
                }
                // Redis原子预扣库存
                boolean redisDeducted = stockService.deductStock(product.getId(), item.getCount());
                if (!redisDeducted) {
                    throw new BusinessException(400, "商品 " + product.getName() + " 库存不足");
                }
                deductedProductIds.add(product.getId());

                // MySQL乐观锁二次扣减
                UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
                updateWrapper.setSql("stock = stock - " + item.getCount() + ", version = version + 1")
                        .eq("id", product.getId())
                        .ge("stock", item.getCount())
                        .eq("version", product.getVersion());
                boolean deducted = productService.update(updateWrapper);
                if (!deducted) {
                    stockService.rollbackStock(product.getId(), item.getCount());
                    throw new BusinessException(400, "商品 " + product.getName() + " 库存不足");
                }

                BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(item.getCount()));
                totalAmount = totalAmount.add(itemTotal);

                OrderItem orderItem = new OrderItem();
                orderItem.setProductId(product.getId());
                orderItem.setProductName(product.getName());
                orderItem.setProductImage(product.getMainImage());
                orderItem.setUnitPrice(product.getPrice());
                orderItem.setQuantity(item.getCount());
                orderItem.setTotalPrice(itemTotal);
                orderItems.add(orderItem);
            }

            // 处理优惠券
            BigDecimal discountAmount = BigDecimal.ZERO;
            if (dto.getCouponId() != null) {
                // 组装 OrderItemCheck 列表供优惠券校验使用
                List<OrderItemCheck> checks = new ArrayList<>();
                for (OrderItemDTO item : dto.getItems()) {
                    Product p = productService.getById(item.getProduct_id());
                    OrderItemCheck check = new OrderItemCheck();
                    check.setProductId(item.getProduct_id());
                    check.setCategoryId(p != null ? p.getCategoryId() : null);
                    check.setPrice(p != null ? p.getPrice() : BigDecimal.ZERO);
                    check.setCount(item.getCount());
                    checks.add(check);
                }
                discountAmount = couponService.validateAndUse(
                        dto.getCouponId(), userId, totalAmount, checks, orderNo);
            }

            OrderMain order = new OrderMain();
            order.setOrderNo(orderNo);
            order.setUserId(userId);
            order.setTotalAmount(totalAmount);
            order.setPayAmount(totalAmount.subtract(discountAmount));
            order.setDiscountAmount(discountAmount);
            order.setCouponId(dto.getCouponId());
            order.setStatus(STATUS_PENDING_PAY);

            Address address = addressMapper.selectById(dto.getAddressId());
            if (address == null || !address.getUserId().equals(userId)) {
                throw new BusinessException(400, "收货地址不存在");
            }
            order.setReceiverName(address.getReceiverName());
            order.setReceiverPhone(address.getReceiverPhone());
            order.setReceiverAddress(address.getDetailAddr());
            
            StringBuilder fullAddr = new StringBuilder();
            if (address.getProvince() != null) fullAddr.append(address.getProvince());
            if (address.getCity() != null) fullAddr.append(address.getCity());
            if (address.getDistrict() != null) fullAddr.append(address.getDistrict());
            if (address.getDetailAddr() != null) fullAddr.append(address.getDetailAddr());
            order.setReceiverFullAddr(fullAddr.toString());
            order.setLon(address.getLongitude());
            order.setLat(address.getLatitude());
            
            order.setRemark(dto.getRemark());
            save(order);

            for (OrderItem oi : orderItems) {
                oi.setOrderId(order.getId());
                orderItemMapper.insert(oi);
            }

            log.info("订单创建成功: orderNo={}, userId={}, amount={}", orderNo, userId, totalAmount);
            return orderNo;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("创建订单异常，回滚Redis库存", e);
            for (int i = 0; i < deductedProductIds.size(); i++) {
                stockService.rollbackStock(deductedProductIds.get(i), dto.getItems().get(i).getCount());
            }
            throw new BusinessException(500, "创建订单失败");
        }
    }

    @Override
    public Page<OrderVO> listOrders(OrderListQuery query) {
        UserDTO user = UserHolder.getUser();
        if (user == null || user.getId() == null) {
            throw new BusinessException(401, "请先登录后操作");
        }

        Page<OrderMain> page = new Page<>(query.getCurrent(), query.getSize());
        LambdaQueryWrapper<OrderMain> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderMain::getUserId, user.getId());
        if (query.getStatus() != null && query.getStatus() >= 0) {
            wrapper.eq(OrderMain::getStatus, query.getStatus());
        }
        wrapper.orderByDesc(OrderMain::getCreateTime);
        Page<OrderMain> orderPage = page(page, wrapper);

        Page<OrderVO> result = new Page<>(orderPage.getCurrent(), orderPage.getSize(), orderPage.getTotal());
        if (orderPage.getRecords().isEmpty()) {
            result.setRecords(Collections.emptyList());
            return result;
        }

        List<Long> orderIds = orderPage.getRecords().stream()
                .map(OrderMain::getId).collect(Collectors.toList());
        LambdaQueryWrapper<OrderItem> itemWrapper = new LambdaQueryWrapper<>();
        itemWrapper.in(OrderItem::getOrderId, orderIds)
                .orderByAsc(OrderItem::getId);
        List<OrderItem> allItems = orderItemMapper.selectList(itemWrapper);
        Map<Long, List<OrderItem>> itemMap = allItems.stream()
                .collect(Collectors.groupingBy(OrderItem::getOrderId));

        List<OrderVO> voList = orderPage.getRecords().stream()
                .map(o -> OrderVO.of(o, itemMap.getOrDefault(o.getId(), Collections.emptyList())))
                .collect(Collectors.toList());
        result.setRecords(voList);
        return result;
    }

    @Override
    public OrderVO getOrderDetail(String orderNo) {
        UserDTO user = UserHolder.getUser();
        if (user == null || user.getId() == null) {
            throw new BusinessException(401, "请先登录后操作");
        }
        LambdaQueryWrapper<OrderMain> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderMain::getOrderNo, orderNo)
                .eq(OrderMain::getUserId, user.getId());
        OrderMain order = getOne(wrapper);
        if (order == null) {
            throw new BusinessException(404, "订单不存在");
        }
        LambdaQueryWrapper<OrderItem> itemWrapper = new LambdaQueryWrapper<>();
        itemWrapper.eq(OrderItem::getOrderId, order.getId())
                .orderByAsc(OrderItem::getId);
        List<OrderItem> items = orderItemMapper.selectList(itemWrapper);
        return OrderVO.of(order, items);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void payOrder(String orderNo) {
        OrderMain order = requireOrderForCurrentUser(orderNo);
        if (order.getStatus() != STATUS_PENDING_PAY) {
            throw new BusinessException(400, "订单状态不允许支付");
        }
        if (isExpired(order.getCreateTime())) {
            throw new BusinessException(400, "订单已超时，无法支付");
        }
        UpdateWrapper<OrderMain> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", order.getId())
                .eq("status", STATUS_PENDING_PAY)
                .set("status", STATUS_COMPLETED)
                .set("pay_time", LocalDateTime.now());
        boolean ok = update(updateWrapper);
        if (!ok) {
            throw new BusinessException(400, "支付失败，订单状态已变更");
        }
        // 发放积分（按实付金额计算）
        int points = order.getPayAmount() != null ? order.getPayAmount().intValue() : 0;
        if (points > 0) {
            pointsService.awardPoints(order.getUserId(), orderNo, points);
            // 记录到订单
            UpdateWrapper<OrderMain> pointsWrapper = new UpdateWrapper<>();
            pointsWrapper.eq("id", order.getId()).set("points_earned", points);
            update(pointsWrapper);
        }
        log.info("订单支付成功，订单完成: orderNo={}, points={}", orderNo, points);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(String orderNo) {
        OrderMain order = requireOrderForCurrentUser(orderNo);
        if (order.getStatus() != STATUS_PENDING_PAY) {
            throw new BusinessException(400, "仅待付款订单可取消");
        }
        UpdateWrapper<OrderMain> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", order.getId())
                .eq("status", STATUS_PENDING_PAY)
                .set("status", STATUS_CANCELLED);
        boolean ok = update(updateWrapper);
        if (!ok) {
            throw new BusinessException(400, "取消失败，订单状态已变更");
        }
        returnStock(order.getId());
        // 退回优惠券
        if (order.getCouponId() != null) {
            couponService.returnCoupon(order.getCouponId());
        }
        log.info("订单已取消并归还库存: orderNo={}", orderNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmReceive(String orderNo) {
        throw new BusinessException(400, "当前版本不支持此操作");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int autoCancelExpiredOrders() {
        LocalDateTime expireTime = LocalDateTime.now().minusMinutes(PAY_EXPIRE_MINUTES);
        LambdaQueryWrapper<OrderMain> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderMain::getStatus, STATUS_PENDING_PAY)
                .lt(OrderMain::getCreateTime, expireTime);
        List<OrderMain> expired = list(wrapper);
        if (expired.isEmpty()) {
            return 0;
        }
        int count = 0;
        for (OrderMain order : expired) {
            UpdateWrapper<OrderMain> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", order.getId())
                    .eq("status", STATUS_PENDING_PAY)
                    .set("status", STATUS_CANCELLED);
            boolean ok = update(updateWrapper);
            if (ok) {
                returnStock(order.getId());
                log.info("订单超时自动取消并归还库存: orderNo={}", order.getOrderNo());
                count++;
            }
        }
        return count;
    }

    private OrderMain requireOrderForCurrentUser(String orderNo) {
        UserDTO user = UserHolder.getUser();
        if (user == null || user.getId() == null) {
            throw new BusinessException(401, "请先登录后操作");
        }
        LambdaQueryWrapper<OrderMain> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderMain::getOrderNo, orderNo)
                .eq(OrderMain::getUserId, user.getId());
        OrderMain order = getOne(wrapper);
        if (order == null) {
            throw new BusinessException(404, "订单不存在");
        }
        return order;
    }

    private boolean isExpired(LocalDateTime createTime) {
        return createTime.plusMinutes(PAY_EXPIRE_MINUTES).isBefore(LocalDateTime.now());
    }

    private void returnStock(Long orderId) {
        LambdaQueryWrapper<OrderItem> itemWrapper = new LambdaQueryWrapper<>();
        itemWrapper.eq(OrderItem::getOrderId, orderId);
        List<OrderItem> items = orderItemMapper.selectList(itemWrapper);
        for (OrderItem item : items) {
            UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
            updateWrapper.setSql("stock = stock + " + item.getQuantity())
                    .eq("id", item.getProductId());
            productService.update(updateWrapper);
            stockService.increaseStock(item.getProductId(), item.getQuantity());
        }
    }
}