package com.imooc.service.impl;

import com.imooc.dto.OrderDTO;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.model.OrderDetail;
import com.imooc.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhang Pengnian
 * @since 2019-10-15 18:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    private OrderServiceImpl orderService;

    private ProductServiceImpl productService;

    private SnowFlake snowFlake;

    @Autowired
    public void setOrderService(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setSnowFlake(SnowFlake snowFlake) {
        this.snowFlake = snowFlake;
    }

    @Autowired
    public void setProductService(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @Test
    public void createTest() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("武当山");
        orderDTO.setBuyerName("张翠山");
        orderDTO.setBuyerPhone("12312341234");
        orderDTO.setBuyerOpenid("zhangcuishan");
        orderDTO.setOrderId(snowFlake.nextId("Order"));

        /*
         * 购物车
         * 1. 皮蛋瘦肉粥 × 2
         * 2. 西班牙海鲜饭 × 1
         */
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("371182675978813440");
        o1.setProductQuantity(2);
        OrderDetail o2 = new OrderDetail();
        o2.setProductId("381655388725837824");
        o2.setProductQuantity(1);
        orderDetailList.add(o1);
        orderDetailList.add(o2);
        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        log.info("【创建订单】result={}", result);
    }

    @Test
    public void createTest2() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("峨眉山");
        orderDTO.setBuyerName("周芷若");
        orderDTO.setBuyerPhone("4008820820");
        orderDTO.setBuyerOpenid("zhouzhiruo");
        orderDTO.setOrderId(snowFlake.nextId("Order"));

        /*
         * 购物车
         * 1. 鳗鱼饭 × 1
         * 2. 番茄牛肉粥 × 1
         */
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId(productService.findByProductName("鳗鱼饭").getProductId());
        o1.setProductQuantity(1);
        OrderDetail o2 = new OrderDetail();
        o2.setProductId(productService.findByProductName("番茄牛肉粥").getProductId());
        o2.setProductQuantity(1);
        orderDetailList.add(o1);
        orderDetailList.add(o2);
        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        log.info("【创建订单】result={}", result);
    }

    @Test
    public void findOneTest() {
        String orderId = "381591967615418368";
        OrderDTO result = orderService.findOne(orderId);
        log.info("【查询单个订单】result={}", result);
        Assert.assertEquals(orderId, result.getOrderId());
    }

    @Test
    public void findListTest() {
        PageRequest request = new PageRequest(0, 10);
        Page<OrderDTO> orderDTOPage =
                orderService.findList("zhangcuishan", request);
        Assert.assertEquals(3, orderDTOPage.getTotalElements());
    }

    @Test
    public void cancelTest() {
        String orderId = "381888827156594688";
        OrderDTO orderDto = orderService.findOne(orderId);
        OrderDTO result = orderService.cancel(orderDto);
        Assert.assertNotNull(result);
        Assert.assertEquals(OrderStatusEnum.CANCELED.getCode(),
                result.getOrderStatus());
    }

    @Test
    public void finish() {
    }

    @Test
    public void paid() {
    }
}
