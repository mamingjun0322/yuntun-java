package com.tsuki.yuntun.java.admin.service;

import com.tsuki.yuntun.java.admin.dto.AdminLoginDTO;
import com.tsuki.yuntun.java.admin.vo.AdminInfoVO;
import com.tsuki.yuntun.java.admin.vo.AdminLoginVO;

/**
 * 管理员认证Service
 */
public interface AdminAuthService {
    
    /**
     * 管理员登录
     */
    AdminLoginVO login(AdminLoginDTO dto);
    
    /**
     * 获取管理员信息
     */
    AdminInfoVO getAdminInfo(Long adminId);
    
    /**
     * 退出登录
     */
    void logout();
}

