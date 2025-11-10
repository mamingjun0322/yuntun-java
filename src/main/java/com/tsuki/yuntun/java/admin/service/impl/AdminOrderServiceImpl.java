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
import java.util.*;
import java.util.stream.Collectors;

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
    public com.tsuki.yuntun.java.admin.vo.OrderStatisticsVO getOrderStatistics() {
        com.tsuki.yuntun.java.admin.vo.OrderStatisticsVO vo = new com.tsuki.yuntun.java.admin.vo.OrderStatisticsVO();
        
        // 今日开始时间
        LocalDateTime todayStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        
        // 1. 统计今日订单数
        Long todayOrderCount = orderMapper.selectCount(new LambdaQueryWrapper<Order>()
                .ge(Order::getCreateTime, todayStart));
        vo.setTodayOrderCount(todayOrderCount);
        
        // 2. 统计今日营业额
        List<Order> todayOrders = orderMapper.selectList(new LambdaQueryWrapper<Order>()
                .ge(Order::getCreateTime, todayStart)
                .in(Order::getStatus, 3, 4)); // 已完成和已评价的订单
        BigDecimal todayRevenue = todayOrders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        vo.setTodayRevenue(todayRevenue);
        
        // 3. 统计待处理订单数（待接单）
        Long pendingOrderCount = orderMapper.selectCount(new LambdaQueryWrapper<Order>()
                .eq(Order::getStatus, 1));
        vo.setPendingOrderCount(pendingOrderCount);
        
        // 4. 统计总订单数
        Long totalOrderCount = orderMapper.selectCount(null);
        vo.setTotalOrderCount(totalOrderCount);
        
        // 5. 统计总营业额
        List<Order> allCompletedOrders = orderMapper.selectList(new LambdaQueryWrapper<Order>()
                .in(Order::getStatus, 3, 4));
        BigDecimal totalRevenue = allCompletedOrders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        vo.setTotalRevenue(totalRevenue);
        
        // 6. 获取最近10条订单
        Page<Order> recentPage = orderMapper.selectPage(
                new Page<>(1, 10),
                new LambdaQueryWrapper<Order>().orderByDesc(Order::getCreateTime)
        );
        List<com.tsuki.yuntun.java.admin.vo.OrderStatisticsVO.RecentOrderVO> recentOrders = recentPage.getRecords().stream()
                .map(order -> {
                    com.tsuki.yuntun.java.admin.vo.OrderStatisticsVO.RecentOrderVO recentOrder = 
                        new com.tsuki.yuntun.java.admin.vo.OrderStatisticsVO.RecentOrderVO();
                    recentOrder.setId(order.getId());
                    recentOrder.setOrderNo(order.getOrderNo());
                    recentOrder.setType(order.getType());
                    recentOrder.setStatus(order.getStatus());
                    recentOrder.setTotalAmount(order.getTotalAmount());
                    recentOrder.setCreateTime(order.getCreateTime().toString());
                    return recentOrder;
                })
                .collect(Collectors.toList());
        vo.setRecentOrders(recentOrders);
        
        // 7. 获取近7天订单趋势
        List<com.tsuki.yuntun.java.admin.vo.OrderStatisticsVO.OrderTrendVO> orderTrend = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDateTime dayStart = LocalDateTime.now().minusDays(i).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime dayEnd = dayStart.plusDays(1);
            
            Long count = orderMapper.selectCount(new LambdaQueryWrapper<Order>()
                    .ge(Order::getCreateTime, dayStart)
                    .lt(Order::getCreateTime, dayEnd));
            
            List<Order> dayOrders = orderMapper.selectList(new LambdaQueryWrapper<Order>()
                    .ge(Order::getCreateTime, dayStart)
                    .lt(Order::getCreateTime, dayEnd)
                    .in(Order::getStatus, 3, 4));
            BigDecimal amount = dayOrders.stream()
                    .map(Order::getTotalAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            com.tsuki.yuntun.java.admin.vo.OrderStatisticsVO.OrderTrendVO trend = 
                new com.tsuki.yuntun.java.admin.vo.OrderStatisticsVO.OrderTrendVO();
            trend.setDate(dayStart.format(java.time.format.DateTimeFormatter.ofPattern("MM-dd")));
            trend.setCount(count);
            trend.setAmount(amount);
            orderTrend.add(trend);
        }
        vo.setOrderTrend(orderTrend);
        
        // 8. 统计订单类型分布
        Long dineInCount = orderMapper.selectCount(new LambdaQueryWrapper<Order>().eq(Order::getType, 1));
        Long takeOutCount = orderMapper.selectCount(new LambdaQueryWrapper<Order>().eq(Order::getType, 2));
        
        List<com.tsuki.yuntun.java.admin.vo.OrderStatisticsVO.OrderTypeDistributionVO> distribution = new ArrayList<>();
        com.tsuki.yuntun.java.admin.vo.OrderStatisticsVO.OrderTypeDistributionVO dineIn = 
            new com.tsuki.yuntun.java.admin.vo.OrderStatisticsVO.OrderTypeDistributionVO();
        dineIn.setName("堂食");
        dineIn.setValue(dineInCount);
        distribution.add(dineIn);
        
        com.tsuki.yuntun.java.admin.vo.OrderStatisticsVO.OrderTypeDistributionVO takeOut = 
            new com.tsuki.yuntun.java.admin.vo.OrderStatisticsVO.OrderTypeDistributionVO();
        takeOut.setName("外卖");
        takeOut.setValue(takeOutCount);
        distribution.add(takeOut);
        
        vo.setCategoryDistribution(distribution);
        
        return vo;
    }
}

