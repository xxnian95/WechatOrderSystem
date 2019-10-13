package com.imooc.repository;

import com.imooc.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Zhang Pengnian
 * @since 2019-10-13 22:12
 */
public interface ProductCategoryRepository
        extends JpaRepository<ProductCategory, Integer> {


}
