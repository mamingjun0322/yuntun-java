package com.tsuki.yuntun.java.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 轮播图DTO
 */
@Data
public class BannerDTO {
    
    /**
     * 图片地址
     */
    @NotBlank(message = "图片地址不能为空")
    private String image;
    
    /**
     * 跳转链接
     */
    private String url;
    
    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    private Integer sort;
    
    /**
     * 状态 0-禁用 1-启用
     */
    @NotNull(message = "状态不能为空")
    private Integer status;
}
