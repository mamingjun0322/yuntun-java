package com.tsuki.yuntun.java.app.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 支付信息VO（返回给小程序）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfoVO {
    
    /**
     * 订单ID
     */
    private Long orderId;
    
    /**
     * 订单号
     */
    private String orderNo;
    
    /**
     * 支付金额
     */
    private BigDecimal amount;
    
    /**
     * 支付链接
     */
    private String paymentUrl;
    
    /**
     * 支付类型
     */
    private String paymentType;
    
    /**
     * 二维码URL（可选）
     */
    private String qrCodeUrl;
}

