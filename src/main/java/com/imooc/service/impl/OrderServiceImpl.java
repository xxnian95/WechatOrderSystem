package com.imooc.service.impl;

import com.imooc.dto.CartDTO;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.model.OrderDetail;
import com.imooc.model.OrderMaster;
import com.imooc.model.ProductInfo;
import com.imooc.repository.OrderDetailRepository;
import com.imooc.repository.OrderMasterRepository;
import com.imooc.service.OrderService;
import com.imooc.service.ProductService;
import com.imooc.utils.CopyUtils;
import com.imooc.utils.SnowFlake;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    @Transactional
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
        CopyUtils.copyProperties(orderDTO, orderMaster);
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
        return null;
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
        return null;
    }

    /**
     * 取消订单
     *
     * @param orderDTO DTO
     * @return DTO
     */
    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    /**
     * 完结订单
     *
     * @param orderDTO DTO
     * @return DTO
     */
    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    /**
     * 支付订单
     *
     * @param orderDTO DTO
     * @return DTO
     */
    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }
}
