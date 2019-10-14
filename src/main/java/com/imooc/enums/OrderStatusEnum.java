package com.imooc.enums;

import lombok.Getter;

/**
 * @author Zhang Pengnian
 * @since 2019-10-14 23:45
 */
@Getter
public enum OrderStatusEnum {
    /**
     * 新订单
     */
    NEW(0, "新订单"),

    /**
     * 订单已完成
     */
    FINISHED(1, "订单完成"),

    /**
     * 订单被取消
     */
    CANCELED(2, "已取消");

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
