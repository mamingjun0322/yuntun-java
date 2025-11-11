package com.tsuki.yuntun.java.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.admin.dto.FaqDTO;
import com.tsuki.yuntun.java.admin.service.FaqAdminService;
import com.tsuki.yuntun.java.common.Result;
import com.tsuki.yuntun.java.common.base.BaseController;
import com.tsuki.yuntun.java.entity.Faq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * FAQ管理Controller
 */
@RestController
@RequestMapping("/admin/faq")
@RequiredArgsConstructor
public class FaqAdminController extends BaseController {
    
    private final FaqAdminService faqAdminService;
    
    /**
     * 分页查询FAQ
     */
    @GetMapping("/list")
    public Result<Page<Faq>> getFaqList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword) {
        Page<Faq> result = faqAdminService.getFaqList(page, pageSize, keyword);
        return Result.success(result);
    }
    
    /**
     * 获取FAQ详情
     */
    @GetMapping("/{id}")
    public Result<Faq> getFaqById(@PathVariable Long id) {
        Faq faq = faqAdminService.getFaqById(id);
        return Result.success(faq);
    }
    
    /**
     * 创建FAQ
     */
    @PostMapping
    public Result<Void> createFaq(@Valid @RequestBody FaqDTO dto) {
        faqAdminService.createFaq(dto);
        return Result.success("创建成功", null);
    }
    
    /**
     * 更新FAQ
     */
    @PutMapping("/{id}")
    public Result<Void> updateFaq(@PathVariable Long id, @Valid @RequestBody FaqDTO dto) {
        dto.setId(id);
        faqAdminService.updateFaq(dto);
        return Result.success("更新成功", null);
    }
    
    /**
     * 删除FAQ
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteFaq(@PathVariable Long id) {
        faqAdminService.deleteFaq(id);
        return Result.success("删除成功", null);
    }
}
