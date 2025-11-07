package com.tsuki.yuntun.java.admin.controller;

import com.tsuki.yuntun.java.admin.dto.AdminLoginDTO;
import com.tsuki.yuntun.java.admin.service.AdminAuthService;
import com.tsuki.yuntun.java.admin.vo.AdminInfoVO;
import com.tsuki.yuntun.java.admin.vo.AdminLoginVO;
import com.tsuki.yuntun.java.common.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员认证Controller
 */
@RestController
@RequestMapping("/admin/auth")
@RequiredArgsConstructor
public class AdminAuthController {
    
    private final AdminAuthService adminAuthService;
    
    /**
     * 管理员登录
     */
    @PostMapping("/login")
    public Result<AdminLoginVO> login(@Valid @RequestBody AdminLoginDTO dto) {
        AdminLoginVO vo = adminAuthService.login(dto);
        return Result.success("登录成功", vo);
    }
    
    /**
     * 获取管理员信息
     */
    @GetMapping("/info")
    public Result<AdminInfoVO> getAdminInfo(HttpServletRequest request) {
        Long adminId = Math.abs((Long) request.getAttribute("userId"));
        AdminInfoVO vo = adminAuthService.getAdminInfo(adminId);
        return Result.success("获取成功", vo);
    }
    
    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public Result<Void> logout() {
        return Result.success("退出成功", null);
    }
}

