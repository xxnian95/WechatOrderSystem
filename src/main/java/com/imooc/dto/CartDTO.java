package com.imooc.dto;

import lombok.Data;

/**
 * 购物车，包含商品ID和购买的数量
 *
 * @author Zhang Pengnian
 * @since 2019-10-15 15:27
 */
@Data
public class CartDTO {

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 商品数量
     */
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
