package com.imooc.service.impl;

import com.imooc.enums.ProductStatusEnum;
import com.imooc.model.ProductInfo;
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

    @Autowired
    public void setService(ProductServiceImpl service) {
        this.service = service;
    }

    @Test
    public void findOne() {
        ProductInfo productInfo = service.findOne("123456");
        Assert.assertEquals("123456", productInfo.getProductId());
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
        productInfo.setProductId("123457");
        productInfo.setProductName("番茄牛肉粥");
        productInfo.setProductPrice(new BigDecimal("5.9"));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("很好喝的番茄牛肉粥");
        productInfo.setProductIcon("https://gss0.bdstatic.com/94o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=2de72c2923a446236ac7ad30f94b196b/574e9258d109b3dedc47b23bcfbf6c81810a4cec.jpg");
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo.setCategoryType(2);
        ProductInfo result = service.save(productInfo);
        Assert.assertNotNull(result);
    }
}
