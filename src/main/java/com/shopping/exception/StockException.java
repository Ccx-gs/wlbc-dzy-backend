package com.shopping.exception;

import com.shopping.common.BusinessException;

public class StockException extends BusinessException {
    public StockException(String message) {
        super(400, message);
    }
}
