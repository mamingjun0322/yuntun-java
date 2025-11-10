package com.tsuki.yuntun.java.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 静态资源配置
 */
@Configuration
public class ResourceConfig implements WebMvcConfigurer {
    
    @Value("${app.upload.path}")
    private String uploadPath;
    
    @Value("${app.upload.url-prefix}")
    private String urlPrefix;
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置上传文件的静态资源访问
        // 访问路径：/uploads/** 映射到实际目录：file:E:/Tuski/yun-tun/uploads/
        registry.addResourceHandler(urlPrefix + "/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }
}

