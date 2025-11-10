package com.tsuki.yuntun.java.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.admin.dto.AdminDTO;
import com.tsuki.yuntun.java.admin.service.AdminManageService;
import com.tsuki.yuntun.java.common.Result;
import com.tsuki.yuntun.java.entity.Admin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 管理员管理Controller
 */
@Tag(name = "管理员管理", description = "管理员管理相关接口")
@RestController
@RequestMapping("/admin/manage")
@RequiredArgsConstructor
public class AdminManageController {
    
    private final AdminManageService adminManageService;
    
    /**
     * 获取管理员列表（分页）
     */
    @Operation(summary = "获取管理员列表")
    @GetMapping("/list")
    public Result<Page<Admin>> getAdminList(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Admin> result = adminManageService.getAdminList(keyword, page, pageSize);
        return Result.success("获取成功", result);
    }
    
    /**
     * 添加管理员
     */
    @Operation(summary = "添加管理员")
    @PostMapping("/add")
    public Result<Void> addAdmin(@Valid @RequestBody AdminDTO dto) {
        adminManageService.addAdmin(dto);
        return Result.success("添加成功", null);
    }
    
    /**
     * 更新管理员
     */
    @Operation(summary = "更新管理员")
    @PutMapping("/update/{id}")
    public Result<Void> updateAdmin(@PathVariable Long id, @Valid @RequestBody AdminDTO dto) {
        adminManageService.updateAdmin(id, dto);
        return Result.success("更新成功", null);
    }
    
    /**
     * 删除管理员
     */
    @Operation(summary = "删除管理员")
    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteAdmin(@PathVariable Long id) {
        adminManageService.deleteAdmin(id);
        return Result.success("删除成功", null);
    }
    
    /**
     * 更新管理员状态
     */
    @Operation(summary = "更新管理员状态")
    @PutMapping("/status/{id}")
    public Result<Void> updateAdminStatus(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        Integer status = params.get("status");
        adminManageService.updateAdminStatus(id, status);
        return Result.success("操作成功", null);
    }
    
    /**
     * 重置管理员密码
     */
    @Operation(summary = "重置管理员密码")
    @PutMapping("/resetPassword/{id}")
    public Result<Void> resetPassword(@PathVariable Long id, @RequestBody Map<String, String> params) {
        String newPassword = params.get("newPassword");
        adminManageService.resetPassword(id, newPassword);
        return Result.success("密码重置成功", null);
    }
}
