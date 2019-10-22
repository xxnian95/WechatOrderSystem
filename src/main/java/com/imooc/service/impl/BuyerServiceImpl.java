package com.imooc.service.impl;

import com.imooc.dto.OrderDTO;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.service.BuyerService;
import com.imooc.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Zhang Pengnian
 * @since 2019-10-22 15:07
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    private OrderService orderService;

    public BuyerServiceImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 查询一个订单
     *
     * @param openid  买家的openid
     * @param orderId 订单ID
     * @return OrderDTO
     */
    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid, orderId);
    }

    /**
     * 取消订单
     *
     * @param openid  买家的openid
     * @param orderId 订单ID
     * @return OrderDTO
     */
    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if (orderDTO == null) {
            log.error("【取消订单】查询不到该订单，orderId = {}",
                    orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }

    /**
     * 根据订单ID查询订单，同时判断订单ID是否属于当前用户
     *
     * @param openid 买家的openid
     * @param orderId 订单ID
     * @return OrderDTO
     */
    private OrderDTO checkOrderOwner(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            return null;
        }
        // 判断是否是自己的订单
        if (orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
            log.error("【查询订单】当前查询不是来自订单本人，openid = {}, orderDTO = {}",
                    openid, orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
