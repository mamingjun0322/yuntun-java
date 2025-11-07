package com.tsuki.yuntun.java.common.base;

import com.tsuki.yuntun.java.util.SaTokenUtil;

/**
 * Controller基类
 */
public abstract class BaseController {
    
    /**
     * 获取当前登录用户ID
     * @return 用户ID
     */
    protected Long getCurrentUserId() {
        return SaTokenUtil.getLoginUserId();
    }
    
    /**
     * 获取当前登录管理员ID
     * @return 管理员ID
     */
    protected Long getCurrentAdminId() {
        return SaTokenUtil.getLoginAdminId();
    }
    
    /**
     * 判断是否是管理员
     * @return true-管理员 false-普通用户
     */
    protected boolean isAdmin() {
        return SaTokenUtil.isAdmin();
    }
}
