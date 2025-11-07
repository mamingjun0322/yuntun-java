package com.tsuki.yuntun.java.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tsuki.yuntun.java.common.exception.BusinessException;
import com.tsuki.yuntun.java.entity.Order;
import com.tsuki.yuntun.java.app.mapper.OrderMapper;
import com.tsuki.yuntun.java.app.service.PayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付Service实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {
    
    private final OrderMapper orderMapper;
    
    @Override
    public Map<String, Object> createPay(Long userId, String orderId, Integer payType) {
        Order order = orderMapper.selectOne(new LambdaQueryWrapper<Order>()
                .eq(Order::getOrderNo, orderId)
                .eq(Order::getUserId, userId));
        
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        if (order.getStatus() != 1) {
            throw new BusinessException("订单状态异常");
        }
        
        // TODO: 这里应该调用真实的支付接口
        // 这里仅模拟返回支付参数
        Map<String, Object> result = new HashMap<>();
        result.put("payInfo", "模拟支付参数");
        
        log.info("创建支付订单：{}, 支付方式：{}", orderId, payType);
        
        return result;
    }
    
    @Override
    public Map<String, Object> getPayStatus(Long userId, String orderId) {
        Order order = orderMapper.selectOne(new LambdaQueryWrapper<Order>()
                .eq(Order::getOrderNo, orderId)
                .eq(Order::getUserId, userId));
        
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        Map<String, Object> result = new HashMap<>();
        
        // 1-待支付 2-已支付
        Integer payStatus = order.getStatus() == 1 ? 0 : 1;
        result.put("status", payStatus);
        
        if (order.getPayTime() != null) {
            result.put("payTime", order.getPayTime());
        }
        
        return result;
    }
}

