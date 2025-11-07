package com.tsuki.yuntun.java.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付方式枚举
 */
@Getter
@AllArgsConstructor
public enum PayType {
    
    /**
     * 微信支付
     */
    WECHAT(1, "微信支付"),
    
    /**
     * 支付宝支付
     */
    ALIPAY(2, "支付宝支付"),
    
    /**
     * 余额支付
     */
    BALANCE(3, "余额支付");
    
    private final Integer code;
    private final String desc;
    
    public static PayType of(Integer code) {
        if (code == null) {
            return null;
        }
        for (PayType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return null;
    }
}

