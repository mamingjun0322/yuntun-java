package com.tsuki.yuntun.java.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.admin.dto.PaymentLinkDTO;
import com.tsuki.yuntun.java.admin.service.PaymentLinkService;
import com.tsuki.yuntun.java.admin.vo.PaymentLinkVO;
import com.tsuki.yuntun.java.app.service.CommonService;
import com.tsuki.yuntun.java.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收款码配置管理Controller（管理后台）
 */
@Tag(name = "收款码配置管理")
@RestController
@RequestMapping("/admin/paymentLink")
@RequiredArgsConstructor
public class PaymentLinkController {
    
    private final PaymentLinkService paymentLinkService;
    private final CommonService commonService;
    
    /**
     * 获取收款码配置列表（分页）
     */
    @Operation(summary = "获取收款码配置列表（分页）")
    @GetMapping("/list")
    public Result<Page<PaymentLinkVO>> getPaymentLinkList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(paymentLinkService.getPaymentLinkList(page, pageSize));
    }
    
    /**
     * 获取所有收款码配置（不分页）
     */
    @Operation(summary = "获取所有收款码配置（不分页）")
    @GetMapping("/all")
    public Result<List<PaymentLinkVO>> getAllPaymentLinks() {
        return Result.success(paymentLinkService.getAllPaymentLinks());
    }
    
    /**
     * 获取收款码配置详情
     */
    @Operation(summary = "获取收款码配置详情")
    @GetMapping("/{id}")
    public Result<PaymentLinkVO> getPaymentLinkDetail(@PathVariable Long id) {
        return Result.success(paymentLinkService.getPaymentLinkDetail(id));
    }
    
    /**
     * 上传收款码图片
     */
    @Operation(summary = "上传收款码图片")
    @PostMapping("/upload")
    public Result<Map<String, String>> uploadQrCodeImage(@RequestParam("file") MultipartFile file) {
        String url = commonService.uploadFile(file);
        Map<String, String> result = new HashMap<>();
        result.put("url", url);
        result.put("paymentUrl", url); // 兼容字段
        return Result.success("上传成功", result);
    }
    
    /**
     * 添加收款码配置
     * 支持两种方式：
     * 1. 传入图片URL（paymentUrl字段）
     * 2. 上传图片文件（使用 /upload 接口先上传，再调用此接口）
     */
    @Operation(summary = "添加收款码配置")
    @PostMapping
    public Result<Void> addPaymentLink(@Valid @RequestBody PaymentLinkDTO dto) {
        paymentLinkService.addPaymentLink(dto);
        return Result.success("添加成功", null);
    }
    
    /**
     * 添加收款码配置（支持直接上传图片）
     * 如果传入了图片文件，会自动上传并保存URL
     */
    @Operation(summary = "添加收款码配置（支持直接上传图片）")
    @PostMapping("/addWithUpload")
    public Result<Void> addPaymentLinkWithUpload(
            @RequestParam("amount") java.math.BigDecimal amount,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "paymentUrl", required = false) String paymentUrl,
            @RequestParam(value = "paymentType", required = false) String paymentType,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "sort", required = false) Integer sort) {
        
        // 如果上传了文件，先上传获取URL
        if (file != null && !file.isEmpty()) {
            paymentUrl = commonService.uploadFile(file);
        }
        
        // 验证必须要有收款码图片地址
        if (paymentUrl == null || paymentUrl.trim().isEmpty()) {
            return Result.error("收款码图片不能为空，请上传图片或提供图片URL");
        }
        
        // 构建DTO
        PaymentLinkDTO dto = new PaymentLinkDTO();
        dto.setAmount(amount);
        dto.setPaymentUrl(paymentUrl);
        dto.setPaymentType(paymentType != null ? paymentType : "wechat");
        dto.setDescription(description);
        dto.setStatus(status != null ? status : 1);
        dto.setSort(sort != null ? sort : 0);
        
        paymentLinkService.addPaymentLink(dto);
        return Result.success("添加成功", null);
    }
    
    /**
     * 更新收款码配置
     * 支持两种方式：
     * 1. 传入图片URL（paymentUrl字段）
     * 2. 上传图片文件（使用 /upload 接口先上传，再调用此接口）
     */
    @Operation(summary = "更新收款码配置")
    @PutMapping("/{id}")
    public Result<Void> updatePaymentLink(@PathVariable Long id, @Valid @RequestBody PaymentLinkDTO dto) {
        paymentLinkService.updatePaymentLink(id, dto);
        return Result.success("更新成功", null);
    }
    
    /**
     * 更新收款码配置（支持直接上传图片）
     * 如果传入了图片文件，会自动上传并更新URL
     */
    @Operation(summary = "更新收款码配置（支持直接上传图片）")
    @PutMapping("/{id}/updateWithUpload")
    public Result<Void> updatePaymentLinkWithUpload(
            @PathVariable Long id,
            @RequestParam(value = "amount", required = false) java.math.BigDecimal amount,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "paymentUrl", required = false) String paymentUrl,
            @RequestParam(value = "paymentType", required = false) String paymentType,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "sort", required = false) Integer sort) {
        
        // 如果上传了文件，先上传获取URL
        if (file != null && !file.isEmpty()) {
            paymentUrl = commonService.uploadFile(file);
        }
        
        // 构建DTO
        PaymentLinkDTO dto = new PaymentLinkDTO();
        if (amount != null) {
            dto.setAmount(amount);
        }
        if (paymentUrl != null && !paymentUrl.trim().isEmpty()) {
            dto.setPaymentUrl(paymentUrl);
        }
        if (paymentType != null) {
            dto.setPaymentType(paymentType);
        }
        if (description != null) {
            dto.setDescription(description);
        }
        if (status != null) {
            dto.setStatus(status);
        }
        if (sort != null) {
            dto.setSort(sort);
        }
        
        paymentLinkService.updatePaymentLink(id, dto);
        return Result.success("更新成功", null);
    }
    
    /**
     * 删除收款码配置
     */
    @Operation(summary = "删除收款码配置")
    @DeleteMapping("/{id}")
    public Result<Void> deletePaymentLink(@PathVariable Long id) {
        paymentLinkService.deletePaymentLink(id);
        return Result.success("删除成功", null);
    }
    
    /**
     * 启用/禁用收款码配置
     */
    @Operation(summary = "启用/禁用收款码配置")
    @PutMapping("/{id}/toggleStatus")
    public Result<Void> toggleStatus(@PathVariable Long id) {
        paymentLinkService.toggleStatus(id);
        return Result.success("操作成功", null);
    }
}

