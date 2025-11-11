package com.tsuki.yuntun.java.app.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.common.Result;
import com.tsuki.yuntun.java.common.base.BaseController;
import com.tsuki.yuntun.java.app.dto.SendMessageDTO;
import com.tsuki.yuntun.java.app.service.CustomerServiceService;
import com.tsuki.yuntun.java.app.vo.CustomerServiceMessageVO;
import com.tsuki.yuntun.java.app.vo.FaqVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 客服Controller
 */
@RestController
@RequestMapping("/customerService")
@RequiredArgsConstructor
public class CustomerServiceController extends BaseController {
    
    private final CustomerServiceService customerServiceService;
    
    /**
     * 发送消息
     */
    @PostMapping("/sendMessage")
    public Result<Void> sendMessage(@Valid @RequestBody SendMessageDTO dto) {
        Long userId = getCurrentUserId();
        customerServiceService.sendMessage(userId, dto);
        return Result.success("发送成功", null);
    }
    
    /**
     * 获取消息列表
     */
    @GetMapping("/messageList")
    public Result<Page<CustomerServiceMessageVO>> getMessageList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize) {
        Long userId = getCurrentUserId();
        Page<CustomerServiceMessageVO> result = customerServiceService.getMessageList(userId, page, pageSize);
        return Result.success(result);
    }
    
    /**
     * 获取常见问题列表
     */
    @GetMapping("/faqList")
    public Result<List<FaqVO>> getFaqList() {
        List<FaqVO> list = customerServiceService.getFaqList();
        return Result.success(list);
    }
    
    /**
     * 标记消息为已读
     */
    @PutMapping("/markAsRead/{messageId}")
    public Result<Void> markAsRead(@PathVariable Long messageId) {
        Long userId = getCurrentUserId();
        customerServiceService.markAsRead(userId, messageId);
        return Result.success("操作成功", null);
    }
    
    /**
     * 获取客服配置（发送限制等）
     */
    @GetMapping("/config")
    public Result<Map<String, Object>> getConfig() {
        Long userId = getCurrentUserId();
        Map<String, Object> config = customerServiceService.getConfig(userId);
        return Result.success(config);
    }
}
