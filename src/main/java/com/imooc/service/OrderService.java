package com.imooc.service;

import com.imooc.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Zhang Pengnian
 * @since 2019-10-15 14:07
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * @param orderDTO 用于在Controller和DAO层传输数据的DTO
     * @return DTO
     */
    OrderDTO create(OrderDTO orderDTO);

    /**
     * 查询单个订单
     *
     * @param orderId 订单ID
     * @return DTO
     */
    OrderDTO findOne(String orderId);

    /**
     * 查询订单列表
     *
     * @param buyerOpenid 买家的Openid
     * @param pageable    分页对象
     * @return DTO的列表
     */
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    /**
     * 取消订单
     *
     * @param orderDTO DTO
     * @return DTO
     */
    OrderDTO cancel(OrderDTO orderDTO);

    /**
     * 完结订单
     *
     * @param orderDTO DTO
     * @return DTO
     */
    OrderDTO finish(OrderDTO orderDTO);

    /**
     * 支付订单
     *
     * @param orderDTO DTO
     * @return DTO
     */
    OrderDTO paid(OrderDTO orderDTO);
}
