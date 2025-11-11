package com.tsuki.yuntun.java.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 客服消息表
 */
@Data
@TableName("customer_service_message")
public class CustomerServiceMessage implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
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
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
