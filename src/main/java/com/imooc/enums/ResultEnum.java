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
    /**
     * 库存不足
     */
    STOCK_NOT_ENOUGH(11, "库存不足"),
    /**
     * 订单不存在
     */
    ORDER_NOT_EXIST(12, "订单不存在"),
    /**
     * 订单详情不存在（这个订单是空的）
     */
    DETAIL_NOT_EXIST(13, "订单详情不存在"),
    /**
     * 订单状态不正确
     * 取消订单时，订单的当前状态不是NEW
     */
    ORDER_STATUS_ERROR(14, "订单状态不正确"),
    /**
     * 订单更新失败
     */
    ORDER_UPDATE_FAIL(15, "订单更新失败"),

    ORDER_DETAIL_EMPTY(16, "订单中无订单详情"),

    ORDER_PAY_STATUS_ERROR(17, "订单的支付状态不正确")
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
