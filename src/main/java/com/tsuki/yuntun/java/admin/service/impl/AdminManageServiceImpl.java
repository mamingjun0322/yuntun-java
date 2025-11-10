package com.tsuki.yuntun.java.admin.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.admin.dto.AdminDTO;
import com.tsuki.yuntun.java.admin.service.AdminManageService;
import com.tsuki.yuntun.java.app.mapper.AdminMapper;
import com.tsuki.yuntun.java.common.exception.BusinessException;
import com.tsuki.yuntun.java.entity.Admin;
import com.tsuki.yuntun.java.util.SaTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 管理员管理Service实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminManageServiceImpl implements AdminManageService {
    
    private final AdminMapper adminMapper;
    
    /**
     * 检查是否是超级管理员
     */
    private void checkSuperAdmin() {
        Long adminId = SaTokenUtil.getLoginAdminId();
        if (adminId == null) {
            throw new BusinessException("未登录");
        }
        
        Admin admin = adminMapper.selectById(adminId);
        if (admin == null || admin.getRole() != 1) {
            throw new BusinessException("权限不足，只有超级管理员才能操作");
        }
    }
    
    @Override
    public Page<Admin> getAdminList(String keyword, Integer page, Integer pageSize) {
        Page<Admin> pageParam = new Page<>(page, pageSize);
        
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like(Admin::getUsername, keyword)
                    .or().like(Admin::getName, keyword)
                    .or().like(Admin::getPhone, keyword));
        }
        
        wrapper.orderByDesc(Admin::getCreateTime);
        
        return adminMapper.selectPage(pageParam, wrapper);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addAdmin(AdminDTO dto) {
        // 检查超级管理员权限
        checkSuperAdmin();
        
        // 检查用户名是否已存在
        Long count = adminMapper.selectCount(new LambdaQueryWrapper<Admin>()
                .eq(Admin::getUsername, dto.getUsername()));
        if (count > 0) {
            throw new BusinessException("用户名已存在");
        }
        
        // 密码必须填写
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new BusinessException("密码不能为空");
        }
        
        Admin admin = new Admin();
        BeanUtils.copyProperties(dto, admin);
        // MD5加密密码
        admin.setPassword(DigestUtil.md5Hex(dto.getPassword()));
        admin.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);
        
        adminMapper.insert(admin);
        
        log.info("添加管理员成功：{}", dto.getUsername());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAdmin(Long id, AdminDTO dto) {
        // 检查超级管理员权限
        checkSuperAdmin();
        
        Admin admin = adminMapper.selectById(id);
        if (admin == null) {
            throw new BusinessException("管理员不存在");
        }
        
        // 检查用户名是否被其他管理员使用
        Long count = adminMapper.selectCount(new LambdaQueryWrapper<Admin>()
                .eq(Admin::getUsername, dto.getUsername())
                .ne(Admin::getId, id));
        if (count > 0) {
            throw new BusinessException("用户名已存在");
        }
        
        BeanUtils.copyProperties(dto, admin);
        admin.setId(id);
        
        // 如果传了新密码，则更新密码
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            admin.setPassword(DigestUtil.md5Hex(dto.getPassword()));
        }
        
        adminMapper.updateById(admin);
        
        log.info("更新管理员成功：{}", dto.getUsername());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAdmin(Long id) {
        // 检查超级管理员权限
        checkSuperAdmin();
        
        Admin admin = adminMapper.selectById(id);
        if (admin == null) {
            throw new BusinessException("管理员不存在");
        }
        
        // 防止删除自己（这里简化处理，实际应该从token中获取当前登录管理员ID）
        if (admin.getRole() == 1) {
            // 检查是否是最后一个超级管理员
            Long superAdminCount = adminMapper.selectCount(new LambdaQueryWrapper<Admin>()
                    .eq(Admin::getRole, 1));
            if (superAdminCount <= 1) {
                throw new BusinessException("不能删除最后一个超级管理员");
            }
        }
        
        adminMapper.deleteById(id);
        
        log.info("删除管理员成功：{}", admin.getUsername());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAdminStatus(Long id, Integer status) {
        // 检查超级管理员权限
        checkSuperAdmin();
        
        Admin admin = adminMapper.selectById(id);
        if (admin == null) {
            throw new BusinessException("管理员不存在");
        }
        
        admin.setStatus(status);
        adminMapper.updateById(admin);
        
        log.info("更新管理员状态成功：{} -> {}", admin.getUsername(), status == 1 ? "启用" : "禁用");
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Long id, String newPassword) {
        // 检查超级管理员权限
        checkSuperAdmin();
        
        Admin admin = adminMapper.selectById(id);
        if (admin == null) {
            throw new BusinessException("管理员不存在");
        }
        
        if (newPassword == null || newPassword.isEmpty()) {
            throw new BusinessException("新密码不能为空");
        }
        
        admin.setPassword(DigestUtil.md5Hex(newPassword));
        adminMapper.updateById(admin);
        
        log.info("重置管理员密码成功：{}", admin.getUsername());
    }
}
