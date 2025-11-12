package com.tsuki.yuntun.java.app.service.impl;

import com.tsuki.yuntun.java.common.config.SystemConfigUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 短信服务示例
 * 展示如何使用数据库配置
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SmsServiceExample {
    
    private final SystemConfigUtil systemConfigUtil;
    
    /**
     * 发送验证码示例
     */
    public void sendVerificationCode(String phone, String code) {
        // 方式1：使用快捷方法
        Integer expireTime = systemConfigUtil.getSmsExpireTime();
        log.info("发送验证码到：{}，验证码：{}，有效期：{}秒", phone, code, expireTime);
        
        // 方式2：使用通用方法
        Integer expireTime2 = systemConfigUtil.getConfigValueAsInt("sms.expire_time", 300);
        log.info("验证码有效期：{}秒", expireTime2);
        
        // TODO: 实际发送短信逻辑
        // smsProvider.send(phone, code, expireTime);
    }
    
    /**
     * 验证验证码示例
     */
    public boolean verifyCode(String phone, String code) {
        Integer expireTime = systemConfigUtil.getSmsExpireTime();
        
        // TODO: 实际验证逻辑
        // 1. 从Redis获取缓存的验证码
        // 2. 检查是否过期（使用expireTime）
        // 3. 验证码是否匹配
        
        log.info("验证码有效期配置：{}秒", expireTime);
        return true;
    }
}
