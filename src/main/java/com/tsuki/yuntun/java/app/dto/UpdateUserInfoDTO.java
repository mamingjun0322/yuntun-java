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
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 性别 (0-未知 1-男 2-女)
     */
    private Integer gender;
    
    /**
     * 生日 (格式：YYYY-MM-DD)
     */
    private String birthday;
}

