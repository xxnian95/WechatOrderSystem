package com.imooc.dto;

import com.imooc.model.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 由于OrderDetail对象与数据库的字段一一对应，为了新增新的字段用于其他用途，新建此对象。
 * DTO：Data Transfer Object
 *
 * @author Zhang Pengnian
 * @since 2019-10-15 14:11
 */
@Data
public class OrderDTO {

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 买家名字
     */
    private String buyerName;

    /**
     * 买家手机号
     */
    private String buyerPhone;

    /**
     * 买家地址
     */
    private String buyerAddress;

    /**
     * 买家OpenID
     */
    private String buyerOpenid;

    /**
     * 订单总金额
     */
    private BigDecimal orderAmount;

    /**
     * 订单状态。默认状态为新下单。
     */
    private Integer orderStatus;

    /**
     * 支付状态，默认是0未支付。
     */
    private Integer payStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     *
     */
    List<OrderDetail> orderDetailList;


}
