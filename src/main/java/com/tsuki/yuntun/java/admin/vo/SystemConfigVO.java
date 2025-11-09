package com.tsuki.yuntun.java.admin.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统配置VO
 */
@Data
public class SystemConfigVO {
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 配置键
     */
    private String configKey;
    
    /**
     * 配置值
     */
    private String configValue;
    
    /**
     * 配置描述
     */
    private String configDesc;
    
    /**
     * 配置类型(string/number/boolean)
     */
    private String configType;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}

