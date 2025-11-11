package com.tsuki.yuntun.java.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.admin.dto.ReplyMessageDTO;
import com.tsuki.yuntun.java.admin.service.CustomerServiceAdminService;
import com.tsuki.yuntun.java.admin.vo.MessageDetailVO;
import com.tsuki.yuntun.java.entity.CustomerServiceMessage;
import com.tsuki.yuntun.java.entity.User;
import com.tsuki.yuntun.java.app.mapper.CustomerServiceMessageMapper;
import com.tsuki.yuntun.java.app.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 管理端客服ServiceImpl
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceAdminServiceImpl implements CustomerServiceAdminService {
    
    private final CustomerServiceMessageMapper messageMapper;
    private final UserMapper userMapper;
    
    @Override
    public Page<MessageDetailVO> getMessageList(Long userId, Integer status, Integer page, Integer pageSize) {
        Page<CustomerServiceMessage> pageParam = new Page<>(page, pageSize);
        
        LambdaQueryWrapper<CustomerServiceMessage> wrapper = new LambdaQueryWrapper<>();
        
        if (userId != null) {
            wrapper.eq(CustomerServiceMessage::getUserId, userId);
        }
        
        if (status != null) {
            wrapper.eq(CustomerServiceMessage::getStatus, status);
        }
        
        wrapper.orderByDesc(CustomerServiceMessage::getCreateTime);
        
        Page<CustomerServiceMessage> result = messageMapper.selectPage(pageParam, wrapper);
        
        Page<MessageDetailVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        List<MessageDetailVO> voList = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        voPage.setRecords(voList);
        
        return voPage;
    }
    
    @Override
    public void replyMessage(ReplyMessageDTO dto) {
        CustomerServiceMessage message = new CustomerServiceMessage();
        message.setUserId(dto.getUserId());
        message.setContent(dto.getContent());
        message.setType(2); // 2-客服回复
        message.setStatus(1); // 1-未读
        message.setReplyId(dto.getReplyId());
        
        messageMapper.insert(message);
    }
    
    @Override
    public List<Map<String, Object>> getUserList() {
        // 获取所有发送过消息的用户
        List<CustomerServiceMessage> messages = messageMapper.selectList(
                new LambdaQueryWrapper<CustomerServiceMessage>()
                        .eq(CustomerServiceMessage::getType, 1)
                        .orderByDesc(CustomerServiceMessage::getCreateTime)
        );
        
        // 去重并获取用户信息
        return messages.stream()
                .map(CustomerServiceMessage::getUserId)
                .distinct()
                .map(userId -> {
                    User user = userMapper.selectById(userId);
                    Map<String, Object> map = new HashMap<>();
                    map.put("userId", userId);
                    map.put("nickname", user != null ? user.getNickname() : "未知用户");
                    map.put("avatar", user != null ? user.getAvatar() : "");
                    
                    // 获取最新一条消息
                    CustomerServiceMessage lastMessage = messageMapper.selectOne(
                            new LambdaQueryWrapper<CustomerServiceMessage>()
                                    .eq(CustomerServiceMessage::getUserId, userId)
                                    .orderByDesc(CustomerServiceMessage::getCreateTime)
                                    .last("LIMIT 1")
                    );
                    
                    if (lastMessage != null) {
                        map.put("lastMessage", lastMessage.getContent());
                        map.put("lastTime", lastMessage.getCreateTime());
                    }
                    
                    // 获取未读消息数
                    Long unreadCount = messageMapper.selectCount(
                            new LambdaQueryWrapper<CustomerServiceMessage>()
                                    .eq(CustomerServiceMessage::getUserId, userId)
                                    .eq(CustomerServiceMessage::getType, 1)
                                    .eq(CustomerServiceMessage::getStatus, 1)
                    );
                    map.put("unreadCount", unreadCount);
                    
                    return map;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public Map<String, Object> getStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        // 总消息数
        Long totalMessages = messageMapper.selectCount(null);
        statistics.put("totalMessages", totalMessages);
        
        // 未读消息数
        Long unreadMessages = messageMapper.selectCount(
                new LambdaQueryWrapper<CustomerServiceMessage>()
                        .eq(CustomerServiceMessage::getType, 1)
                        .eq(CustomerServiceMessage::getStatus, 1)
        );
        statistics.put("unreadMessages", unreadMessages);
        
        // 咨询用户数 - 获取所有type=1的消息，然后按userId去重统计
        List<Long> userIds = messageMapper.selectList(
                new LambdaQueryWrapper<CustomerServiceMessage>()
                        .select(CustomerServiceMessage::getUserId)
                        .eq(CustomerServiceMessage::getType, 1)
        ).stream()
         .map(CustomerServiceMessage::getUserId)
         .distinct()
         .collect(Collectors.toList());
        statistics.put("totalUsers", userIds.size());
        
        return statistics;
    }
    
    /**
     * 转换为VO
     */
    private MessageDetailVO convertToVO(CustomerServiceMessage message) {
        MessageDetailVO vo = new MessageDetailVO();
        BeanUtils.copyProperties(message, vo);
        
        // 获取用户信息
        User user = userMapper.selectById(message.getUserId());
        if (user != null) {
            vo.setUserName(user.getNickname());
        }
        
        return vo;
    }
}
