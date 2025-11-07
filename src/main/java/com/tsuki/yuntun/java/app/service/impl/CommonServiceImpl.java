package com.tsuki.yuntun.java.app.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.tsuki.yuntun.java.common.exception.BusinessException;
import com.tsuki.yuntun.java.app.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 通用Service实现类
 */
@Slf4j
@Service
public class CommonServiceImpl implements CommonService {
    
    @Value("${app.upload.path}")
    private String uploadPath;
    
    @Value("${app.upload.max-size}")
    private long maxFileSize;
    
    @Value("${app.upload.url-prefix}")
    private String urlPrefix;
    
    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png", ".gif", ".bmp"};
    
    @Override
    public String uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("文件不能为空");
        }
        
        // 检查文件大小
        if (file.getSize() > maxFileSize) {
            throw new BusinessException("文件大小不能超过" + (maxFileSize / 1024 / 1024) + "MB");
        }
        
        // 获取原始文件名
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new BusinessException("文件名无效");
        }
        
        // 检查文件扩展名
        String extension = FileUtil.extName(originalFilename).toLowerCase();
        boolean isAllowed = false;
        for (String allowedExt : ALLOWED_EXTENSIONS) {
            if (allowedExt.equals("." + extension)) {
                isAllowed = true;
                break;
            }
        }
        
        if (!isAllowed) {
            throw new BusinessException("不支持的文件类型");
        }
        
        try {
            // 构建存储路径：uploadPath/yyyy-MM-dd/
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String dir = uploadPath + File.separator + date;
            
            // 创建目录（使用绝对路径）
            File dirFile = new File(dir);
            if (!dirFile.exists()) {
                boolean created = dirFile.mkdirs();
                if (!created) {
                    throw new BusinessException("创建上传目录失败");
                }
                log.info("创建上传目录：{}", dir);
            }
            
            // 生成新文件名
            String newFilename = IdUtil.simpleUUID() + "." + extension;
            File dest = new File(dirFile, newFilename);
            
            // 保存文件
            file.transferTo(dest);
            
            // 返回访问URL：/uploads/yyyy-MM-dd/filename.ext
            String url = urlPrefix + "/" + date + "/" + newFilename;
            
            log.info("文件上传成功，保存路径：{}，访问URL：{}", dest.getAbsolutePath(), url);
            return url;
            
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new BusinessException("文件上传失败：" + e.getMessage());
        }
    }
}

