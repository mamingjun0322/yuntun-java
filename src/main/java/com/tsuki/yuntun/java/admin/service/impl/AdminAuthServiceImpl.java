package com.tsuki.yuntun.java.admin.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tsuki.yuntun.java.admin.dto.AdminLoginDTO;
import com.tsuki.yuntun.java.admin.service.AdminAuthService;
import com.tsuki.yuntun.java.admin.vo.AdminInfoVO;
import com.tsuki.yuntun.java.admin.vo.AdminLoginVO;
import com.tsuki.yuntun.java.common.exception.BusinessException;
import com.tsuki.yuntun.java.entity.Admin;
import com.tsuki.yuntun.java.app.mapper.AdminMapper;
import com.tsuki.yuntun.java.util.SaTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 管理员认证Service实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminAuthServiceImpl implements AdminAuthService {
    
    private final AdminMapper adminMapper;
    
    @Override
    public AdminLoginVO login(AdminLoginDTO dto) {
        // 查询管理员
        Admin admin = adminMapper.selectOne(new LambdaQueryWrapper<Admin>()
                .eq(Admin::getUsername, dto.getUsername()));
        
        if (admin == null) {
            throw new BusinessException("用户名或密码错误");
        }
        
        if (admin.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }
        
        // 验证密码 - 使用MD5验证
        String password = DigestUtil.md5Hex(dto.getPassword());
        if (!password.equals(admin.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        
        // 生成token（使用负数ID区分管理员和普通用户）
        String token = SaTokenUtil.adminLogin(admin.getId());
        
        // 构建响应
        AdminInfoVO adminInfo = new AdminInfoVO();
        BeanUtils.copyProperties(admin, adminInfo);
        
        return AdminLoginVO.builder()
                .token(token)
                .adminInfo(adminInfo)
                .build();
    }
    
    @Override
    public AdminInfoVO getAdminInfo(Long adminId) {
        Admin admin = adminMapper.selectById(adminId);
        if (admin == null) {
            throw new BusinessException("管理员不存在");
        }
        
        AdminInfoVO vo = new AdminInfoVO();
        BeanUtils.copyProperties(admin, vo);
        return vo;
    }
    
    @Override
    public void logout() {
        SaTokenUtil.adminLogout();
    }
}

