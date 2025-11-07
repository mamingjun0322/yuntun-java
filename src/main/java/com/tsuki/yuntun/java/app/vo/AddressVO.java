package com.tsuki.yuntun.java.app.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 地址VO
 */
@Data
public class AddressVO {
    
    /**
     * 地址ID
     */
    private Long id;
    
    /**
     * 联系人姓名
     */
    private String name;
    
    /**
     * 联系电话
     */
    private String phone;
    
    /**
     * 省市区
     */
    private String region;
    
    /**
     * 详细地址
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
     * 是否默认地址
     */
    private Boolean isDefault;
}

