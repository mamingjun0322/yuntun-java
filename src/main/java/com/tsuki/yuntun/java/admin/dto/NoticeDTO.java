package com.tsuki.yuntun.java.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 通知DTO
 */
@Data
public class NoticeDTO {
    
    /**
     * ID（更新时需要）
     */
    private Long id;
    
    /**
     * 通知内容
     */
    @NotBlank(message = "通知内容不能为空")
    private String content;
    
    /**
     * 状态(1-启用 0-禁用)
     */
    private Integer status;
}
