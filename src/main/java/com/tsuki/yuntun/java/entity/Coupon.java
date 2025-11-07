package com.tsuki.yuntun.java.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 优惠券表
 */
@Data
@TableName("coupon")
public class Coupon implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 优惠券名称
     */
    private String name;
    
    /**
     * 优惠金额
     */
    private BigDecimal discount;
    
    /**
     * 最低消费金额
     */
    private BigDecimal minAmount;
    
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
    
    /**
     * 库存
     */
    private Integer stock;
    
    /**
     * 状态 0-下架 1-上架
     */
    private Integer status;
    
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

