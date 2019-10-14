package com.imooc.repository;

import com.imooc.model.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Zhang Pengnian
 * @since 2019-10-14 09:53
 */
public interface ProductInfoRepository
        extends JpaRepository<ProductInfo, String> {

    /**
     * 通过商品状态查询商品列表
     *
     * @param productStatus 商品状态
     * @return 商品列表
     */
    List<ProductInfo> findByProductStatus(Integer productStatus);

}
