package com.tsuki.yuntun.java.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 回复消息DTO
 */
@Data
public class ReplyMessageDTO {
    
    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;
    
    /**
     * 回复内容
     */
    @NotBlank(message = "回复内容不能为空")
    private String content;
    
    /**
     * 回复的消息ID（可选）
     */
    private Long replyId;
}
