package com.imooc.service.impl;

import com.imooc.enums.ProductStatusEnum;
import com.imooc.model.ProductInfo;
import com.imooc.repository.ProductInfoRepository;
import com.imooc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zhang Pengnian
 * @since 2019-10-14 13:19
 */
@Service
public class ProductServiceImpl implements ProductService {

    private ProductInfoRepository repository;

    @Autowired
    public void setRepository(ProductInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    /**
     * 客户端查询在架的所有商品的列表
     *
     * @return 在架的商品列表
     */
    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }
}
