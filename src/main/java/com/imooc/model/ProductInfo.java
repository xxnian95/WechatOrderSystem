package com.imooc.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author Zhang Pengnian
 * @since 2019-10-14 09:50
 */
@Entity
@Data
@DynamicUpdate
public class ProductInfo {

    @Id
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品单价
     */
    private BigDecimal productPrice;

    /**
     * 商品库存
     */
    private Integer productStock;

    /**
     * 描述
     */
    private String productDescription;

    /**
     * 商品小图（链接地址）
     */
    private String productIcon;

    /**
     * 商品状态，0正常，1下架
     */
    private Integer productStatus;

    /**
     * 类目编号
     */
    private Integer categoryType;

}
