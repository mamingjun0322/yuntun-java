package com.tsuki.yuntun.java.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单状态枚举
 */
@Getter
@AllArgsConstructor
public enum OrderStatus {
    
    /**
     * 待支付
     */
    PENDING(1, "待支付"),
    
    /**
     * 制作中
     */
    PREPARING(2, "制作中"),
    
    /**
     * 配送中
     */
    DELIVERING(3, "配送中"),
    
    /**
     * 已完成
     */
    COMPLETED(4, "已完成"),
    
    /**
     * 已取消
     */
    CANCELLED(5, "已取消");
    
    private final Integer code;
    private final String desc;
    
    /**
     * 根据code获取枚举
     */
    public static OrderStatus of(Integer code) {
        if (code == null) {
            return null;
        }
        for (OrderStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return null;
    }
}

