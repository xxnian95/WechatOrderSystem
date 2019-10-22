package com.imooc.service;

import com.imooc.dto.OrderDTO;

/**
 * 买家Service
 *
 * @author Zhang Pengnian
 * @since 2019-10-22 15:01
 */
public interface BuyerService {

    /**
     * 查询一个订单
     *
     * @param openid 买家的openid
     * @param orderId 订单ID
     * @return OrderDTO
     */
    OrderDTO findOrderOne(String openid, String orderId);

    /**
     * 取消订单
     *
     * @param openid 买家的openid
     * @param orderId 订单ID
     * @return OrderDTO
     */
    OrderDTO cancelOrder(String openid, String orderId);

}
