package com.imooc.service.impl;

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
 * @since 2019-10-14 00:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    private CategoryServiceImpl categoryService;

    @Autowired
    public void setCategoryService(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @Test
    public void findOne() throws Exception {
        ProductCategory productCategory = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1), productCategory.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> productCategoryList = categoryService.findAll();
        Assert.assertNotEquals(0, productCategoryList.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> productCategoryList =
                categoryService.findByCategoryTypeIn(Arrays.asList(1,2,3));
        Assert.assertNotEquals(0, productCategoryList.size());
    }

    @Test
    public void save() {
        ProductCategory productCategory = new ProductCategory("男生最爱", 5);
        ProductCategory result = categoryService.save(productCategory);
        Assert.assertNotNull(result);
    }
}
