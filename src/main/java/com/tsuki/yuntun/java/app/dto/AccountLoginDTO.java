package com.tsuki.yuntun.java.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 账号密码登录DTO
 */
@Data
public class AccountLoginDTO {
    
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;
    
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}

