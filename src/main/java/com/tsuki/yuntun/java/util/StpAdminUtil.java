package com.tsuki.yuntun.java.util;

import cn.dev33.satoken.stp.StpLogic;

/**
 * 管理员端认证工具类
 * 独立的管理员认证体系，与普通用户完全隔离
 */
public class StpAdminUtil {
    
    /**
     * 账号类型标识（用于区分管理员和普通用户）
     */
    public static final String TYPE = "admin";
    
    /**
     * 管理员专用的 StpLogic 实例
     */
    public static final StpLogic stpLogic = new StpLogic(TYPE);
    
}
