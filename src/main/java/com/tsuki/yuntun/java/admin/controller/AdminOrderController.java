package com.tsuki.yuntun.java.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.admin.service.AdminOrderService;
import com.tsuki.yuntun.java.admin.vo.AdminOrderVO;
import com.tsuki.yuntun.java.admin.vo.OrderStatisticsVO;
import com.tsuki.yuntun.java.common.Result;
import com.tsuki.yuntun.java.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 后台订单管理Controller
 */
@RestController
@RequestMapping("/admin/order")
@RequiredArgsConstructor
public class AdminOrderController {
    
    private final AdminOrderService adminOrderService;
    
    /**
     * 获取订单列表（分页）
     */
    @GetMapping("/list")
    public Result<Page<AdminOrderVO>> getOrderList(
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<AdminOrderVO> result = adminOrderService.getOrderList(type, status, keyword, page, pageSize);
        return Result.success("获取成功", result);
    }
    
    /**
     * 更新订单状态
     */
    @PutMapping("/status/{id}")
    public Result<Void> updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        Integer status = params.get("status");
        adminOrderService.updateOrderStatus(id, status);
        return Result.success("操作成功", null);
    }
    
    /**
     * 获取订单统计
     */
    @GetMapping("/statistics")
    public Result<OrderStatisticsVO> getOrderStatistics(@RequestParam(required = false, defaultValue = "7") Integer days) {
        OrderStatisticsVO result = adminOrderService.getOrderStatistics(days);
        return Result.success("获取成功", result);
    }
}

