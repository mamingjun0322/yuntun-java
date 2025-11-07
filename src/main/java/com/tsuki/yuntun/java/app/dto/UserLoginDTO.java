package com.tsuki.yuntun.java.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户登录DTO
 */
@Data
public class UserLoginDTO {
    
    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String phone;
    
    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String code;
}

