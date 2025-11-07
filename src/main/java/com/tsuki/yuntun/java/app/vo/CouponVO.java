package com.tsuki.yuntun.java.app.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 优惠券VO
 */
@Data
public class CouponVO {
    
    /**
     * 优惠券ID
     */
    private Long id;
    
    /**
     * 优惠券名称
     */
    private String name;
    
    /**
     * 优惠金额
     */
    private BigDecimal discount;
    
    /**
     * 最低消费金额
     */
    private BigDecimal minAmount;
    
    /**
     * 过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expireTime;
    
    /**
     * 状态（用户优惠券）
     */
    private Integer status;
}

