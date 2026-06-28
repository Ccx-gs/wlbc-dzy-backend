package com.shopping.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shopping.common.BusinessException;
import com.shopping.common.UserHolder;
import com.shopping.dto.OrderCreateDTO;
import com.shopping.dto.OrderItemDTO;
import com.shopping.entity.OrderItem;
import com.shopping.entity.OrderMain;
import com.shopping.entity.Product;
import com.shopping.exception.StockException;
import com.shopping.mapper.OrderItemMapper;
import com.shopping.mapper.OrderMainMapper;
import com.shopping.service.OrderService;
import com.shopping.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMainMapper, OrderMain> implements OrderService {

    private final OrderItemMapper orderItemMapper;
    private final ProductService productService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createOrder(OrderCreateDTO dto) {
        Long userId = UserHolder.getUser().getId();
        if (userId == null) {
            throw new BusinessException(401, "请先登录后操作");
        }
        String orderNo = "O" + IdUtil.getSnowflake(1, 1).nextIdStr();
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemDTO item : dto.getItems()) {
            Product product = productService.getById(item.getProduct_id());
            if (product == null || product.getStatus() != 1) {
                throw new BusinessException(400, "商品 " + item.getProduct_id() + " 不存在或已下架");
            }
            // 乐观锁扣库存：SQL 级原子操作，WHERE stock>=count AND version=oldVersion
            UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
            updateWrapper.setSql("stock = stock - " + item.getCount())
                    .eq("id", product.getId())
                    .ge("stock", item.getCount())
                    .eq("version", product.getVersion());
            boolean deducted = productService.update(updateWrapper);
            if (!deducted) {
                throw new StockException("商品 " + product.getName() + " 库存不足");
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

        OrderMain order = new OrderMain();
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setPayAmount(totalAmount);
        order.setStatus(1);
        order.setReceiverName(dto.getReceiver_name());
        order.setReceiverPhone(dto.getReceiver_phone());
        order.setReceiverAddress(dto.getReceiver_address());
        order.setRemark(dto.getRemark());
        save(order);

        for (OrderItem oi : orderItems) {
            oi.setOrderId(order.getId());
            orderItemMapper.insert(oi);
        }

        log.info("订单创建成功: orderNo={}, userId={}, amount={}", orderNo, userId, totalAmount);
        return orderNo;
    }
}
