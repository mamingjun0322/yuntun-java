package com.tsuki.yuntun.java.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 分类DTO
 */
@Data
public class CategoryDTO {
    
    /**
     * 分类名称
     */
    @NotBlank(message = "分类名称不能为空")
    private String name;
    
    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    private Integer sort;
    
    /**
     * 状态 0-禁用 1-启用
     */
    private Integer status;
}
