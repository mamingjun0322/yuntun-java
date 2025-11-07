package com.tsuki.yuntun.java.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户角色枚举
 */
@Getter
@AllArgsConstructor
public enum UserRole {
    
    /**
     * 超级管理员
     */
    SUPER_ADMIN(1, "超级管理员"),
    
    /**
     * 普通管理员
     */
    ADMIN(2, "普通管理员"),
    
    /**
     * 普通用户
     */
    USER(3, "普通用户");
    
    private final Integer code;
    private final String desc;
    
    public static UserRole of(Integer code) {
        if (code == null) {
            return null;
        }
        for (UserRole role : values()) {
            if (role.code.equals(code)) {
                return role;
            }
        }
        return null;
    }
}

