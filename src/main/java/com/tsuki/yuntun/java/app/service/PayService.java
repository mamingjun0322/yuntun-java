package com.tsuki.yuntun.java.app.service;

import com.tsuki.yuntun.java.app.vo.PaymentInfoVO;

import java.util.Map;

/**
 * 支付Service（支付链接模式）
 */
public interface PayService {
    
    /**
     * 获取订单支付信息（支付链接）
     */
    PaymentInfoVO getPaymentInfo(Long userId, Long orderId);
    
    /**
     * 查询订单支付状态
     */
    Map<String, Object> getPayStatus(Long userId, Long orderId);
    
    /**
     * 确认支付完成
     */
    void confirmPayment(Long userId, Long orderId);
    
    /**
     * 处理支付回调
     */
    void handlePayCallback(String callbackData, String paymentType);
}

