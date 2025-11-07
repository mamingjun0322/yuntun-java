package com.tsuki.yuntun.java.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品表
 */
@Data
@TableName("goods")
public class Goods implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 分类ID
     */
    private Long categoryId;
    
    /**
     * 商品名称
     */
    private String name;
    
    /**
     * 商品图片（多张用逗号分隔）
     */
    private String images;
    
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
     * 商品详情
     */
    private String detail;
    
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
    
    /**
     * 逻辑删除标志
     */
    @TableLogic
    private Integer deleted;
    
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}

