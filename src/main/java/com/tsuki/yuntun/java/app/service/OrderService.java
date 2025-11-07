package com.tsuki.yuntun.java.app.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.app.dto.CommentOrderDTO;
import com.tsuki.yuntun.java.app.dto.CreateOrderDTO;
import com.tsuki.yuntun.java.app.vo.OrderVO;

import java.util.Map;

/**
 * 订单Service
 */
public interface OrderService {
    
    /**
     * 创建订单
     */
    Map<String, Object> createOrder(Long userId, CreateOrderDTO dto);
    
    /**
     * 获取订单列表
     */
    Page<OrderVO> getOrderList(Long userId, Integer status, Integer page, Integer pageSize);
    
    /**
     * 获取订单详情
     */
    OrderVO getOrderDetail(Long userId, Long id);
    
    /**
     * 取消订单
     */
    void cancelOrder(Long userId, Long id, String reason);
    
    /**
     * 查询订单状态
     */
    Integer getOrderStatus(Long userId, Long id);
    
    /**
     * 订单评价
     */
    void commentOrder(Long userId, Long id, CommentOrderDTO dto);
}

