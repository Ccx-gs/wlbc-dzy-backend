package com.shopping.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.shopping.common.BusinessException;
import com.shopping.common.UserHolder;
import com.shopping.dto.CartItemVO;
import com.shopping.dto.CartVO;
import com.shopping.dto.UserDTO;
import com.shopping.entity.Product;
import com.shopping.service.CartService;
import com.shopping.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final StringRedisTemplate stringRedisTemplate;
    private final ProductService productService;
    private static final String CART_PREFIX = "cart:";
    private static final long CART_TTL = 30;
    private static final int MAX_COUNT = 999;

    private Long getUserId() {
        UserDTO user = UserHolder.getUser();
        if (user == null) {
            throw new BusinessException(401, "请先登录后操作");
        }
        return user.getId();
    }

    private String cartKey(Long userId) {
        return CART_PREFIX + userId;
    }

    private Product validateProduct(Long productId) {
        if (productId == null || productId <= 0) {
            throw new BusinessException(400, "商品ID不合法");
        }
        Product product = productService.getById(productId);
        if (product == null) {
            throw new BusinessException(400, "商品不存在");
        }
        if (product.getStatus() != 1) {
            throw new BusinessException(400, "商品已下架");
        }
        return product;
    }

    private CartItemVO parseItem(String jsonStr, Product product) {
        CartItemVO vo = new CartItemVO();
        vo.setProductId(product.getId());
        vo.setProductName(product.getName());
        vo.setProductImage(product.getMainImage());
        vo.setUnitPrice(product.getPrice());
        // 兼容旧数据: 纯数字字符串当作 count，selected 默认 true
        try {
            JSONObject obj = JSONUtil.parseObj(jsonStr);
            vo.setCount(obj.getInt("count", 1));
            vo.setSelected(obj.getBool("selected", true));
        } catch (Exception e) {
            vo.setCount(Integer.parseInt(jsonStr));
            vo.setSelected(true);
        }
        vo.calcSubtotal();
        return vo;
    }

    private String buildValue(Integer count, Boolean selected) {
        JSONObject obj = new JSONObject();
        obj.set("count", count);
        obj.set("selected", selected != null ? selected : true);
        return obj.toString();
    }

    private void refreshTtl(String key) {
        stringRedisTemplate.expire(key, CART_TTL, TimeUnit.DAYS);
    }

    @Override
    public void addCart(Long productId, Integer count) {
        Long userId = getUserId();
        if (count == null || count <= 0) {
            throw new BusinessException(400, "商品数量必须大于0");
        }
        if (count > MAX_COUNT) {
            throw new BusinessException(400, "单件商品最多添加" + MAX_COUNT + "件");
        }
        Product product = validateProduct(productId);
        if (count > product.getStock()) {
            throw new BusinessException(400, "库存不足，当前库存: " + product.getStock());
        }
        String key = cartKey(userId);
        String field = productId.toString();
        // 读取已有数量，合并
        String exist = (String) stringRedisTemplate.opsForHash().get(key, field);
        int total = count;
        if (exist != null) {
            try {
                JSONObject obj = JSONUtil.parseObj(exist);
                total += obj.getInt("count", 0);
            } catch (Exception e) {
                total += Integer.parseInt(exist);
            }
        }
        if (total > MAX_COUNT) {
            throw new BusinessException(400, "该商品累计不能超过" + MAX_COUNT + "件");
        }
        if (total > product.getStock()) {
            throw new BusinessException(400, "库存不足，当前库存: " + product.getStock());
        }
        stringRedisTemplate.opsForHash().put(key, field, buildValue(total, true));
        refreshTtl(key);
    }

    @Override
    public CartVO getCart() {
        Long userId = getUserId();
        String key = cartKey(userId);
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(key);
        List<CartItemVO> items = new ArrayList<>();
        int selectedCount = 0;
        BigDecimal selectedTotal = BigDecimal.ZERO;
        int totalCount = 0;
        for (Map.Entry<Object, Object> entry : entries.entrySet()) {
            Long productId = Long.valueOf(entry.getKey().toString());
            Product product = productService.getById(productId);
            if (product == null) continue;
            CartItemVO vo = parseItem(entry.getValue().toString(), product);
            items.add(vo);
            totalCount += vo.getCount();
            if (vo.getSelected() != null && vo.getSelected()) {
                selectedCount += vo.getCount();
                selectedTotal = selectedTotal.add(vo.getSubtotal());
            }
        }
        CartVO cartVO = new CartVO();
        cartVO.setItems(items);
        cartVO.setSelectedCount(selectedCount);
        cartVO.setSelectedTotalPrice(selectedTotal);
        cartVO.setTotalCount(totalCount);
        return cartVO;
    }

    @Override
    public void removeCart(Long productId) {
        Long userId = getUserId();
        if (productId == null || productId <= 0) {
            throw new BusinessException(400, "商品ID不合法");
        }
        stringRedisTemplate.opsForHash().delete(cartKey(userId), productId.toString());
        refreshTtl(cartKey(userId));
    }

    @Override
    public void updateCartCount(Long productId, Integer count) {
        Long userId = getUserId();
        if (count == null || count <= 0) {
            throw new BusinessException(400, "商品数量必须大于0");
        }
        if (count > MAX_COUNT) {
            throw new BusinessException(400, "单件商品最多" + MAX_COUNT + "件");
        }
        Product product = validateProduct(productId);
        if (count > product.getStock()) {
            throw new BusinessException(400, "库存不足，当前库存: " + product.getStock());
        }
        String key = cartKey(userId);
        String field = productId.toString();
        String exist = (String) stringRedisTemplate.opsForHash().get(key, field);
        if (exist == null) {
            throw new BusinessException(400, "购物车中没有该商品");
        }
        boolean selected = true;
        try {
            selected = JSONUtil.parseObj(exist).getBool("selected", true);
        } catch (Exception ignored) {}
        stringRedisTemplate.opsForHash().put(key, field, buildValue(count, selected));
        refreshTtl(key);
    }

    @Override
    public void batchRemoveCart(List<Long> productIds) {
        Long userId = getUserId();
        if (productIds == null || productIds.isEmpty()) {
            throw new BusinessException(400, "请选择要删除的商品");
        }
        Object[] fields = productIds.stream().map(Object::toString).toArray();
        stringRedisTemplate.opsForHash().delete(cartKey(userId), fields);
        refreshTtl(cartKey(userId));
    }

    @Override
    public void clearAllCart() {
        Long userId = getUserId();
        stringRedisTemplate.delete(cartKey(userId));
    }

    @Override
    public void toggleSelect(Long productId) {
        Long userId = getUserId();
        if (productId == null || productId <= 0) {
            throw new BusinessException(400, "商品ID不合法");
        }
        String key = cartKey(userId);
        String field = productId.toString();
        String exist = (String) stringRedisTemplate.opsForHash().get(key, field);
        if (exist == null) {
            throw new BusinessException(400, "购物车中没有该商品");
        }
        int count;
        boolean selected;
        try {
            JSONObject obj = JSONUtil.parseObj(exist);
            count = obj.getInt("count", 1);
            selected = !obj.getBool("selected", true);
        } catch (Exception e) {
            count = Integer.parseInt(exist);
            selected = false;
        }
        stringRedisTemplate.opsForHash().put(key, field, buildValue(count, selected));
        refreshTtl(key);
    }

    @Override
    public void selectAllCart(Boolean selectAll) {
        Long userId = getUserId();
        String key = cartKey(userId);
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(key);
        for (Map.Entry<Object, Object> entry : entries.entrySet()) {
            String field = entry.getKey().toString();
            int count;
            try {
                count = JSONUtil.parseObj(entry.getValue().toString()).getInt("count", 1);
            } catch (Exception e) {
                count = Integer.parseInt(entry.getValue().toString());
            }
            stringRedisTemplate.opsForHash().put(key, field,
                    buildValue(count, selectAll != null && selectAll));
        }
        refreshTtl(key);
    }

    @Override
    public CartVO getSelectedCartTotal() {
        CartVO cartVO = getCart();
        if (cartVO.getItems() == null) return cartVO;
        List<CartItemVO> selectedItems = cartVO.getItems().stream()
                .filter(i -> i.getSelected() != null && i.getSelected())
                .toList();
        CartVO result = new CartVO();
        result.setItems(selectedItems);
        result.setSelectedCount(selectedItems.stream().mapToInt(CartItemVO::getCount).sum());
        result.setSelectedTotalPrice(selectedItems.stream()
                .map(CartItemVO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        result.setTotalCount(selectedItems.stream().mapToInt(CartItemVO::getCount).sum());
        return result;
    }
}
