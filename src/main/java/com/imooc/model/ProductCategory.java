package com.imooc.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author Zhang Pengnian
 * @since 2019-10-13 22:09
 */
@Entity
@DynamicUpdate
@Data
public class ProductCategory {

    /**
     * 类目ID
     */
    @Id
    @GeneratedValue
    private Integer categoryId;

    /**
     * 类目名字
     */
    private String categoryName;

    /**
     * 类目编号
     */
    private int categoryType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, int categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    public ProductCategory(String categoryName, int categoryType,
                           Date createTime, Date updateTime) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
