package com.tsuki.yuntun.java.app.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品VO
 */
@Data
public class GoodsVO {
    
    /**
     * 商品ID
     */
    private Long id;
    
    /**
     * 商品名称
     */
    private String name;
    
    /**
     * 商品图片
     */
    private String image;
    
    /**
     * 商品图片列表
     */
    private List<String> images;
    
    /**
     * 商品价格
     */
    private BigDecimal price;
    
    /**
     * 原价
     */
    private BigDecimal originalPrice;
    
    /**
     * 商品描述
     */
    private String description;
    
    /**
     * 分类ID
     */
    private Long categoryId;
    
    /**
     * 销量
     */
    private Integer sales;
    
    /**
     * 库存
     */
    private Integer stock;
    
    /**
     * 标签
     */
    private String tag;
    
    /**
     * 是否有规格
     */
    private Boolean hasSpecs;
}

