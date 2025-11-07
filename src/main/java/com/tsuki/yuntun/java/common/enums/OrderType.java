package com.tsuki.yuntun.java.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单类型枚举
 */
@Getter
@AllArgsConstructor
public enum OrderType {
    
    /**
     * 堂食
     */
    DINE_IN(1, "堂食"),
    
    /**
     * 外卖
     */
    TAKEOUT(2, "外卖");
    
    private final Integer code;
    private final String desc;
    
    public static OrderType of(Integer code) {
        if (code == null) {
            return null;
        }
        for (OrderType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return null;
    }
}

