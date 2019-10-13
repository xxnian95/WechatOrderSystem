package com.imooc.service;

import com.imooc.model.ProductCategory;

import java.util.List;

/**
 * @author Zhang Pengnian
 * @since 2019-10-14 00:21
 */
public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
