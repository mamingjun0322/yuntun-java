package com.tsuki.yuntun.java.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.admin.service.AdminOrderService;
import com.tsuki.yuntun.java.common.exception.BusinessException;
import com.tsuki.yuntun.java.entity.Order;
import com.tsuki.yuntun.java.app.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 后台订单管理Service实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {
    
    private final OrderMapper orderMapper;
    
    @Override
    public Page<Order> getOrderList(Integer type, Integer status, String keyword, Integer page, Integer pageSize) {
        Page<Order> pageParam = new Page<>(page, pageSize);
        
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        
        if (type != null) {
            wrapper.eq(Order::getType, type);
        }
        
        if (status != null) {
            wrapper.eq(Order::getStatus, status);
        }
        
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like(Order::getOrderNo, keyword)
                    .or().like(Order::getReceiverName, keyword)
                    .or().like(Order::getReceiverPhone, keyword));
        }
        
        wrapper.orderByDesc(Order::getCreateTime);
        
        return orderMapper.selectPage(pageParam, wrapper);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatus(Long id, Integer status) {
        Order order = orderMapper.selectById(id);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        order.setStatus(status);
        
        // 如果是完成订单，记录完成时间
        if (status == 4) {
            order.setFinishTime(LocalDateTime.now());
        }
        
        orderMapper.updateById(order);
    }
    
    @Override
    public Object getOrderStatistics() {
        // 统计今日订单
        LocalDateTime todayStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        Long todayCount = orderMapper.selectCount(new LambdaQueryWrapper<Order>()
                .ge(Order::getCreateTime, todayStart));
        
        // 统计今日销售额
        // 注意：这里简化处理，实际应该使用聚合查询
        BigDecimal todayAmount = BigDecimal.ZERO;
        
        // 统计待处理订单
        Long pendingCount = orderMapper.selectCount(new LambdaQueryWrapper<Order>()
                .eq(Order::getStatus, 1));
        
        // 统计总订单数
        Long totalCount = orderMapper.selectCount(null);
        
        Map<String, Object> result = new HashMap<>();
        result.put("todayOrderCount", todayCount);
        result.put("todayAmount", todayAmount);
        result.put("pendingOrderCount", pendingCount);
        result.put("totalOrderCount", totalCount);
        
        return result;
    }
}

