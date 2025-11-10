package com.tsuki.yuntun.java.admin.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支付链接VO
 */
@Data
public class PaymentLinkVO {
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 支付金额（元）
     */
    private BigDecimal amount;
    
    /**
     * 收款码图片地址
     */
    private String paymentUrl;
    
    /**
     * 支付类型(wechat-微信 alipay-支付宝)
     */
    private String paymentType;
    
    /**
     * 二维码图片URL（可选，备用字段）
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
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}

