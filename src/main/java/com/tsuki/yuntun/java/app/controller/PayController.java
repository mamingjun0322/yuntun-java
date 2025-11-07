package com.tsuki.yuntun.java.app.controller;

import com.tsuki.yuntun.java.app.dto.CreatePayDTO;
import com.tsuki.yuntun.java.common.Result;
import com.tsuki.yuntun.java.common.base.BaseController;
import com.tsuki.yuntun.java.app.service.PayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 支付Controller
 */
@RestController
@RequestMapping("/pay")
@RequiredArgsConstructor
public class PayController extends BaseController {
    
    private final PayService payService;
    
    /**
     * 创建支付订单
     */
    @PostMapping("/create")
    public Result<Map<String, Object>> createPay(@Valid @RequestBody CreatePayDTO dto) {
        Long userId = getCurrentUserId();
        Map<String, Object> result = payService.createPay(userId, dto.getOrderId(), dto.getPayType());
        return Result.success(result);
    }
    
    /**
     * 查询支付状态
     */
    @GetMapping("/status/{orderId}")
    public Result<Map<String, Object>> getPayStatus(@PathVariable String orderId) {
        Long userId = getCurrentUserId();
        Map<String, Object> result = payService.getPayStatus(userId, orderId);
        return Result.success(result);
    }
    
    /**
     * 微信支付
     */
    @PostMapping("/wechat")
    public Result<Map<String, Object>> wechatPay(@RequestBody CreatePayDTO dto) {
        Long userId = getCurrentUserId();
        Map<String, Object> result = payService.createPay(userId, dto.getOrderId(), 1);
        return Result.success(result);
    }
    
    /**
     * 支付宝支付
     */
    @PostMapping("/alipay")
    public Result<Map<String, Object>> alipay(@RequestBody Map<String, String> params) {
        Long userId = getCurrentUserId();
        String orderId = params.get("orderId");
        Map<String, Object> result = payService.createPay(userId, orderId, 2);
        return Result.success(result);
    }
}

