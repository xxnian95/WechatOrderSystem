package com.imooc.enums;

import lombok.Getter;

/**
 * 商品状态
 *
 * @author Zhang Pengnian
 * @since 2019-10-14 13:24
 */
@Getter
public enum ProductStatusEnum {

    /**
     * 上架
     */
    UP(0, "在架"),

    /**
     * 下架
     */
    DOWN(1, "下架");

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code) {
        this.code = code;
    }

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
