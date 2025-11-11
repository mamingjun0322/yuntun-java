package com.tsuki.yuntun.java.admin.controller;

import com.tsuki.yuntun.java.common.Result;
import com.tsuki.yuntun.java.util.ImageUrlUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * 文件上传Controller
 */
@Slf4j
@Tag(name = "文件上传", description = "文件上传相关接口")
@RestController
@RequestMapping("/admin/upload")
@RequiredArgsConstructor
public class UploadController {
    
    @Value("${app.upload.path}")
    private String uploadPath;
    
    private final ImageUrlUtil imageUrlUtil;
    
    /**
     * 测试静态资源访问
     */
    @Operation(summary = "测试静态资源访问")
    @GetMapping("/test")
    public Result<String> testUpload() {
        String testUrl = "/api/uploads/test.jpg";
        log.info("测试URL：{}", testUrl);
        return Result.success("测试成功，请访问：" + testUrl);
    }
    
    /**
     * 上传图片
     */
    @Operation(summary = "上传图片")
    @PostMapping("/image")
    public Result<String> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("请选择要上传的文件");
        }
        
        // 验证文件类型
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return Result.error("只能上传图片文件");
        }
        
        // 验证文件大小（2MB）
        if (file.getSize() > 2 * 1024 * 1024) {
            return Result.error("图片大小不能超过2MB");
        }
        
        try {
            // 获取绝对路径
            String absolutePath;
            if (uploadPath.startsWith("/") || uploadPath.matches("^[A-Za-z]:.*")) {
                // 绝对路径，直接使用
                absolutePath = uploadPath;
            } else {
                // 相对路径，使用项目根目录
                String projectPath = System.getProperty("user.dir");
                absolutePath = projectPath + File.separator + uploadPath;
            }
            
            // 创建上传目录
            File uploadDir = new File(absolutePath);
            if (!uploadDir.exists()) {
                boolean created = uploadDir.mkdirs();
                if (!created) {
                    log.error("创建上传目录失败：{}", absolutePath);
                    return Result.error("创建上传目录失败");
                }
            }
            
            // 生成文件名
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null && originalFilename.contains(".") 
                ? originalFilename.substring(originalFilename.lastIndexOf(".")) 
                : ".jpg";
            String filename = UUID.randomUUID().toString() + extension;
            
            // 保存文件
            File destFile = new File(uploadDir, filename);
            file.transferTo(destFile);
            
            // 返回访问URL（转换为完整URL，支持小程序访问）
            String relativePath = "/api/uploads/" + filename;
            String fullUrl = imageUrlUtil.toFullUrl(relativePath);
            
            log.info("文件上传成功：{} -> {}", filename, destFile.getAbsolutePath());
            log.info("相对路径：{}", relativePath);
            log.info("完整URL：{}", fullUrl);
            Result<String> result = Result.success(fullUrl);
            log.info("返回结果：{}", result);
            return result;
            
        } catch (IOException e) {
            log.error("文件上传失败", e);
            return Result.error("文件上传失败：" + e.getMessage());
        }
    }
}
