package com.tsuki.yuntun.java.admin.vo;

import lombok.Data;

/**
 * 管理员信息VO
 */
@Data
public class AdminInfoVO {
    
    /**
     * 管理员ID
     */
    private Long id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 头像
     */
    private String avatar;
    
    /**
     * 角色 1-超级管理员 2-普通管理员
     */
    private Integer role;
}

