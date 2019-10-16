package com.imooc.service.impl;

import com.imooc.dto.CartDTO;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.model.OrderDetail;
import com.imooc.model.OrderMaster;
import com.imooc.model.ProductInfo;
import com.imooc.repository.OrderDetailRepository;
import com.imooc.repository.OrderMasterRepository;
import com.imooc.service.OrderService;
import com.imooc.service.ProductService;
import com.imooc.utils.OrderMaster2OrderDTOConverter;
import com.imooc.utils.PropertiesCopyUtils;
import com.imooc.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Zhang Pengnian
 * @since 2019-10-15 14:27
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private ProductService productService;

    private OrderDetailRepository orderDetailRepository;

    private OrderMasterRepository orderMasterRepository;

    private SnowFlake snowFlake;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setOrderDetailRepository(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Autowired
    public void setSnowFlake(SnowFlake snowFlake) {
        this.snowFlake = snowFlake;
    }

    @Autowired
    public void setOrderMasterRepository(OrderMasterRepository orderMasterRepository) {
        this.orderMasterRepository = orderMasterRepository;
    }

    /**
     * 创建订单
     * Transactional是因为写入数据时，抛出异常能够自动地回滚事务
     *
     * @param orderDTO 用于在Controller和DAO层传输数据的DTO
     * @return DTO
     */
    @Override
    @Transactional(rollbackOn = {SellException.class})
    public OrderDTO create(OrderDTO orderDTO) {

        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        // 1. 查询商品（数量，价格）
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo
                    = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            // 2. 计算订单的总价
            orderDetail.setProductPrice(productService.findOne(
                    orderDetail.getProductId()).getProductPrice());
            orderAmount = orderDetail.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            // 订单详情入库
            orderDetail.setDetailId(snowFlake.nextId("Detail"));
            orderDetail.setOrderId(orderDTO.getOrderId());
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);
        }

        // 3. 写入订单数据库（OrderMaster和OrderDetail）
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderAmount(orderAmount);
        PropertiesCopyUtils.copyProperties(orderDTO, orderMaster);
        orderMasterRepository.save(orderMaster);

        // 4. 扣库存
        // TODO 分布式锁防止超卖
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList()
                .stream().map(e ->
                        new CartDTO(e.getProductId(), e.getProductQuantity())
                ).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    /**
     * 查询单个订单
     *
     * @param orderId 订单ID
     * @return DTO
     */
    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList =
                orderDetailRepository.findByOrderId(orderId);
        if (orderDetailList == null || orderDetailList.size() == 0) {
            throw new SellException(ResultEnum.DETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        PropertiesCopyUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    /**
     * 查询订单列表
     *
     * @param buyerOpenid 买家的Openid
     * @param pageable    分页对象
     * @return DTO的列表
     */
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage =
                orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        orderMasterPage.getContent();

        List<OrderDTO> orderDTOList =
                OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

        return new PageImpl<>(orderDTOList, pageable,
                orderMasterPage.getTotalElements());

    }

    /**
     * 取消订单
     * Transactional注解没有发挥作用，订单状态修改之后，如果抛出异常，并不会回滚
     * 根据阿里Java规范的报错，不能依赖抛出异常回滚事务，应当显式地指定回滚事务的操作。
     * <p>
     * 在Transactional注解当中指定rollbackOn可以解决这个问题。
     * 可能原因：自己定义的SellException虽然实现了RuntimeException，但是无法触发
     * Transactional注解的自动回滚。
     *
     * @param orderDTO DTO
     * @return DTO
     */
    @Override
    @Transactional(rollbackOn = {SellException.class})
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        // 1. 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【取消订单】订单状态不正确，orderId = {}, orderStatus = {}",
                    orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 2. 修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCELED.getCode());
        PropertiesCopyUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【取消订单】更新失败，orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        // 3. 返还库存
        // (1) 判断订单中是否有商品
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【取消订单】订单中无商品详情，orderDTO = {}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        // (2) 加库存
        // TODO 库存的增加可能存在线程安全问题（丢失某个线程的修改）
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList()
                .stream().map(e ->
                        new CartDTO(e.getProductId(), e.getProductQuantity())
                ).collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        // 4. 退款（如果已支付）
        if (orderDTO.getPayStatus().equals(PayStatusEnum.PAID.getCode())) {
            // TODO 退款
        }

        /*
        这里如果直接返回orderDTO，对象中的订单状态并不会改变。需要重新从数据库中读取信息并
        返回（我也不知道这是为啥）。
         */
        return orderDTO;
    }

    /**
     * 完结订单
     *
     * @param orderDTO DTO
     * @return DTO
     */
    @Override
    @Transactional(rollbackOn = {SellException.class})
    public OrderDTO finish(OrderDTO orderDTO) {
        // 1. 判断订单状态
        // TODO 能够对订单执行finish的条件，除了当前状态时NEW，还应当payStatus为已支付
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态不正确，orderId = {}, orderStatus = {}",
                    orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 2. 修改状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        PropertiesCopyUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【完结订单】更新失败，orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

    /**
     * 支付订单
     *
     * @param orderDTO DTO
     * @return DTO
     */
    @Override
    @Transactional(rollbackOn = {SellException.class})
    public OrderDTO paid(OrderDTO orderDTO) {
        // 1. 判断订状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【支付订单】订单状态不正确，orderId = {}, orderStatus = {}",
                    orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 2. 判断订单的支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.UNPAID.getCode())) {
            log.error("【支付订单】订单支付状态不正确, orderId = {}, payStatus = {}",
                    orderDTO.getOrderId(), orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        // 3. 修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.PAID.getCode());
        OrderMaster orderMaster = new OrderMaster();
        PropertiesCopyUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【支付订单】支付失败，orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }
}
