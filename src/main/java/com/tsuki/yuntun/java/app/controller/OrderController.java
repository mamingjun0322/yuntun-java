package com.tsuki.yuntun.java.app.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.common.Result;
import com.tsuki.yuntun.java.common.base.BaseController;
import com.tsuki.yuntun.java.app.dto.CommentOrderDTO;
import com.tsuki.yuntun.java.app.dto.CreateOrderDTO;
import com.tsuki.yuntun.java.app.service.OrderService;
import com.tsuki.yuntun.java.app.vo.OrderVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单Controller
 */
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController extends BaseController {
    
    private final OrderService orderService;
    
    /**
     * 创建订单
     */
    @PostMapping("/create")
    public Result<Map<String, Object>> createOrder(@Valid @RequestBody CreateOrderDTO dto) {
        Long userId = getCurrentUserId();
        Map<String, Object> result = orderService.createOrder(userId, dto);
        return Result.success(result);
    }
    
    /**
     * 获取订单列表
     */
    @GetMapping("/list")
    public Result<Page<OrderVO>> getOrderList(
            @RequestParam(defaultValue = "0") Integer status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Long userId = getCurrentUserId();
        Page<OrderVO> result = orderService.getOrderList(userId, status, page, pageSize);
        return Result.success(result);
    }
    
    /**
     * 获取订单详情
     */
    @GetMapping("/detail/{id}")
    public Result<OrderVO> getOrderDetail(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        OrderVO vo = orderService.getOrderDetail(userId, id);
        return Result.success(vo);
    }
    
    /**
     * 取消订单
     */
    @PutMapping("/cancel/{id}")
    public Result<Void> cancelOrder(@PathVariable Long id, 
                                    @RequestBody(required = false) Map<String, String> params) {
        Long userId = getCurrentUserId();
        String reason = params != null ? params.get("reason") : null;
        orderService.cancelOrder(userId, id, reason);
        return Result.success();
    }
    
    /**
     * 查询订单状态
     */
    @GetMapping("/status/{id}")
    public Result<Map<String, Integer>> getOrderStatus(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        Integer status = orderService.getOrderStatus(userId, id);
        Map<String, Integer> result = new HashMap<>();
        result.put("status", status);
        return Result.success(result);
    }
    
    /**
     * 订单评价
     */
    @PostMapping("/comment/{id}")
    public Result<Void> commentOrder(@PathVariable Long id, 
                                     @Valid @RequestBody CommentOrderDTO dto) {
        Long userId = getCurrentUserId();
        orderService.commentOrder(userId, id, dto);
        return Result.success();
    }
}

