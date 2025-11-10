package com.tsuki.yuntun.java.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 店铺配置DTO
 */
@Data
public class ShopConfigDTO {
    
    /**
     * 店铺名称
     */
    @NotBlank(message = "店铺名称不能为空")
    private String shopName;
    
    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话不能为空")
    private String phone;
    
    /**
     * 店铺地址
     */
    @NotBlank(message = "店铺地址不能为空")
    private String address;
    
    /**
     * 营业时间
     */
    private String businessHours;
    
    /**
     * 店铺介绍
     */
    private String intro;
    
    /**
     * 配送范围(km)
     */
    @NotNull(message = "配送范围不能为空")
    private BigDecimal deliveryRange;
    
    /**
     * 最低配送金额
     */
    @NotNull(message = "最低配送金额不能为空")
    private BigDecimal minDeliveryAmount;
    
    /**
     * 配送费
     */
    @NotNull(message = "配送费不能为空")
    private BigDecimal deliveryFee;
    
    /**
     * 打包费
     */
    @NotNull(message = "打包费不能为空")
    private BigDecimal packingFee;
}

