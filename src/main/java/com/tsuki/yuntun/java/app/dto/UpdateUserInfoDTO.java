package com.tsuki.yuntun.java.app.dto;

import lombok.Data;

/**
 * 更新用户信息DTO
 */
@Data
public class UpdateUserInfoDTO {
    
    /**
     * 昵称
     */
    private String nickname;
    
    /**
     * 头像
     */
    private String avatar;
}

