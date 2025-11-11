package com.tsuki.yuntun.java.app.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.app.dto.SendMessageDTO;
import com.tsuki.yuntun.java.app.vo.CustomerServiceMessageVO;
import com.tsuki.yuntun.java.app.vo.FaqVO;

import java.util.List;
import java.util.Map;

/**
 * 客服Service
 */
public interface CustomerServiceService {
    
    /**
     * 发送消息
     */
    void sendMessage(Long userId, SendMessageDTO dto);
    
    /**
     * 获取消息列表
     */
    Page<CustomerServiceMessageVO> getMessageList(Long userId, Integer page, Integer pageSize);
    
    /**
     * 获取常见问题列表
     */
    List<FaqVO> getFaqList();
    
    /**
     * 标记消息为已读
     */
    void markAsRead(Long userId, Long messageId);
    
    /**
     * 获取客服配置（发送限制等）
     */
    Map<String, Object> getConfig(Long userId);
}
