package com.tsuki.yuntun.java.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 支付链接DTO
 */
@Data
public class PaymentLinkDTO {
    
    /**
     * 支付金额（元）
     */
    @NotNull(message = "支付金额不能为空")
    private BigDecimal amount;
    
    /**
     * 支付链接URL
     */
    @NotBlank(message = "支付链接不能为空")
    private String paymentUrl;
    
    /**
     * 支付类型(wechat-微信 alipay-支付宝)
     */
    private String paymentType;
    
    /**
     * 二维码图片URL（可选）
     */
    private String qrCodeUrl;
    
    /**
     * 描述说明
     */
    private String description;
    
    /**
     * 状态(1-启用 0-禁用)
     */
    private Integer status;
    
    /**
     * 排序
     */
    private Integer sort;
}

