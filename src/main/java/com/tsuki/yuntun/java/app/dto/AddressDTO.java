package com.tsuki.yuntun.java.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 地址DTO
 */
@Data
public class AddressDTO {
    
    /**
     * 联系人姓名
     */
    @NotBlank(message = "联系人姓名不能为空")
    private String name;
    
    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话不能为空")
    private String phone;
    
    /**
     * 省市区
     */
    @NotBlank(message = "省市区不能为空")
    private String region;
    
    /**
     * 详细地址
     */
    @NotBlank(message = "详细地址不能为空")
    private String address;
    
    /**
     * 纬度
     */
    @NotNull(message = "纬度不能为空")
    private BigDecimal latitude;
    
    /**
     * 经度
     */
    @NotNull(message = "经度不能为空")
    private BigDecimal longitude;
    
    /**
     * 是否默认地址
     */
    private Boolean isDefault;
}

