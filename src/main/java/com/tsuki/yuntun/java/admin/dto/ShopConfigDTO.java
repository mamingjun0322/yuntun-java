package com.tsuki.yuntun.java.admin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 店铺配置DTO
 */
@Data
public class ShopConfigDTO {
    
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

