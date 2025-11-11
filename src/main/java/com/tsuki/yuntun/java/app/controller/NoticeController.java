package com.tsuki.yuntun.java.app.controller;

import com.tsuki.yuntun.java.common.Result;
import com.tsuki.yuntun.java.common.base.BaseController;
import com.tsuki.yuntun.java.app.service.NoticeService;
import com.tsuki.yuntun.java.entity.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通知Controller（小程序端）
 */
@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController extends BaseController {
    
    private final NoticeService noticeService;
    
    /**
     * 获取启用的通知列表
     */
    @GetMapping("/list")
    public Result<List<Notice>> getNoticeList(@RequestParam(defaultValue = "1") Integer status) {
        List<Notice> list = noticeService.getEnabledNotices(status);
        return Result.success(list);
    }
}
