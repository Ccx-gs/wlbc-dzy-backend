package com.shopping.service;

public interface StockService {

    boolean deductStock(Long productId, Integer count);

    void rollbackStock(Long productId, Integer count);

    Integer getRedisStock(Long productId);

    void syncStock(Long productId, Integer stock);

    void syncAllStock(java.util.Map<Long, Integer> stockMap);

    void increaseStock(Long productId, Integer count);
}
