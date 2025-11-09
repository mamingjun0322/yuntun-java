package com.tsuki.yuntun.java.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支付链接配置表
 */
@Data
@TableName("payment_link")
public class PaymentLink implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 支付金额（元）
     */
    private BigDecimal amount;
    
    /**
     * 支付链接URL
     */
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
    
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}

