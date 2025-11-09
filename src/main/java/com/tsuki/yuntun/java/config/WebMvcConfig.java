package com.tsuki.yuntun.java.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * Web MVC 配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    @Value("${app.upload.path}")
    private String uploadPath;
    
    /**
     * 配置静态资源映射
     * 将 /uploads/** 映射到实际的文件目录
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 判断是否为绝对路径
        String absolutePath;
        if (uploadPath.startsWith("/") || uploadPath.matches("^[A-Za-z]:.*")) {
            // 绝对路径，直接使用
            absolutePath = uploadPath + File.separator;
        } else {
            // 相对路径，使用项目根目录
            String projectPath = System.getProperty("user.dir");
            absolutePath = projectPath + File.separator + uploadPath + File.separator;
        }
        
        // 映射 /uploads/** 到实际文件目录
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + absolutePath);
    }
}

