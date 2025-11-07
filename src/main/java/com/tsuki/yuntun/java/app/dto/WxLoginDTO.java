package com.tsuki.yuntun.java.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 微信登录DTO
 */
@Data
public class WxLoginDTO {
    
    /**
     * 微信登录凭证code
     */
    @NotBlank(message = "code不能为空")
    private String code;
    
    /**
     * 用户昵称（可选）
     */
    private String nickName;
    
    /**
     * 用户头像（可选）
     */
    private String avatar;
}

