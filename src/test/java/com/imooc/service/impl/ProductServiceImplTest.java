package com.imooc.service.impl;

import com.imooc.enums.ProductStatusEnum;
import com.imooc.model.ProductInfo;
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
import java.util.List;

/**
 * @author Zhang Pengnian
 * @since 2019-10-14 13:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    private ProductServiceImpl service;

    private SnowFlake snowFlake;

    @Autowired
    public void setService(ProductServiceImpl service) {
        this.service = service;
    }

    @Autowired
    public void setSnowFlake(SnowFlake snowFlake) {
        this.snowFlake = snowFlake;
    }

    @Test
    public void findOne() {
        ProductInfo productInfo = service.findOne("123456");
        Assert.assertEquals("123456", productInfo.getProductId());
    }

    @Test
    public void findByProductNameTest() {
        ProductInfo productInfo = service.findByProductName("芝士焗扇贝");
        Assert.assertEquals("芝士焗扇贝", productInfo.getProductName());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoList = service.findUpAll();
        Assert.assertNotEquals(0, productInfoList.size());
    }

    @Test
    public void findAll() {
        PageRequest request = new PageRequest(0, 2);
        Page<ProductInfo> productInfoPage = service.findAll(request);
        Assert.assertNotEquals(0, productInfoPage.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(String.valueOf(snowFlake.nextId("Product")));
        productInfo.setProductName("西班牙海鲜饭");
        productInfo.setProductPrice(new BigDecimal("29.9"));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("西班牙海鲜饭（Paella，音译为巴埃加），西餐三大名菜之一，与法国蜗牛、意大利面齐名。西班牙海鲜饭卖相绝佳，黄澄澄的饭粒出自名贵的香料藏红花，饭中点缀着无数虾子、螃蟹、黑蚬、蛤、牡蛎、鱿鱼……热气腾腾，令人垂涎。");
        productInfo.setProductIcon("https://gss0.bdstatic.com/94o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=2de72c2923a446236ac7ad30f94b196b/574e9258d109b3dedc47b23bcfbf6c81810a4cec.jpg");
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo.setCategoryType(2);
        ProductInfo result = service.save(productInfo);
        Assert.assertNotNull(result);
    }
}
