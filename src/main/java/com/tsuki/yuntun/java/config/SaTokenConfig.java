package com.tsuki.yuntun.java.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.tsuki.yuntun.java.util.StpAdminUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token配置类
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {
    
    /**
     * 注册Sa-Token拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 管理员端路由拦截器（使用独立的认证体系）
        registry.addInterceptor(new SaInterceptor(handle -> {
            SaRouter.match("/admin/**")
                    .notMatch("/admin/auth/login") // 排除登录接口
                    .check(r -> StpAdminUtil.stpLogic.checkLogin());
        })).addPathPatterns("/admin/**");
        
        // 小程序端路由拦截器（普通用户认证）
        registry.addInterceptor(new SaInterceptor(handle -> {
            SaRouter.match("/**")
                    .notMatch(
                            // 排除不需要登录的接口
                            "/user/login",
                            "/user/accountLogin", 
                            "/user/sendCode",
                            "/user/wxLogin",
                            "/shop/**",
                            "/category/**",
                            "/goods/**",
                            "/config/**",
                            "/common/**",
                            "/coupon/list",
                            // 排除管理员端所有接口
                            "/admin/**",
                            // 排除静态资源
                            "/uploads/**",
                            // 排除Swagger文档
                            "/doc.html",
                            "/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/swagger-resources/**",
                            "/webjars/**"
                    )
                    .check(r -> StpUtil.checkLogin());
        })).addPathPatterns("/**");
    }
}

