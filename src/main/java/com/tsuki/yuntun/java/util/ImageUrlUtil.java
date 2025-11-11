package com.tsuki.yuntun.java.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 图片URL处理工具类
 */
@Component
public class ImageUrlUtil {
    
    @Value("${app.image.base-url:}")
    private String imageBaseUrl;
    
    @Value("${server.port}")
    private String serverPort;
    
    /**
     * 将相对路径转换为完整URL
     * 如果已经是完整URL（以http开头），则直接返回
     */
    public String toFullUrl(String relativePath) {
        if (relativePath == null || relativePath.isEmpty()) {
            return "";
        }
        
        // 已经是完整URL
        if (relativePath.startsWith("http://") || relativePath.startsWith("https://")) {
            return relativePath;
        }
        
        // 使用配置的基础URL
        if (imageBaseUrl != null && !imageBaseUrl.isEmpty()) {
            return imageBaseUrl + relativePath;
        }
        
        // 默认使用 localhost
        return "http://localhost:" + serverPort + relativePath;
    }
    
    /**
     * 将逗号分隔的图片路径字符串转换为完整URL列表
     */
    public List<String> toFullUrlList(String imagesStr) {
        if (imagesStr == null || imagesStr.isEmpty()) {
            return List.of();
        }
        
        return Arrays.stream(imagesStr.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(this::toFullUrl)
                .collect(Collectors.toList());
    }
    
    /**
     * 从图片列表获取第一张图片的完整URL
     */
    public String getFirstImageUrl(String imagesStr) {
        List<String> urls = toFullUrlList(imagesStr);
        return urls.isEmpty() ? "" : urls.get(0);
    }
}
