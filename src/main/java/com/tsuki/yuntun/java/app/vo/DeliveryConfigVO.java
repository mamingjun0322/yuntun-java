package com.tsuki.yuntun.java.app.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 配送配置VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryConfigVO {
    
    /**
     * 最低配送金额
     */
    private BigDecimal minAmount;
    
    /**
     * 配送费
     */
    private BigDecimal deliveryFee;
    
    /**
     * 打包费
     */
    private BigDecimal packingFee;
    
    /**
     * 配送范围（公里）
     */
    private BigDecimal range;
}

