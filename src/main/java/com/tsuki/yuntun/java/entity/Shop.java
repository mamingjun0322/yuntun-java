package com.tsuki.yuntun.java.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 店铺表
 */
@Data
@TableName("shop")
public class Shop implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 店铺名称
     */
    private String name;
    
    /**
     * 店铺图片
     */
    private String image;
    
    /**
     * 联系电话
     */
    private String phone;
    
    /**
     * 店铺地址
     */
    private String address;
    
    /**
     * 纬度
     */
    private BigDecimal latitude;
    
    /**
     * 经度
     */
    private BigDecimal longitude;
    
    /**
     * 营业时间
     */
    private String businessHours;
    
    /**
     * 配送范围（公里）
     */
    private BigDecimal deliveryRange;
    
    /**
     * 最低配送金额
     */
    private BigDecimal minDeliveryAmount;
    
    /**
     * 配送费
     */
    private BigDecimal deliveryFee;
    
    /**
     * 打包费
     */
    private BigDecimal packingFee;
    
    /**
     * 店铺介绍
     */
    private String intro;
    
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

