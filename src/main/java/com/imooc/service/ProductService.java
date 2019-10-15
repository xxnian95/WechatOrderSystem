package com.imooc.service;

import com.imooc.dto.CartDTO;
import com.imooc.model.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Zhang Pengnian
 * @since 2019-10-14 13:04
 */
public interface ProductService {

    /**
     * 查找一个product
     *
     * @param productId 商品ID
     * @return 一个商品信息
     */
    ProductInfo findOne(String productId);

    /**
     * 客户端查询在架的所有商品的列表
     *
     * @return 在架的商品列表
     */
    List<ProductInfo> findUpAll();

    /**
     * 查找全部商品
     * @param pageable 查找分页请求
     * @return Page列表
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 保存商品
     *
     * @param productInfo 单个商品
     * @return 保存的商品
     */
    ProductInfo save(ProductInfo productInfo);

    /**
     * 加库存
     *
     * @param cartDTOList 购物车的列表
     */
    void increaseStock(List<CartDTO> cartDTOList);

    /**
     * 减库存
     *
     * @param cartDTOList 购物车的列表
     */
    void decreaseStock(List<CartDTO> cartDTOList);
}
