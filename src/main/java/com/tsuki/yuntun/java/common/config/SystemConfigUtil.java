package com.tsuki.yuntun.java.common.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tsuki.yuntun.java.app.mapper.SystemConfigMapper;
import com.tsuki.yuntun.java.entity.SystemConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;

/**
 * 系统配置工具类
 * 用于从数据库读取系统配置
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SystemConfigUtil {
    
    private final SystemConfigMapper systemConfigMapper;
    
    /**
     * 系统启动时初始化默认配置
     */
    @PostConstruct
    public void initDefaultConfig() {
        log.info("开始初始化系统配置...");
        
        // 检查并初始化短信配置
        initConfigIfNotExists("sms.expire_time", "300", "短信验证码过期时间（秒）", "number");
        
        // 检查并初始化积分配置
        initConfigIfNotExists("points.sign_in", "10", "每日签到赠送积分", "number");
        initConfigIfNotExists("points.order", "10", "订单赠送积分倍率（每消费1元获得的积分）", "number");
        
        log.info("系统配置初始化完成");
    }
    
    /**
     * 如果配置不存在则初始化
     */
    private void initConfigIfNotExists(String key, String value, String desc, String type) {
        SystemConfig config = systemConfigMapper.selectOne(
            new LambdaQueryWrapper<SystemConfig>().eq(SystemConfig::getConfigKey, key)
        );
        
        if (config == null) {
            config = new SystemConfig();
            config.setConfigKey(key);
            config.setConfigValue(value);
            config.setConfigDesc(desc);
            config.setConfigType(type);
            systemConfigMapper.insert(config);
            log.info("初始化配置：{} = {}", key, value);
        }
    }
    
    /**
     * 获取配置值（字符串）
     */
    public String getConfigValue(String configKey) {
        SystemConfig config = systemConfigMapper.selectOne(
            new LambdaQueryWrapper<SystemConfig>().eq(SystemConfig::getConfigKey, configKey)
        );
        return config != null ? config.getConfigValue() : null;
    }
    
    /**
     * 获取配置值（字符串，带默认值）
     */
    public String getConfigValue(String configKey, String defaultValue) {
        String value = getConfigValue(configKey);
        return value != null ? value : defaultValue;
    }
    
    /**
     * 获取配置值（整数）
     */
    public Integer getConfigValueAsInt(String configKey) {
        String value = getConfigValue(configKey);
        if (value == null) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            log.error("配置值转换失败：{} = {}", configKey, value, e);
            return null;
        }
    }
    
    /**
     * 获取配置值（整数，带默认值）
     */
    public Integer getConfigValueAsInt(String configKey, Integer defaultValue) {
        Integer value = getConfigValueAsInt(configKey);
        return value != null ? value : defaultValue;
    }
    
    /**
     * 获取配置值（Long）
     */
    public Long getConfigValueAsLong(String configKey) {
        String value = getConfigValue(configKey);
        if (value == null) {
            return null;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            log.error("配置值转换失败：{} = {}", configKey, value, e);
            return null;
        }
    }
    
    /**
     * 获取配置值（Long，带默认值）
     */
    public Long getConfigValueAsLong(String configKey, Long defaultValue) {
        Long value = getConfigValueAsLong(configKey);
        return value != null ? value : defaultValue;
    }
    
    /**
     * 获取配置值（BigDecimal）
     */
    public BigDecimal getConfigValueAsDecimal(String configKey) {
        String value = getConfigValue(configKey);
        if (value == null) {
            return null;
        }
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            log.error("配置值转换失败：{} = {}", configKey, value, e);
            return null;
        }
    }
    
    /**
     * 获取配置值（BigDecimal，带默认值）
     */
    public BigDecimal getConfigValueAsDecimal(String configKey, BigDecimal defaultValue) {
        BigDecimal value = getConfigValueAsDecimal(configKey);
        return value != null ? value : defaultValue;
    }
    
    /**
     * 获取配置值（Boolean）
     */
    public Boolean getConfigValueAsBoolean(String configKey) {
        String value = getConfigValue(configKey);
        if (value == null) {
            return null;
        }
        return Boolean.parseBoolean(value);
    }
    
    /**
     * 获取配置值（Boolean，带默认值）
     */
    public Boolean getConfigValueAsBoolean(String configKey, Boolean defaultValue) {
        Boolean value = getConfigValueAsBoolean(configKey);
        return value != null ? value : defaultValue;
    }
    
    // ========================================
    // 业务配置快捷方法
    // ========================================
    
    /**
     * 获取短信验证码过期时间（秒）
     */
    public Integer getSmsExpireTime() {
        return getConfigValueAsInt("sms.expire_time", 300);
    }
    
    /**
     * 获取签到赠送积分
     */
    public Integer getSignInPoints() {
        return getConfigValueAsInt("points.sign_in", 10);
    }
    
    /**
     * 获取订单赠送积分倍率
     */
    public Integer getOrderPointsRate() {
        return getConfigValueAsInt("points.order", 10);
    }
}
