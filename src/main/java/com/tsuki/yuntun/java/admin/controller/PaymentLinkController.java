package com.tsuki.yuntun.java.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.admin.dto.PaymentLinkDTO;
import com.tsuki.yuntun.java.admin.service.PaymentLinkService;
import com.tsuki.yuntun.java.admin.vo.PaymentLinkVO;
import com.tsuki.yuntun.java.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 支付链接管理Controller（管理后台）
 */
@Tag(name = "支付链接管理")
@RestController
@RequestMapping("/admin/paymentLink")
@RequiredArgsConstructor
public class PaymentLinkController {
    
    private final PaymentLinkService paymentLinkService;
    
    /**
     * 获取支付链接列表（分页）
     */
    @Operation(summary = "获取支付链接列表（分页）")
    @GetMapping("/list")
    public Result<Page<PaymentLinkVO>> getPaymentLinkList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(paymentLinkService.getPaymentLinkList(page, pageSize));
    }
    
    /**
     * 获取所有支付链接（不分页）
     */
    @Operation(summary = "获取所有支付链接（不分页）")
    @GetMapping("/all")
    public Result<List<PaymentLinkVO>> getAllPaymentLinks() {
        return Result.success(paymentLinkService.getAllPaymentLinks());
    }
    
    /**
     * 获取支付链接详情
     */
    @Operation(summary = "获取支付链接详情")
    @GetMapping("/{id}")
    public Result<PaymentLinkVO> getPaymentLinkDetail(@PathVariable Long id) {
        return Result.success(paymentLinkService.getPaymentLinkDetail(id));
    }
    
    /**
     * 添加支付链接
     */
    @Operation(summary = "添加支付链接")
    @PostMapping
    public Result<Void> addPaymentLink(@Valid @RequestBody PaymentLinkDTO dto) {
        paymentLinkService.addPaymentLink(dto);
        return Result.success("添加成功", null);
    }
    
    /**
     * 更新支付链接
     */
    @Operation(summary = "更新支付链接")
    @PutMapping("/{id}")
    public Result<Void> updatePaymentLink(@PathVariable Long id, @Valid @RequestBody PaymentLinkDTO dto) {
        paymentLinkService.updatePaymentLink(id, dto);
        return Result.success("更新成功", null);
    }
    
    /**
     * 删除支付链接
     */
    @Operation(summary = "删除支付链接")
    @DeleteMapping("/{id}")
    public Result<Void> deletePaymentLink(@PathVariable Long id) {
        paymentLinkService.deletePaymentLink(id);
        return Result.success("删除成功", null);
    }
    
    /**
     * 启用/禁用支付链接
     */
    @Operation(summary = "启用/禁用支付链接")
    @PutMapping("/{id}/toggleStatus")
    public Result<Void> toggleStatus(@PathVariable Long id) {
        paymentLinkService.toggleStatus(id);
        return Result.success("操作成功", null);
    }
}

