package com.imooc.enums;

import lombok.Getter;

/**
 * @author Zhang Pengnian
 * @since 2019-10-15 15:01
 */
@Getter
public enum ResultEnum {

    /**
     * 商品不存在的异常
     */
    PRODUCT_NOT_EXIST(10, "商品不存在"),
    STOCK_NOT_ENOUGH(11, "库存不足"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
