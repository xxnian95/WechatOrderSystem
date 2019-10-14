package com.imooc.enums;

import lombok.Getter;

/**
 * @author Zhang Pengnian
 * @since 2019-10-14 23:50
 */
@Getter
public enum PayStatusEnum {

    /**
     * 未支付
     */
    UNPAID(0, "未支付"),

    /**
     * 已支付
     */
    PAID(1, "已支付");

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
