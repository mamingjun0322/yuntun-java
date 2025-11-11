package com.tsuki.yuntun.java.app.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 客服消息VO
 */
@Data
public class CustomerServiceMessageVO {
    
    /**
     * 消息ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 消息内容
     */
    private String content;
    
    /**
     * 消息类型(1-用户发送 2-客服回复)
     */
    private Integer type;
    
    /**
     * 状态(1-未读 2-已读)
     */
    private Integer status;
    
    /**
     * 回复的消息ID
     */
    private Long replyId;
    
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
