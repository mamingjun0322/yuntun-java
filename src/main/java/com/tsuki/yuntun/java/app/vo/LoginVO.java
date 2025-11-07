package com.tsuki.yuntun.java.app.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录响应VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO {
    
    /**
     * token
     */
    private String token;
    
    /**
     * 是否新用户
     */
    private Boolean isNewUser;
    
    /**
     * 用户信息
     */
    private UserInfoVO userInfo;
}

