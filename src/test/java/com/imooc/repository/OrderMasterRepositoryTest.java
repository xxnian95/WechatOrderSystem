package com.imooc.repository;

import com.imooc.model.OrderMaster;
import com.imooc.utils.SnowFlake;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * @author Zhang Pengnian
 * @since 2019-10-15 00:43
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    private OrderMasterRepository repository;

    private SnowFlake snowFlake;

    @Autowired
    public void setRepository(OrderMasterRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setSnowFlake(SnowFlake snowFlake) {
        this.snowFlake = snowFlake;
    }

    @Test
    public void saveTest() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(snowFlake.nextId("Order"));
        orderMaster.setBuyerName("张无忌");
        orderMaster.setBuyerPhone("13339216615");
        orderMaster.setBuyerAddress("光明顶");
        orderMaster.setBuyerOpenid("zhangwuji");
        orderMaster.setOrderAmount(new BigDecimal(58));

        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenid() {
        PageRequest request = new PageRequest(0, 10);
        Page<OrderMaster> result =
                repository.findByBuyerOpenid("zhangwuji", request);
        Assert.assertEquals("张无忌",
                result.getContent().get(0).getBuyerName());
    }
}
