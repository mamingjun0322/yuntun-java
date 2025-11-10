package com.tsuki.yuntun.java.util;

import cn.dev33.satoken.stp.StpUtil;

/**
 * Sa-Token工具类
 * 使用 Sa-Token 多账号认证体系：
 * - StpUtil: 普通用户认证（默认）
 * - StpAdminUtil: 管理员认证（独立）
 */
public class SaTokenUtil {
    
    /**
     * 普通用户登录
     * @param userId 用户ID
     * @return token
     */
    public static String login(Long userId) {
        StpUtil.login(userId);
        return StpUtil.getTokenValue();
    }
    
    /**
     * 管理员登录（使用独立的认证体系）
     * @param adminId 管理员ID
     * @return token
     */
    public static String adminLogin(Long adminId) {
        // 使用管理员专用的 StpLogic 进行登录
        StpAdminUtil.stpLogic.login(adminId);
        return StpAdminUtil.stpLogic.getTokenValue();
    }
    
    /**
     * 获取当前登录用户ID（普通用户）
     * @return 用户ID，未登录返回null
     */
    public static Long getLoginUserId() {
        if (!StpUtil.isLogin()) {
            return null;
        }
        return StpUtil.getLoginIdAsLong();
    }
    
    /**
     * 获取当前登录管理员ID
     * @return 管理员ID，未登录返回null
     */
    public static Long getLoginAdminId() {
        if (!StpAdminUtil.stpLogic.isLogin()) {
            return null;
        }
        return StpAdminUtil.stpLogic.getLoginIdAsLong();
    }
    
    /**
     * 判断是否是管理员登录
     * @return true-已登录的管理员 false-未登录或普通用户
     */
    public static boolean isAdmin() {
        return StpAdminUtil.stpLogic.isLogin();
    }
    
    /**
     * 判断是否是普通用户登录
     * @return true-已登录的普通用户 false-未登录或管理员
     */
    public static boolean isUser() {
        return StpUtil.isLogin();
    }
    
    /**
     * 普通用户退出登录
     */
    public static void logout() {
        StpUtil.logout();
    }
    
    /**
     * 管理员退出登录
     */
    public static void adminLogout() {
        StpAdminUtil.stpLogic.logout();
    }
    
    /**
     * 检查普通用户是否登录
     * @return true-已登录 false-未登录
     */
    public static boolean isLogin() {
        return StpUtil.isLogin();
    }
    
    /**
     * 检查管理员是否登录
     * @return true-已登录 false-未登录
     */
    public static boolean isAdminLogin() {
        return StpAdminUtil.stpLogic.isLogin();
    }
    
    /**
     * 设置普通用户Session数据
     * @param key 键
     * @param value 值
     */
    public static void setSession(String key, Object value) {
        StpUtil.getSession().set(key, value);
    }
    
    /**
     * 获取普通用户Session数据
     * @param key 键
     * @return 值
     */
    public static Object getSession(String key) {
        return StpUtil.getSession().get(key);
    }
    
    /**
     * 设置管理员Session数据
     * @param key 键
     * @param value 值
     */
    public static void setAdminSession(String key, Object value) {
        StpAdminUtil.stpLogic.getSession().set(key, value);
    }
    
    /**
     * 获取管理员Session数据
     * @param key 键
     * @return 值
     */
    public static Object getAdminSession(String key) {
        return StpAdminUtil.stpLogic.getSession().get(key);
    }
    
    /**
     * 踢普通用户下线
     * @param loginId 登录ID
     */
    public static void kickout(Long loginId) {
        StpUtil.kickout(loginId);
    }
    
    /**
     * 踢管理员下线
     * @param loginId 登录ID
     */
    public static void kickoutAdmin(Long loginId) {
        StpAdminUtil.stpLogic.kickout(loginId);
    }
}

