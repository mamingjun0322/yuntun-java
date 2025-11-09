package com.tsuki.yuntun.java.admin.controller;

import com.tsuki.yuntun.java.admin.dto.ShopConfigDTO;
import com.tsuki.yuntun.java.admin.dto.SystemConfigDTO;
import com.tsuki.yuntun.java.admin.service.SystemConfigService;
import com.tsuki.yuntun.java.admin.vo.SystemConfigVO;
import com.tsuki.yuntun.java.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统配置Controller
 */
@Tag(name = "系统配置管理", description = "系统配置相关接口")
@RestController
@RequestMapping("/admin/config")
@RequiredArgsConstructor
public class SystemConfigController {
    
    private final SystemConfigService systemConfigService;
    
    /**
     * 获取所有系统配置
     */
    @Operation(summary = "获取所有系统配置")
    @GetMapping("/list")
    public Result<List<SystemConfigVO>> getAllConfig() {
        List<SystemConfigVO> list = systemConfigService.getAllConfig();
        return Result.success(list);
    }
    
    /**
     * 批量更新系统配置
     */
    @Operation(summary = "批量更新系统配置")
    @PostMapping("/update")
    public Result<Void> batchUpdateConfig(@Valid @RequestBody List<SystemConfigDTO> configs) {
        systemConfigService.batchUpdateConfig(configs);
        return Result.success("配置更新成功", null);
    }
    
    /**
     * 获取店铺配置
     */
    @Operation(summary = "获取店铺配置")
    @GetMapping("/shop")
    public Result<Map<String, Object>> getShopConfig() {
        Map<String, Object> config = systemConfigService.getShopConfig();
        return Result.success(config);
    }
    
    /**
     * 更新店铺配置
     */
    @Operation(summary = "更新店铺配置")
    @PostMapping("/shop/update")
    public Result<Void> updateShopConfig(@Valid @RequestBody ShopConfigDTO dto) {
        systemConfigService.updateShopConfig(dto);
        return Result.success("店铺配置更新成功", null);
    }
}

