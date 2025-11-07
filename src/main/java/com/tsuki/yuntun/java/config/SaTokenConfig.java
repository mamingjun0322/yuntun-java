package com.tsuki.yuntun.java.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
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
        // 注册Sa-Token拦截器，校验规则为StpUtil.checkLogin()登录校验
        registry.addInterceptor(new SaInterceptor(handle -> {
            // 小程序端路由拦截（需要登录的接口）
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
                            // 排除后台管理登录接口
                            "/admin/auth/login",
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

