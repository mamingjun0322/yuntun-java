package com.tsuki.yuntun.java.app.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tsuki.yuntun.java.common.exception.BusinessException;
import com.tsuki.yuntun.java.app.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
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
    
    @Value("${wx.mini.appid}")
    private String wxAppId;
    
    @Value("${wx.mini.secret}")
    private String wxAppSecret;
    
    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png", ".gif", ".bmp"};
    
    // 微信API接口
    private static final String WX_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    private static final String WX_QRCODE_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s";
    
    // Access Token 缓存
    private String accessToken;
    private long tokenExpireTime;
    
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
            
            // 判断是否为绝对路径
            String dir;
            if (uploadPath.startsWith("/") || uploadPath.matches("^[A-Za-z]:.*")) {
                // 绝对路径（Linux: /data/uploads 或 Windows: D:/uploads），直接使用
                dir = uploadPath + File.separator + date;
            } else {
                // 相对路径（uploads），使用项目根目录
                String projectPath = System.getProperty("user.dir");
                dir = projectPath + File.separator + uploadPath + File.separator + date;
            }
            
            // 创建目录（使用绝对路径）
            File dirFile = new File(dir);
            if (!dirFile.exists()) {
                boolean created = dirFile.mkdirs();
                if (!created) {
                    log.error("创建上传目录失败：{}", dir);
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
    
    @Override
    public String generateQrCode(String tableNo, String page) {
        if (tableNo == null || tableNo.trim().isEmpty()) {
            throw new BusinessException("桌位号不能为空");
        }
        
        try {
            // 获取Access Token
            String token = getAccessToken();
            
            // 构建请求参数
            JSONObject params = new JSONObject();
            // scene 参数（最多32个字符），使用简写节省字符数
            params.set("scene", "t=" + tableNo);
            // 默认跳转到首页，如果需要可以自定义页面
            params.set("page", page != null && !page.isEmpty() ? page : "pages/index/index");
            // 二维码宽度，单位px，最小280，最大1280
            params.set("width", 430);
            // 自动配置线条颜色
            params.set("auto_color", false);
            // 是否需要透明底色
            params.set("is_hyaline", true);
            
            // 调用微信接口生成小程序码
            String qrCodeUrl = String.format(WX_QRCODE_URL, token);
            byte[] qrCodeBytes = HttpUtil.createPost(qrCodeUrl)
                    .body(params.toString())
                    .execute()
                    .bodyBytes();
            
            // 检查返回结果是否为错误信息
            if (qrCodeBytes.length < 1000) {
                // 小于1KB可能是错误信息，尝试解析
                String errorMsg = new String(qrCodeBytes);
                JSONObject errorJson = JSONUtil.parseObj(errorMsg);
                if (errorJson.containsKey("errcode") && errorJson.getInt("errcode") != 0) {
                    log.error("生成小程序码失败：{}", errorMsg);
                    throw new BusinessException("生成小程序码失败：" + errorJson.getStr("errmsg"));
                }
            }
            
            // 保存小程序码图片
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            
            // 判断是否为绝对路径
            String dir;
            if (uploadPath.startsWith("/") || uploadPath.matches("^[A-Za-z]:.*")) {
                dir = uploadPath + File.separator + date;
            } else {
                String projectPath = System.getProperty("user.dir");
                dir = projectPath + File.separator + uploadPath + File.separator + date;
            }
            
            // 创建目录
            File dirFile = new File(dir);
            if (!dirFile.exists()) {
                boolean created = dirFile.mkdirs();
                if (!created) {
                    log.error("创建目录失败：{}", dir);
                    throw new BusinessException("创建目录失败");
                }
            }
            
            // 生成文件名：qrcode_桌号_时间戳.png
            String filename = "qrcode_" + tableNo + "_" + System.currentTimeMillis() + ".png";
            File qrCodeFile = new File(dirFile, filename);
            
            // 保存文件
            try (FileOutputStream fos = new FileOutputStream(qrCodeFile)) {
                fos.write(qrCodeBytes);
            }
            
            // 返回访问URL
            String url = urlPrefix + "/" + date + "/" + filename;
            log.info("生成小程序码成功，桌位号：{}，保存路径：{}，访问URL：{}", tableNo, qrCodeFile.getAbsolutePath(), url);
            
            return url;
            
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("生成小程序码失败", e);
            throw new BusinessException("生成小程序码失败：" + e.getMessage());
        }
    }
    
    /**
     * 获取微信Access Token
     * 使用缓存机制，避免频繁请求
     */
    private synchronized String getAccessToken() {
        // 检查缓存是否有效（提前5分钟刷新）
        if (accessToken != null && System.currentTimeMillis() < tokenExpireTime - 300000) {
            return accessToken;
        }
        
        try {
            // 请求微信接口获取Access Token
            String url = String.format(WX_TOKEN_URL, wxAppId, wxAppSecret);
            String response = HttpUtil.get(url);
            
            JSONObject json = JSONUtil.parseObj(response);
            
            // 检查是否有错误
            if (json.containsKey("errcode")) {
                Integer errcode = json.getInt("errcode");
                if (errcode != null && errcode != 0) {
                    String errmsg = json.getStr("errmsg");
                    log.error("获取微信Access Token失败，errcode: {}, errmsg: {}", errcode, errmsg);
                    throw new BusinessException("获取微信Access Token失败：" + errmsg);
                }
            }
            
            // 获取Access Token和过期时间
            accessToken = json.getStr("access_token");
            Integer expiresIn = json.getInt("expires_in");
            
            if (accessToken == null || accessToken.isEmpty()) {
                throw new BusinessException("获取微信Access Token失败：返回结果为空");
            }
            
            // 计算过期时间（毫秒）
            tokenExpireTime = System.currentTimeMillis() + (expiresIn != null ? expiresIn * 1000L : 7200000L);
            
            log.info("获取微信Access Token成功，过期时间：{}", new java.util.Date(tokenExpireTime));
            
            return accessToken;
            
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取微信Access Token异常", e);
            throw new BusinessException("获取微信Access Token异常：" + e.getMessage());
        }
    }
}

