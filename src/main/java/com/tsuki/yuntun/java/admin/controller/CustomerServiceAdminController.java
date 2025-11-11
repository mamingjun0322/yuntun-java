package com.tsuki.yuntun.java.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.common.Result;
import com.tsuki.yuntun.java.common.base.BaseController;
import com.tsuki.yuntun.java.admin.dto.ReplyMessageDTO;
import com.tsuki.yuntun.java.admin.service.CustomerServiceAdminService;
import com.tsuki.yuntun.java.admin.vo.MessageDetailVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 管理端客服Controller
 */
@RestController
@RequestMapping("/admin/customerService")
@RequiredArgsConstructor
public class CustomerServiceAdminController extends BaseController {
    
    private final CustomerServiceAdminService customerServiceAdminService;
    
    /**
     * 获取消息列表（所有用户）
     */
    @GetMapping("/messageList")
    public Result<Page<MessageDetailVO>> getMessageList(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize) {
        Page<MessageDetailVO> result = customerServiceAdminService.getMessageList(userId, status, page, pageSize);
        return Result.success(result);
    }
    
    /**
     * 回复消息
     */
    @PostMapping("/reply")
    public Result<Void> replyMessage(@Valid @RequestBody ReplyMessageDTO dto) {
        customerServiceAdminService.replyMessage(dto);
        return Result.success("回复成功", null);
    }
    
    /**
     * 获取用户列表（有消息的用户）
     */
    @GetMapping("/userList")
    public Result<List<Map<String, Object>>> getUserList() {
        List<Map<String, Object>> list = customerServiceAdminService.getUserList();
        return Result.success(list);
    }
    
    /**
     * 获取统计数据
     */
    @GetMapping("/statistics")
    public Result<Map<String, Object>> getStatistics() {
        Map<String, Object> statistics = customerServiceAdminService.getStatistics();
        return Result.success(statistics);
    }
}
