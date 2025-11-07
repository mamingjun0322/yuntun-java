package com.tsuki.yuntun.java.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品DTO（后台管理）
 */
@Data
public class GoodsDTO {
    
    /**
     * 分类ID
     */
    @NotNull(message = "分类ID不能为空")
    private Long categoryId;
    
    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空")
    private String name;
    
    /**
     * 商品图片
     */
    private String images;
    
    /**
     * 商品价格
     */
    @NotNull(message = "商品价格不能为空")
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
     * 商品详情
     */
    private String detail;
    
    /**
     * 库存
     */
    @NotNull(message = "库存不能为空")
    private Integer stock;
    
    /**
     * 标签
     */
    private String tag;
    
    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 状态 0-下架 1-上架
     */
    private Integer status;
    
    /**
     * 是否有规格
     */
    private Boolean hasSpecs;
}

