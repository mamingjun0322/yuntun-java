package com.tsuki.yuntun.java.app.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 积分明细VO
 */
@Data
public class PointsHistoryVO {
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 标题
     */
    private String title;
    
    /**
     * 积分变动值
     */
    private Integer points;
    
    /**
     * 类型
     */
    private Integer type;
    
    /**
     * 时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
}

