package com.tsuki.yuntun.java.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.admin.dto.ReplyMessageDTO;
import com.tsuki.yuntun.java.admin.vo.MessageDetailVO;

import java.util.List;
import java.util.Map;

/**
 * 管理端客服Service
 */
public interface CustomerServiceAdminService {
    
    /**
     * 获取消息列表
     */
    Page<MessageDetailVO> getMessageList(Long userId, Integer status, Integer page, Integer pageSize);
    
    /**
     * 回复消息
     */
    void replyMessage(ReplyMessageDTO dto);
    
    /**
     * 获取用户列表
     */
    List<Map<String, Object>> getUserList();
    
    /**
     * 获取统计数据
     */
    Map<String, Object> getStatistics();
}
