package com.tsuki.yuntun.java.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 发送消息DTO
 */
@Data
public class SendMessageDTO {
    
    /**
     * 消息内容
     */
    @NotBlank(message = "消息内容不能为空")
    private String content;
}
