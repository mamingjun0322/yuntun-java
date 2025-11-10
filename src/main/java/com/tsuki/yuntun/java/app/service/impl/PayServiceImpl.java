package com.tsuki.yuntun.java.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tsuki.yuntun.java.app.vo.PaymentInfoVO;
import com.tsuki.yuntun.java.common.exception.BusinessException;
import com.tsuki.yuntun.java.entity.Order;
import com.tsuki.yuntun.java.app.mapper.OrderMapper;
import com.tsuki.yuntun.java.app.service.PayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付Service实现类（支付链接模式）
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {
    
    private final OrderMapper orderMapper;
    
    @Override
    public PaymentInfoVO getPaymentInfo(Long userId, Long orderId) {
        Order order = orderMapper.selectOne(new LambdaQueryWrapper<Order>()
                .eq(Order::getId, orderId)
                .eq(Order::getUserId, userId));
        
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        if (order.getStatus() != 1) {
            throw new BusinessException("订单状态异常，当前状态不支持支付");
        }
        
        // 如果订单没有收款码，抛出异常
        if (order.getPaymentUrl() == null || order.getPaymentUrl().isEmpty()) {
            throw new BusinessException("该订单暂无可用收款码，请联系客服");
        }
        
        // 构建支付信息
        return PaymentInfoVO.builder()
                .orderId(order.getId())
                .orderNo(order.getOrderNo())
                .amount(order.getTotalAmount())
                .paymentUrl(order.getPaymentUrl()) // 收款码图片地址
                .paymentType("wechat")
                .build();
    }
    
    @Override
    public Map<String, Object> getPayStatus(Long userId, Long orderId) {
        Order order = orderMapper.selectOne(new LambdaQueryWrapper<Order>()
                .eq(Order::getId, orderId)
                .eq(Order::getUserId, userId));
        
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        Map<String, Object> result = new HashMap<>();
        
        // 订单状态：1-待支付 2-制作中 3-配送中 4-已完成 5-已取消
        // 支付状态：0-未支付 1-已支付
        Integer payStatus = order.getStatus() == 1 ? 0 : 1;
        result.put("status", payStatus);
        result.put("orderStatus", order.getStatus());
        
        if (order.getPayTime() != null) {
            result.put("payTime", order.getPayTime());
        }
        
        log.info("查询订单支付状态：orderId={}, status={}", orderId, payStatus);
        
        return result;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmPayment(Long userId, Long orderId) {
        Order order = orderMapper.selectOne(new LambdaQueryWrapper<Order>()
                .eq(Order::getId, orderId)
                .eq(Order::getUserId, userId));
        
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        if (order.getStatus() != 1) {
            throw new BusinessException("订单状态异常，无法确认支付");
        }
        
        // 更新订单状态为"制作中"，并记录支付时间
        order.setStatus(2); // 制作中
        order.setPayType(1); // 微信支付
        order.setPayTime(LocalDateTime.now());
        orderMapper.updateById(order);
        
        log.info("确认订单支付成功：orderId={}, userId={}", orderId, userId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handlePayCallback(String callbackData, String paymentType) {
        // TODO: 实现支付回调处理逻辑
        // 1. 验签：验证回调数据是否来自真实的支付平台
        // 2. 解析回调数据，获取订单号和支付状态
        // 3. 更新订单状态
        // 4. 返回成功响应给支付平台
        
        log.info("收到支付回调：paymentType={}, data={}", paymentType, callbackData);
        
        // 示例：简化处理
        // 实际项目中需要根据支付平台的文档进行解析和验签
        /*
        if ("wechat".equals(paymentType)) {
            // 解析微信支付回调数据
            // 更新订单状态
        } else if ("alipay".equals(paymentType)) {
            // 解析支付宝回调数据
            // 更新订单状态
        }
        */
    }
}

