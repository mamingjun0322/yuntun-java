package com.tsuki.yuntun.java.app.vo;

import lombok.Data;

/**
 * 轮播图VO
 */
@Data
public class BannerVO {
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 图片地址
     */
    private String image;
    
    /**
     * 跳转链接
     */
    private String url;
}

