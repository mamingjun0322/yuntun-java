package com.tsuki.yuntun.java.app.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品详情VO
 */
@Data
public class GoodsDetailVO {
    
    /**
     * 商品ID
     */
    private Long id;
    
    /**
     * 商品名称
     */
    private String name;
    
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
     * 商品详情
     */
    private String detail;
    
    /**
     * 规格列表
     */
    private List<SpecsVO> specsList;
    
    @Data
    public static class SpecsVO {
        /**
         * 规格名称
         */
        private String name;
        
        /**
         * 规格选项列表
         */
        private List<SpecsOptionVO> options;
    }
    
    @Data
    public static class SpecsOptionVO {
        /**
         * 选项ID
         */
        private Long id;
        
        /**
         * 选项值
         */
        private String value;
        
        /**
         * 价格差异
         */
        private BigDecimal price;
    }
}

