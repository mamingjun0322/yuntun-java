package com.tsuki.yuntun.java.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.entity.Order;

/**
 * 后台订单管理Service
 */
public interface AdminOrderService {
    
    /**
     * 获取订单列表（分页）
     */
    Page<Order> getOrderList(Integer type, Integer status, String keyword, Integer page, Integer pageSize);
    
    /**
     * 更新订单状态
     */
    void updateOrderStatus(Long id, Integer status);
    
    /**
     * 获取订单统计
     */
    Object getOrderStatistics();
}

