package com.tsuki.yuntun.java.app.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 公告VO
 */
@Data
public class NoticeVO {
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 公告内容
     */
    private String content;
    
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}

