package com.tsuki.yuntun.java.app.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 通用Service
 */
public interface CommonService {
    
    /**
     * 上传文件
     */
    String uploadFile(MultipartFile file);
    
    /**
     * 生成小程序码
     * @param tableNo 桌位号
     * @param page 跳转页面路径（可选，默认为首页）
     * @return 小程序码图片URL
     */
    String generateQrCode(String tableNo, String page);
}

