package com.imooc.repository;

import com.imooc.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Zhang Pengnian
 * @since 2019-10-13 22:12
 */
public interface ProductCategoryRepository
        extends JpaRepository<ProductCategory, Integer> {

    /**
     * 根据类目编号查找类目
     *
     * @param categoryTypeList 类目编号的列表
     * @return 类目的列表
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
