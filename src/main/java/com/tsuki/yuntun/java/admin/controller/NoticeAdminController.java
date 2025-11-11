package com.tsuki.yuntun.java.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.admin.dto.NoticeDTO;
import com.tsuki.yuntun.java.admin.service.NoticeAdminService;
import com.tsuki.yuntun.java.common.Result;
import com.tsuki.yuntun.java.common.base.BaseController;
import com.tsuki.yuntun.java.entity.Notice;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 通知管理Controller
 */
@RestController
@RequestMapping("/admin/notice")
@RequiredArgsConstructor
public class NoticeAdminController extends BaseController {
    
    private final NoticeAdminService noticeAdminService;
    
    /**
     * 分页查询通知
     */
    @GetMapping("/list")
    public Result<Page<Notice>> getNoticeList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword) {
        Page<Notice> result = noticeAdminService.getNoticeList(page, pageSize, keyword);
        return Result.success(result);
    }
    
    /**
     * 获取通知详情
     */
    @GetMapping("/{id}")
    public Result<Notice> getNoticeById(@PathVariable Long id) {
        Notice notice = noticeAdminService.getNoticeById(id);
        return Result.success(notice);
    }
    
    /**
     * 创建通知
     */
    @PostMapping
    public Result<Void> createNotice(@Valid @RequestBody NoticeDTO dto) {
        noticeAdminService.createNotice(dto);
        return Result.success("创建成功", null);
    }
    
    /**
     * 更新通知
     */
    @PutMapping("/{id}")
    public Result<Void> updateNotice(@PathVariable Long id, @Valid @RequestBody NoticeDTO dto) {
        dto.setId(id);
        noticeAdminService.updateNotice(dto);
        return Result.success("更新成功", null);
    }
    
    /**
     * 删除通知
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteNotice(@PathVariable Long id) {
        noticeAdminService.deleteNotice(id);
        return Result.success("删除成功", null);
    }
}
