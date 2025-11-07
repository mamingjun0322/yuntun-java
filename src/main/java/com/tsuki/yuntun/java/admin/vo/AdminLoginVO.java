package com.tsuki.yuntun.java.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理员登录响应VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginVO {
    
    /**
     * token
     */
    private String token;
    
    /**
     * 管理员信息
     */
    private AdminInfoVO adminInfo;
}

