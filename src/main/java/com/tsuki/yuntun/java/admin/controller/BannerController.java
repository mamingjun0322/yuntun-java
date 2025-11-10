package com.tsuki.yuntun.java.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.admin.dto.BannerDTO;
import com.tsuki.yuntun.java.admin.service.BannerService;
import com.tsuki.yuntun.java.common.Result;
import com.tsuki.yuntun.java.entity.Banner;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 轮播图管理Controller
 */
@Tag(name = "轮播图管理", description = "轮播图管理相关接口")
@RestController
@RequestMapping("/admin/banner")
@RequiredArgsConstructor
public class BannerController {
    
    private final BannerService bannerService;
    
    /**
     * 获取轮播图列表（分页）
     */
    @Operation(summary = "获取轮播图列表")
    @GetMapping("/list")
    public Result<Page<Banner>> getBannerList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Banner> result = bannerService.getBannerList(page, pageSize);
        return Result.success("获取成功", result);
    }
    
    /**
     * 添加轮播图
     */
    @Operation(summary = "添加轮播图")
    @PostMapping("/add")
    public Result<Void> addBanner(@Valid @RequestBody BannerDTO dto) {
        bannerService.addBanner(dto);
        return Result.success("添加成功", null);
    }
    
    /**
     * 更新轮播图
     */
    @Operation(summary = "更新轮播图")
    @PutMapping("/update/{id}")
    public Result<Void> updateBanner(@PathVariable Long id, @Valid @RequestBody BannerDTO dto) {
        bannerService.updateBanner(id, dto);
        return Result.success("更新成功", null);
    }
    
    /**
     * 删除轮播图
     */
    @Operation(summary = "删除轮播图")
    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteBanner(@PathVariable Long id) {
        bannerService.deleteBanner(id);
        return Result.success("删除成功", null);
    }
    
    /**
     * 更新轮播图状态
     */
    @Operation(summary = "更新轮播图状态")
    @PutMapping("/status/{id}")
    public Result<Void> updateBannerStatus(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        Integer status = params.get("status");
        bannerService.updateBannerStatus(id, status);
        return Result.success("操作成功", null);
    }
}
