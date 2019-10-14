package com.imooc.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品（包含类目）
 *
 * @author Zhang Pengnian
 * @since 2019-10-14 16:05
 */
@Data
public class ProductVO {

    /**
     * 类目名称
     */
    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;



}
