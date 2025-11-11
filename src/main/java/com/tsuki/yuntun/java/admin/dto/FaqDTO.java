package com.tsuki.yuntun.java.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 常见问题DTO
 */
@Data
public class FaqDTO {
    
    /**
     * ID（更新时需要）
     */
    private Long id;
    
    /**
     * 问题
     */
    @NotBlank(message = "问题不能为空")
    private String question;
    
    /**
     * 答案
     */
    @NotBlank(message = "答案不能为空")
    private String answer;
    
    /**
     * 分类
     */
    private String category;
    
    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 状态(1-启用 0-禁用)
     */
    private Integer status;
}
