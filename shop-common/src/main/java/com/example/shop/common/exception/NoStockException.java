package com.example.shop.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description 自定义库存不足异常类
 * @Author zpx
 * @Date 2023/12/24 10:13
 * @Version 1.0
 */
@Getter
@Setter
public class NoStockException extends RuntimeException {
    private Long productId;

    public NoStockException(Long productId) {
        super("商品Id:" + productId + ";库存不足");
    }

    public NoStockException(String msg) {
        super(msg);
    }
}
