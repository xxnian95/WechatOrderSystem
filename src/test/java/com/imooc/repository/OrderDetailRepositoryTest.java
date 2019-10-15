package com.imooc.repository;

import com.imooc.model.OrderDetail;
import com.imooc.utils.SnowFlake;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Zhang Pengnian
 * @since 2019-10-15 12:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    private OrderDetailRepository repository;

    private SnowFlake snowFlake;

    @Autowired
    public void setRepository(OrderDetailRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setSnowFlake(SnowFlake snowFlake) {
        this.snowFlake = snowFlake;
    }

    @Test
    public void saveTest() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId(snowFlake.nextId("Detail"));
        orderDetail.setOrderId(snowFlake.nextId("Order"));
        orderDetail.setProductIcon("http://xxx.jpg");
        orderDetail.setProductId(snowFlake.nextId("Product"));
        orderDetail.setProductName("商品名称2");
        orderDetail.setProductPrice(new BigDecimal(100));
        orderDetail.setProductQuantity(2);

        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetailList
                = repository.findByOrderId("38147940430721833");
        Assert.assertEquals(0, orderDetailList.size());
    }
}
