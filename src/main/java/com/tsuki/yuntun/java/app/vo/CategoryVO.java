package com.tsuki.yuntun.java.app.vo;

import lombok.Data;

/**
 * 分类VO
 */
@Data
public class CategoryVO {
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 分类名称
     */
    private String name;
    
    /**
     * 排序
     */
    private Integer sort;
}

