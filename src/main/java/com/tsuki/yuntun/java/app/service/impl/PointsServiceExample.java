package com.tsuki.yuntun.java.app.service.impl;

import com.tsuki.yuntun.java.common.config.SystemConfigUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 积分服务示例
 * 展示如何使用数据库配置
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PointsServiceExample {
    
    private final SystemConfigUtil systemConfigUtil;
    
    /**
     * 用户签到赠送积分
     */
    public void signIn(Long userId) {
        // 从数据库配置读取签到积分
        Integer signInPoints = systemConfigUtil.getSignInPoints();
        
        log.info("用户 {} 签到，赠送 {} 积分", userId, signInPoints);
        
        // TODO: 实际积分赠送逻辑
        // userPointsMapper.addPoints(userId, signInPoints);
    }
    
    /**
     * 订单完成赠送积分
     */
    public void rewardOrderPoints(Long userId, BigDecimal orderAmount) {
        // 从数据库配置读取积分倍率
        Integer pointsRate = systemConfigUtil.getOrderPointsRate();
        
        // 计算赠送积分：订单金额 * 倍率
        int points = orderAmount.multiply(new BigDecimal(pointsRate)).intValue();
        
        log.info("用户 {} 订单金额 {}，按倍率 {} 计算，赠送 {} 积分", 
                userId, orderAmount, pointsRate, points);
        
        // TODO: 实际积分赠送逻辑
        // userPointsMapper.addPoints(userId, points);
    }
    
    /**
     * 获取当前积分规则
     */
    public String getPointsRules() {
        Integer signInPoints = systemConfigUtil.getSignInPoints();
        Integer orderPointsRate = systemConfigUtil.getOrderPointsRate();
        
        return String.format(
            "积分规则：\n" +
            "1. 每日签到赠送 %d 积分\n" +
            "2. 订单完成每消费 1 元赠送 %d 积分",
            signInPoints, orderPointsRate
        );
    }
}
