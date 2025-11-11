package com.tsuki.yuntun.java.app.vo;

import lombok.Data;

/**
 * 常见问题VO
 */
@Data
public class FaqVO {
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 问题
     */
    private String question;
    
    /**
     * 答案
     */
    private String answer;
    
    /**
     * 分类
     */
    private String category;
}
