package com.tsuki.yuntun.java.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 管理员DTO
 */
@Data
public class AdminDTO {
    
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;
    
    /**
     * 密码（添加时必填，更新时可选）
     */
    private String password;
    
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
    @NotNull(message = "角色不能为空")
    private Integer role;
    
    /**
     * 状态 0-禁用 1-启用
     */
    private Integer status;
}
