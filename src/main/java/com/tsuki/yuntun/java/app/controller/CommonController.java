package com.tsuki.yuntun.java.app.controller;

import com.tsuki.yuntun.java.common.Result;
import com.tsuki.yuntun.java.app.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用Controller
 */
@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
public class CommonController {
    
    private final CommonService commonService;
    
    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public Result<Map<String, String>> upload(@RequestParam("file") MultipartFile file) {
        String url = commonService.uploadFile(file);
        Map<String, String> result = new HashMap<>();
        result.put("url", url);
        return Result.success("上传成功", result);
    }
}

