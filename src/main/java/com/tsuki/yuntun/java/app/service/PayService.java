package com.tsuki.yuntun.java.app.service;

import java.util.Map;

/**
 * 支付Service
 */
public interface PayService {
    
    /**
     * 创建支付订单
     */
    Map<String, Object> createPay(Long userId, String orderId, Integer payType);
    
    /**
     * 查询支付状态
     */
    Map<String, Object> getPayStatus(Long userId, String orderId);
}

