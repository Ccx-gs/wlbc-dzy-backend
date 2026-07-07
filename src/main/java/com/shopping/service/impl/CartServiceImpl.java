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

    private CartItemVO parseItem(Long productId, String jsonStr) {
        CartItemVO vo = new CartItemVO();
        vo.setProductId(productId);
        try {
            JSONObject obj = JSONUtil.parseObj(jsonStr);
            vo.setCount(obj.getInt("count", 1));
            vo.setSelected(obj.getBool("selected", true));
            vo.setAddTime(obj.getLong("addTime", System.currentTimeMillis()));
            vo.setProductName(obj.getStr("name", ""));
            vo.setProductImage(obj.getStr("image", ""));
            Object priceObj = obj.get("price");
            if (priceObj instanceof Number) {
                vo.setUnitPrice(BigDecimal.valueOf(((Number) priceObj).doubleValue()));
            } else if (priceObj != null) {
                vo.setUnitPrice(new BigDecimal(priceObj.toString()));
            } else {
                vo.setUnitPrice(BigDecimal.ZERO);
            }
        } catch (Exception e) {
            // 兼容最老数据：纯数字字符串 = count
            vo.setCount(Integer.parseInt(jsonStr));
            vo.setSelected(true);
            vo.setAddTime(System.currentTimeMillis());
            vo.setProductName("");
            vo.setUnitPrice(BigDecimal.ZERO);
        }
        vo.calcSubtotal();
        return vo;
    }

    private String buildValue(Integer count, Boolean selected, Long addTime, String name, BigDecimal price, String image) {
        JSONObject obj = new JSONObject();
        obj.set("count", count);
        obj.set("selected", selected != null ? selected : true);
        obj.set("addTime", addTime != null ? addTime : System.currentTimeMillis());
        obj.set("name", name);
        obj.set("price", price);
        obj.set("image", image != null ? image : "");
        return obj.toString();
    }

    /** 部分更新：只改 count/selected，保留 name/price/image/addTime */
    private void partialUpdate(String key, String field, Integer count, Boolean selected) {
        String exist = (String) stringRedisTemplate.opsForHash().get(key, field);
        JSONObject obj;
        try {
            obj = JSONUtil.parseObj(exist);
        } catch (Exception e) {
            obj = new JSONObject();
        }
        if (count != null) obj.set("count", count);
        if (selected != null) obj.set("selected", selected);
        stringRedisTemplate.opsForHash().put(key, field, obj.toString());
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
        Long addTime = null;
        if (exist != null) {
            try {
                JSONObject obj = JSONUtil.parseObj(exist);
                total += obj.getInt("count", 0);
                addTime = obj.getLong("addTime", null);
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
        stringRedisTemplate.opsForHash().put(key, field,
                buildValue(total, true, addTime, product.getName(), product.getPrice(), product.getMainImage()));
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
            CartItemVO vo = parseItem(productId, entry.getValue().toString());
            // 兼容旧数据：Redis 中没有 name/price 时回退查库
            if (vo.getProductName() == null || vo.getProductName().isEmpty()) {
                Product product = productService.getById(productId);
                if (product == null) continue;
                vo.setProductName(product.getName());
                vo.setProductImage(product.getMainImage());
                vo.setUnitPrice(product.getPrice());
                vo.calcSubtotal();
            }
            items.add(vo);
            totalCount += vo.getCount();
            if (vo.getSelected() != null && vo.getSelected()) {
                selectedCount += vo.getCount();
                selectedTotal = selectedTotal.add(vo.getSubtotal());
            }
        }
        items.sort((a, b) -> Long.compare(b.getAddTime(), a.getAddTime()));
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
        partialUpdate(key, field, count, null);
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
        boolean selected;
        try {
            JSONObject obj = JSONUtil.parseObj(exist);
            selected = !obj.getBool("selected", true);
        } catch (Exception e) {
            selected = false;
        }
        partialUpdate(key, field, null, selected);
        refreshTtl(key);
    }

    @Override
    public void selectAllCart(Boolean selectAll) {
        Long userId = getUserId();
        String key = cartKey(userId);
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(key);
        for (Map.Entry<Object, Object> entry : entries.entrySet()) {
            String field = entry.getKey().toString();
            partialUpdate(key, field, null, selectAll != null && selectAll);
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
