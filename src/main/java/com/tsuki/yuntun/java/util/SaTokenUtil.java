package com.tsuki.yuntun.java.util;

import cn.dev33.satoken.stp.StpUtil;

/**
 * Sa-Token工具类
 */
public class SaTokenUtil {
    
    /**
     * 用户登录
     * @param userId 用户ID
     * @return token
     */
    public static String login(Long userId) {
        StpUtil.login(userId);
        return StpUtil.getTokenValue();
    }
    
    /**
     * 管理员登录（使用负数ID区分）
     * @param adminId 管理员ID
     * @return token
     */
    public static String adminLogin(Long adminId) {
        // 使用负数ID来区分管理员
        StpUtil.login(-adminId);
        return StpUtil.getTokenValue();
    }
    
    /**
     * 获取当前登录用户ID
     * @return 用户ID
     */
    public static Long getLoginUserId() {
        long loginId = StpUtil.getLoginIdAsLong();
        // 如果是负数，说明是管理员，返回null
        return loginId > 0 ? loginId : null;
    }
    
    /**
     * 获取当前登录管理员ID
     * @return 管理员ID
     */
    public static Long getLoginAdminId() {
        long loginId = StpUtil.getLoginIdAsLong();
        // 如果是负数，取绝对值返回管理员ID
        return loginId < 0 ? -loginId : null;
    }
    
    /**
     * 判断是否是管理员
     * @return true-管理员 false-普通用户
     */
    public static boolean isAdmin() {
        return StpUtil.getLoginIdAsLong() < 0;
    }
    
    /**
     * 退出登录
     */
    public static void logout() {
        StpUtil.logout();
    }
    
    /**
     * 检查是否登录
     * @return true-已登录 false-未登录
     */
    public static boolean isLogin() {
        return StpUtil.isLogin();
    }
    
    /**
     * 设置Session数据
     * @param key 键
     * @param value 值
     */
    public static void setSession(String key, Object value) {
        StpUtil.getSession().set(key, value);
    }
    
    /**
     * 获取Session数据
     * @param key 键
     * @return 值
     */
    public static Object getSession(String key) {
        return StpUtil.getSession().get(key);
    }
    
    /**
     * 踢人下线
     * @param loginId 登录ID
     */
    public static void kickout(Long loginId) {
        StpUtil.kickout(loginId);
    }
}

