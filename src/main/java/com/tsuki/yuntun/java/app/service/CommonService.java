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
}

