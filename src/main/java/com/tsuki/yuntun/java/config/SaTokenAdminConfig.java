package com.tsuki.yuntun.java.config;

import cn.dev33.satoken.config.SaTokenConfig;
import com.tsuki.yuntun.java.util.StpAdminUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

/**
 * Sa-Token 管理员端配置
 */
@Configuration
public class SaTokenAdminConfig {
    
    /**
     * 初始化管理员端 StpLogic 配置
     */
    @PostConstruct
    public void initAdminStpLogic() {
        // 手动创建管理员端的配置
        SaTokenConfig config = new SaTokenConfig();
        config.setTokenName("admin-token");
        config.setTimeout(604800); // 7天
        config.setIsConcurrent(false);
        config.setIsShare(false);
        config.setIsReadHeader(true);
        config.setIsReadCookie(false);
        config.setIsReadBody(false);
        config.setTokenStyle("uuid");
        
        // 设置到管理员 StpLogic
        StpAdminUtil.stpLogic.setConfig(config);
    }
}
