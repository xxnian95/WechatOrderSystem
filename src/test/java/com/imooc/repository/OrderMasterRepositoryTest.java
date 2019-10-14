package com.imooc.repository;

import com.imooc.model.OrderMaster;
import com.imooc.utils.SnowFlake;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        orderMaster.setOrderId(String.valueOf(snowFlake.nextId()));
        orderMaster.setBuyerName("黄药师");
        orderMaster.setBuyerPhone("18140010915");
        orderMaster.setBuyerAddress("桃花岛");
        orderMaster.setBuyerOpenid("110");
        orderMaster.setOrderAmount(new BigDecimal(29));

        repository.save(orderMaster);
    }

    @Test
    public void findByBuyerOpenid() {

    }
}
