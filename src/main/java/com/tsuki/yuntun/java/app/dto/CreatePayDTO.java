package com.tsuki.yuntun.java.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 创建支付DTO
 */
@Data
public class CreatePayDTO {
    
    /**
     * 订单号
     */
    @NotBlank(message = "订单号不能为空")
    private String orderId;
    
    /**
     * 支付方式 1-微信 2-支付宝
     */
    @NotNull(message = "支付方式不能为空")
    private Integer payType;
}

