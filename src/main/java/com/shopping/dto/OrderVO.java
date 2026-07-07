package com.shopping.dto;

import com.shopping.entity.OrderItem;
import com.shopping.entity.OrderMain;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单列表/详情返回 VO（含订单项）
 */
@Data
public class OrderVO {
    private Long id;
    private String orderNo;
    private Long userId;
    private BigDecimal totalAmount;
    private BigDecimal payAmount;
    private Integer status;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private String remark;
    private LocalDateTime payTime;
    private BigDecimal discountAmount;
    private Integer pointsEarned;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private List<OrderItem> items;

    public static OrderVO of(OrderMain o, List<OrderItem> items) {
        OrderVO vo = new OrderVO();
        vo.setId(o.getId());
        vo.setOrderNo(o.getOrderNo());
        vo.setUserId(o.getUserId());
        vo.setTotalAmount(o.getTotalAmount());
        vo.setPayAmount(o.getPayAmount());
        vo.setStatus(o.getStatus());
        vo.setReceiverName(o.getReceiverName());
        vo.setReceiverPhone(o.getReceiverPhone());
        vo.setReceiverAddress(o.getReceiverAddress());
        vo.setRemark(o.getRemark());
        vo.setPayTime(o.getPayTime());
        vo.setDiscountAmount(o.getDiscountAmount() != null ? o.getDiscountAmount() : BigDecimal.ZERO);
        vo.setPointsEarned(o.getPointsEarned());
        vo.setCreateTime(o.getCreateTime());
        vo.setUpdateTime(o.getUpdateTime());
        vo.setItems(items);
        return vo;
    }
}
