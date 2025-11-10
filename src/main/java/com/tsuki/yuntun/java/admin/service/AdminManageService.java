package com.tsuki.yuntun.java.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.admin.dto.AdminDTO;
import com.tsuki.yuntun.java.entity.Admin;

/**
 * 管理员管理Service
 */
public interface AdminManageService {
    
    /**
     * 获取管理员列表（分页）
     */
    Page<Admin> getAdminList(String keyword, Integer page, Integer pageSize);
    
    /**
     * 添加管理员
     */
    void addAdmin(AdminDTO dto);
    
    /**
     * 更新管理员
     */
    void updateAdmin(Long id, AdminDTO dto);
    
    /**
     * 删除管理员
     */
    void deleteAdmin(Long id);
    
    /**
     * 更新管理员状态
     */
    void updateAdminStatus(Long id, Integer status);
    
    /**
     * 重置管理员密码
     */
    void resetPassword(Long id, String newPassword);
}
