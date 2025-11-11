package com.tsuki.yuntun.java.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.app.dto.SendMessageDTO;
import com.tsuki.yuntun.java.app.service.CustomerServiceService;
import com.tsuki.yuntun.java.app.vo.CustomerServiceMessageVO;
import com.tsuki.yuntun.java.app.vo.FaqVO;
import com.tsuki.yuntun.java.common.exception.BusinessException;
import com.tsuki.yuntun.java.entity.CustomerServiceMessage;
import com.tsuki.yuntun.java.entity.Faq;
import com.tsuki.yuntun.java.app.mapper.CustomerServiceMessageMapper;
import com.tsuki.yuntun.java.app.mapper.FaqMapper;
import com.tsuki.yuntun.java.app.mapper.SystemConfigMapper;
import com.tsuki.yuntun.java.entity.SystemConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 客服ServiceImpl
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceServiceImpl implements CustomerServiceService {
    
    private final CustomerServiceMessageMapper messageMapper;
    private final FaqMapper faqMapper;
    private final SystemConfigMapper systemConfigMapper;
    
    @Override
    public void sendMessage(Long userId, SendMessageDTO dto) {
        // 1. 检查发送间隔
        checkSendInterval(userId);
        
        // 2. 检查每日发送次数
        checkDailyLimit(userId);
        
        // 3. 发送消息
        CustomerServiceMessage message = new CustomerServiceMessage();
        message.setUserId(userId);
        message.setContent(dto.getContent());
        message.setType(1); // 1-用户发送
        message.setStatus(1); // 1-未读
        
        messageMapper.insert(message);
    }
    
    @Override
    public Page<CustomerServiceMessageVO> getMessageList(Long userId, Integer page, Integer pageSize) {
        Page<CustomerServiceMessage> pageParam = new Page<>(page, pageSize);
        
        LambdaQueryWrapper<CustomerServiceMessage> wrapper = new LambdaQueryWrapper<CustomerServiceMessage>()
                .eq(CustomerServiceMessage::getUserId, userId)
                .orderByDesc(CustomerServiceMessage::getCreateTime);
        
        Page<CustomerServiceMessage> result = messageMapper.selectPage(pageParam, wrapper);
        
        Page<CustomerServiceMessageVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        List<CustomerServiceMessageVO> voList = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        voPage.setRecords(voList);
        
        return voPage;
    }
    
    @Override
    public List<FaqVO> getFaqList() {
        List<Faq> list = faqMapper.selectList(
                new LambdaQueryWrapper<Faq>()
                        .eq(Faq::getStatus, 1)
                        .orderByAsc(Faq::getSort)
        );
        
        return list.stream().map(faq -> {
            FaqVO vo = new FaqVO();
            BeanUtils.copyProperties(faq, vo);
            return vo;
        }).collect(Collectors.toList());
    }
    
    @Override
    public void markAsRead(Long userId, Long messageId) {
        CustomerServiceMessage message = messageMapper.selectOne(
                new LambdaQueryWrapper<CustomerServiceMessage>()
                        .eq(CustomerServiceMessage::getId, messageId)
                        .eq(CustomerServiceMessage::getUserId, userId)
        );
        
        if (message == null) {
            throw new BusinessException("消息不存在");
        }
        
        message.setStatus(2); // 2-已读
        messageMapper.updateById(message);
    }
    
    @Override
    public Map<String, Object> getConfig(Long userId) {
        Map<String, Object> config = new HashMap<>();
        
        // 获取每日发送次数限制
        String limitStr = getConfigValue("customer_service_message_limit");
        int limit = limitStr != null ? Integer.parseInt(limitStr) : 10;
        
        // 获取发送间隔（秒）
        String intervalStr = getConfigValue("customer_service_message_interval");
        int interval = intervalStr != null ? Integer.parseInt(intervalStr) : 10;
        
        // 获取今日已发送次数
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        Long todaySent = messageMapper.selectCount(
                new LambdaQueryWrapper<CustomerServiceMessage>()
                        .eq(CustomerServiceMessage::getUserId, userId)
                        .eq(CustomerServiceMessage::getType, 1)
                        .ge(CustomerServiceMessage::getCreateTime, startOfDay)
        );
        
        // 获取最后一条消息的时间
        CustomerServiceMessage lastMessage = messageMapper.selectOne(
                new LambdaQueryWrapper<CustomerServiceMessage>()
                        .eq(CustomerServiceMessage::getUserId, userId)
                        .eq(CustomerServiceMessage::getType, 1)
                        .orderByDesc(CustomerServiceMessage::getCreateTime)
                        .last("LIMIT 1")
        );
        
        config.put("dailyLimit", limit);
        config.put("todaySent", todaySent.intValue());
        config.put("remaining", Math.max(0, limit - todaySent.intValue()));
        config.put("sendInterval", interval);
        config.put("lastSendTime", lastMessage != null ? lastMessage.getCreateTime() : null);
        
        return config;
    }
    
    /**
     * 检查发送间隔
     */
    private void checkSendInterval(Long userId) {
        String intervalStr = getConfigValue("customer_service_message_interval");
        int interval = intervalStr != null ? Integer.parseInt(intervalStr) : 10;
        
        // 获取最后一条消息
        CustomerServiceMessage lastMessage = messageMapper.selectOne(
                new LambdaQueryWrapper<CustomerServiceMessage>()
                        .eq(CustomerServiceMessage::getUserId, userId)
                        .eq(CustomerServiceMessage::getType, 1)
                        .orderByDesc(CustomerServiceMessage::getCreateTime)
                        .last("LIMIT 1")
        );
        
        if (lastMessage != null) {
            LocalDateTime lastTime = lastMessage.getCreateTime();
            LocalDateTime now = LocalDateTime.now();
            long seconds = java.time.Duration.between(lastTime, now).getSeconds();
            
            if (seconds < interval) {
                throw new BusinessException("发送过于频繁，请" + (interval - seconds) + "秒后再试");
            }
        }
    }
    
    /**
     * 检查每日发送次数限制
     */
    private void checkDailyLimit(Long userId) {
        String limitStr = getConfigValue("customer_service_message_limit");
        int limit = limitStr != null ? Integer.parseInt(limitStr) : 10;
        
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        Long todaySent = messageMapper.selectCount(
                new LambdaQueryWrapper<CustomerServiceMessage>()
                        .eq(CustomerServiceMessage::getUserId, userId)
                        .eq(CustomerServiceMessage::getType, 1)
                        .ge(CustomerServiceMessage::getCreateTime, startOfDay)
        );
        
        if (todaySent >= limit) {
            throw new BusinessException("今日发送次数已达上限（" + limit + "条），请明天再试");
        }
    }
    
    /**
     * 获取系统配置值
     */
    private String getConfigValue(String configKey) {
        SystemConfig config = systemConfigMapper.selectOne(
                new LambdaQueryWrapper<SystemConfig>()
                        .eq(SystemConfig::getConfigKey, configKey)
        );
        return config != null ? config.getConfigValue() : null;
    }
    
    /**
     * 转换为VO
     */
    private CustomerServiceMessageVO convertToVO(CustomerServiceMessage message) {
        CustomerServiceMessageVO vo = new CustomerServiceMessageVO();
        BeanUtils.copyProperties(message, vo);
        return vo;
    }
}
