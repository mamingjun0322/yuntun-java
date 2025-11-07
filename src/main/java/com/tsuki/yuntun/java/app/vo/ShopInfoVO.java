package com.tsuki.yuntun.java.app.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 店铺信息VO
 */
@Data
public class ShopInfoVO {
    
    /**
     * 店铺ID
     */
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
}

