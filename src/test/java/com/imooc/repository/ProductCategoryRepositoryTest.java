package com.imooc.repository;

import com.imooc.model.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author Zhang Pengnian
 * @since 2019-10-13 22:14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest() {
        ProductCategory productCategory = repository.findOne(1);
        System.out.println(productCategory.toString());
    }

    @Test
    public void saveTest() {
        ProductCategory productCategory = new ProductCategory("女生最爱", 4);
        ProductCategory result = repository.save(productCategory);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByCategoryTypeInTest() {
        List<Integer> categoryTypeList = Arrays.asList(1, 2, 3);
        List<ProductCategory> result = repository.findByCategoryTypeIn(categoryTypeList);
        Assert.assertNotEquals(0, result);
        for (ProductCategory pc : result) {
            System.out.println(pc.toString());
        }
    }
}
