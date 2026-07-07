package com.shopping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shopping.config.AITool;
import com.shopping.entity.ChatRecord;
import com.shopping.entity.Coupon;
import com.shopping.entity.OrderMain;
import com.shopping.entity.Product;
import com.shopping.entity.UserCoupon;
import com.shopping.mapper.CouponMapper;
import com.shopping.mapper.ChatRecordMapper;
import com.shopping.mapper.OrderMainMapper;
import com.shopping.mapper.ProductMapper;
import com.shopping.mapper.UserCouponMapper;
import com.shopping.service.ChatService;
import com.shopping.service.PointsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final AITool aiTool;
    private final ChatRecordMapper chatRecordMapper;
    private final ProductMapper productMapper;
    private final OrderMainMapper orderMainMapper;
    private final UserCouponMapper userCouponMapper;
    private final CouponMapper couponMapper;
    private final PointsService pointsService;
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    private static final String REJECT_MESSAGE = "不好意思，我仅提供商城数码商品购物相关咨询，其他问题无法为您解答";
    private static final Pattern KEYWORD_PATTERN = Pattern.compile("[\\u4e00-\\u9fa5a-zA-Z0-9]{2,}");
    private static final String REDIS_KEY_PREFIX = "chat:session:";
    private static final int REDIS_EXPIRE_HOURS = 24;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String ask(Long userId, String userMsg, List<Map<String, String>> historyList) {
        if (userMsg == null || userMsg.trim().isEmpty()) {
            return "请输入您的问题";
        }

        if (!isShoppingRelated(userMsg)) {
            saveRecord(userId, userMsg, REJECT_MESSAGE);
            updateRedisSession(userId, userMsg, REJECT_MESSAGE);
            return REJECT_MESSAGE;
        }

        String productInfo = searchProducts(userMsg);
        String orderInfo = searchOrders(userId);
        String couponInfo = searchCouponsAndPoints(userId);
        String referenceInfo = productInfo
                + (couponInfo.isEmpty() ? "" : "\n" + couponInfo)
                + (orderInfo.isEmpty() ? "" : "\n" + orderInfo);

        String aiReply;
        try {
            aiReply = aiTool.chat(userMsg, historyList, referenceInfo);
        } catch (Exception e) {
            log.error("AI chat failed", e);
            aiReply = "抱歉，服务暂时出现异常，请稍后重试";
        }

        saveRecord(userId, userMsg, aiReply);
        updateRedisSession(userId, userMsg, aiReply);
        return aiReply;
    }

    @Override
    public List<ChatRecord> getHistory(Long userId) {
        try {
            return chatRecordMapper.selectList(
                new LambdaQueryWrapper<ChatRecord>()
                    .eq(ChatRecord::getUserId, userId)
                    .orderByAsc(ChatRecord::getSendTime)
            );
        } catch (Exception e) {
            log.error("Failed to get chat history from MySQL", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearHistory(Long userId) {
        try {
            String redisKey = REDIS_KEY_PREFIX + userId;
            redisTemplate.delete(redisKey);
        } catch (Exception e) {
            log.error("Failed to clear chat session cache", e);
        }
        try {
            chatRecordMapper.delete(new LambdaQueryWrapper<ChatRecord>()
                    .eq(ChatRecord::getUserId, userId));
            log.info("Cleared chat history for user: {}", userId);
        } catch (Exception e) {
            log.error("Failed to clear chat history from MySQL", e);
        }
    }

    @Override
    public List<Map<String, String>> loadSession(Long userId) {
        String redisKey = REDIS_KEY_PREFIX + userId;
        
        try {
            String cachedSession = redisTemplate.opsForValue().get(redisKey);
            if (cachedSession != null && !cachedSession.isEmpty()) {
                log.info("Loading chat session from Redis for user: {}", userId);
                List<Map<String, String>> session = objectMapper.readValue(
                    cachedSession, 
                    new TypeReference<List<Map<String, String>>>() {}
                );
                redisTemplate.expire(redisKey, REDIS_EXPIRE_HOURS, TimeUnit.HOURS);
                return session;
            }
        } catch (Exception e) {
            log.error("Failed to load chat session from Redis", e);
        }

        log.info("Loading chat session from MySQL for user: {}", userId);
        List<ChatRecord> records = getHistory(userId);
        List<Map<String, String>> session = new ArrayList<>();
        for (ChatRecord record : records) {
            Map<String, String> userMsg = new HashMap<>();
            userMsg.put("role", "user");
            userMsg.put("content", record.getUserMsg());
            session.add(userMsg);
            
            if (record.getAiReply() != null && !record.getAiReply().isEmpty()) {
                Map<String, String> aiMsg = new HashMap<>();
                aiMsg.put("role", "assistant");
                aiMsg.put("content", record.getAiReply());
                session.add(aiMsg);
            }
        }

        if (!session.isEmpty()) {
            try {
                String jsonSession = objectMapper.writeValueAsString(session);
                redisTemplate.opsForValue().set(redisKey, jsonSession, REDIS_EXPIRE_HOURS, TimeUnit.HOURS);
                log.info("Cached chat session to Redis for user: {}", userId);
            } catch (Exception e) {
                log.error("Failed to cache chat session to Redis", e);
            }
        }

        return session;
    }

    private void updateRedisSession(Long userId, String userMsg, String aiReply) {
        String redisKey = REDIS_KEY_PREFIX + userId;
        
        try {
            List<Map<String, String>> session = loadSession(userId);
            
            Map<String, String> userMsgMap = new HashMap<>();
            userMsgMap.put("role", "user");
            userMsgMap.put("content", userMsg);
            session.add(userMsgMap);
            
            Map<String, String> aiMsgMap = new HashMap<>();
            aiMsgMap.put("role", "assistant");
            aiMsgMap.put("content", aiReply);
            session.add(aiMsgMap);
            
            String jsonSession = objectMapper.writeValueAsString(session);
            redisTemplate.opsForValue().set(redisKey, jsonSession, REDIS_EXPIRE_HOURS, TimeUnit.HOURS);
        } catch (Exception e) {
            log.error("Failed to update chat session in Redis", e);
        }
    }

    /**
     * 判断是否为商城相关问题。
     * 策略：白名单优先 —— 只要包含购物关键词就不拦截，让 AI 自己回答。
     * 仅有纯黑名单词且无购物意图时才拒绝，避免浪费 token。
     */
    private boolean isShoppingRelated(String msg) {
        // ===== 白名单：包含以下任意词 → 直接放行 =====
        String[] whiteList = {
            // 商品通用词
            "商品", "产品", "多少钱", "价格", "便宜", "贵", "性价比", "划算",
            "库存", "有货", "没货", "断货", "补货", "新款", "二手", "翻新",
            "买", "卖", "下单", "购物", "购买", "加购", "抢购", "秒杀", "预售",
            // 订单/支付
            "订单", "支付", "付款", "退款", "退货", "换货", "售后",
            "取消", "发货", "物流", "快递", "配送", "收货", "运费", "包邮",
            // 账号/积分/券
            "账号", "注册", "登录", "密码", "地址", "积分", "优惠券", "折扣",
            "满减", "会员", "收藏", "关注", "浏览", "足迹",
            // 产品品类
            "手机", "电脑", "笔记本", "平板", "耳机", "手表", "手环",
            "相机", "电视", "音响", "音箱", "家电", "冰箱", "洗衣机",
            "空调", "扫地", "路由", "充电", "数据线", "键盘", "鼠标",
            "显示器", "投影", "打印机", "数码", "电子", "智能",
            // 产品参数
            "配置", "参数", "型号", "规格", "尺寸", "颜色", "内存", "存储",
            "电池", "续航", "屏幕", "分辨率", "刷新率", "处理器", "芯片",
            "系统", "网络", "摄像头", "像素", "重量", "材质",
            "蓝牙", "WiFi", "Type-C", "HDMI", "USB", "快充", "无线充",
            // 购物咨询
            "推荐", "哪个好", "怎么样", "好不好", "值得买", "区别", "对比",
            "选哪个", "适合", "能不能", "可以吗", "支持", "兼容",
            "保修", "维修", "换新", "以旧换新", "分期", "免息",
            "评价", "口碑", "测评", "开箱", "体验", "功能",
        };

        for (String w : whiteList) {
            if (msg.contains(w)) {
                return true; // 白名单命中，放行给 AI
            }
        }

        // ===== 黑名单：纯无关聊天 → 拒绝 =====
        String[] blackList = {
            "天气", "新闻", "八卦", "股票", "彩票", "星座", "算命",
            "笑话", "讲故事", "写诗", "作诗", "写歌", "写作文", "翻译",
            "游戏攻略", "游戏怎么", "打游戏", "玩游戏",
            "电影推荐", "看电影", "电视剧", "综艺节目", "追剧",
            "音乐推荐", "听歌", "唱歌", "点歌",
            "美食推荐", "去哪吃", "旅游攻略", "订酒店", "机票",
            "体育赛事", "足球", "篮球", "NBA", "世界杯",
        };

        for (String b : blackList) {
            if (msg.contains(b)) {
                return false;
            }
        }

        // 默认放行（无法判断的给 AI 自己处理）
        return true;
    }

    private String searchProducts(String msg) {
        List<String> keywords = extractKeywords(msg);
        if (keywords.isEmpty()) {
            return "暂无匹配商品";
        }

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("(");
        for (int i = 0; i < keywords.size(); i++) {
            if (i > 0) queryBuilder.append(" OR ");
            queryBuilder.append("name LIKE '%").append(keywords.get(i)).append("%'");
        }
        queryBuilder.append(")");

        try {
            List<Product> products = productMapper.selectList(
                new LambdaQueryWrapper<Product>()
                    .apply(queryBuilder.toString())
                    .eq(Product::getStatus, 1)
                    .last("LIMIT 10")
            );

            if (products.isEmpty()) {
                return "暂无匹配商品";
            }

            StringBuilder productInfoBuilder = new StringBuilder();
            for (Product product : products) {
                productInfoBuilder.append("- 商品名称：").append(product.getName())
                        .append("，价格：").append(product.getPrice())
                        .append("元，库存：").append(product.getStock())
                        .append("件\n");
            }

            return productInfoBuilder.toString();
        } catch (Exception e) {
            log.error("Failed to search products", e);
            return "商品查询失败";
        }
    }

    private String searchOrders(Long userId) {
        try {
            List<OrderMain> orders = orderMainMapper.selectList(
                new LambdaQueryWrapper<OrderMain>()
                    .eq(OrderMain::getUserId, userId)
                    .orderByDesc(OrderMain::getCreateTime)
                    .last("LIMIT 5")
            );

            if (orders.isEmpty()) {
                return "";
            }

            StringBuilder orderInfoBuilder = new StringBuilder();
            orderInfoBuilder.append("您的订单信息：\n");
            for (OrderMain order : orders) {
                String statusDesc = getOrderStatusDesc(order.getStatus());
                orderInfoBuilder.append("- 订单号：").append(order.getOrderNo())
                        .append("，金额：").append(order.getPayAmount())
                        .append("元，状态：").append(statusDesc)
                        .append("\n");
            }

            return orderInfoBuilder.toString();
        } catch (Exception e) {
            log.error("Failed to search orders", e);
            return "";
        }
    }

    private String searchCouponsAndPoints(Long userId) {
        try {
            // 积分
            int points = pointsService.getPoints();

            // 优惠券
            List<UserCoupon> userCoupons = userCouponMapper.selectList(
                new LambdaQueryWrapper<UserCoupon>()
                    .eq(UserCoupon::getUserId, userId)
                    .eq(UserCoupon::getStatus, 0)
                    .gt(UserCoupon::getExpireTime, LocalDateTime.now())
                    .orderByAsc(UserCoupon::getExpireTime)
                    .last("LIMIT 10")
            );

            StringBuilder sb = new StringBuilder();
            sb.append("用户积分与优惠券信息：\n");
            sb.append("- 可用积分：").append(points).append("\n");

            if (userCoupons.isEmpty()) {
                sb.append("- 优惠券：暂无可用优惠券\n");
            } else {
                sb.append("- 可用优惠券：\n");
                for (UserCoupon uc : userCoupons) {
                    Coupon c = couponMapper.selectById(uc.getCouponId());
                    if (c == null) continue;
                    String typeLabel = c.getDiscountType() == 1
                            ? "满" + c.getMinOrderAmount() + "减" + c.getDiscountValue()
                            : "满" + c.getMinOrderAmount() + "打" + c.getDiscountValue().multiply(
                                    java.math.BigDecimal.TEN).stripTrailingZeros().toPlainString() + "折";
                    sb.append("  · ").append(c.getName())
                            .append("（").append(typeLabel)
                            .append("，").append(c.getScopeType() == 1 ? "全场通用" : "指定商品")
                            .append("，").append(uc.getExpireTime().toLocalDate()).append("到期）\n");
                }
            }
            return sb.toString();
        } catch (Exception e) {
            log.error("Failed to search coupons and points", e);
            return "";
        }
    }

    private String getOrderStatusDesc(Integer status) {
        if (status == null) return "未知";
        return switch (status) {
            case 0 -> "待付款";
            case 3 -> "已完成";
            case 4 -> "已取消";
            default -> "未知";
        };
    }

    private List<String> extractKeywords(String msg) {
        List<String> keywords = new ArrayList<>();
        Matcher matcher = KEYWORD_PATTERN.matcher(msg);
        while (matcher.find()) {
            String keyword = matcher.group();
            if (!keywords.contains(keyword)) {
                keywords.add(keyword);
            }
        }
        return keywords;
    }

    private void saveRecord(Long userId, String userMsg, String aiReply) {
        try {
            ChatRecord record = new ChatRecord();
            record.setUserId(userId);
            record.setUserMsg(userMsg);
            record.setAiReply(aiReply);
            record.setSendTime(LocalDateTime.now());
            chatRecordMapper.insert(record);
        } catch (Exception e) {
            log.error("Failed to save chat record", e);
        }
    }
}