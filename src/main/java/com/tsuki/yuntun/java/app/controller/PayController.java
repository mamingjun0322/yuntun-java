package com.tsuki.yuntun.java.app.controller;

import com.tsuki.yuntun.java.app.vo.PaymentInfoVO;
import com.tsuki.yuntun.java.common.Result;
import com.tsuki.yuntun.java.common.base.BaseController;
import com.tsuki.yuntun.java.app.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 支付Controller（支付链接模式）
 */
@Tag(name = "支付管理")
@RestController
@RequestMapping("/pay")
@RequiredArgsConstructor
public class PayController extends BaseController {
    
    private final PayService payService;
    
    /**
     * 获取订单支付信息（支付链接）
     */
    @Operation(summary = "获取订单支付信息")
    @GetMapping("/info/{orderId}")
    public Result<PaymentInfoVO> getPaymentInfo(@PathVariable Long orderId) {
        Long userId = getCurrentUserId();
        PaymentInfoVO result = payService.getPaymentInfo(userId, orderId);
        return Result.success(result);
    }
    
    /**
     * 查询订单支付状态
     */
    @Operation(summary = "查询订单支付状态")
    @GetMapping("/status/{orderId}")
    public Result<Map<String, Object>> getPayStatus(@PathVariable Long orderId) {
        Long userId = getCurrentUserId();
        Map<String, Object> result = payService.getPayStatus(userId, orderId);
        return Result.success(result);
    }
    
    /**
     * 手动确认支付（管理员或用户确认已支付）
     */
    @Operation(summary = "确认支付完成")
    @PostMapping("/confirm/{orderId}")
    public Result<Void> confirmPayment(@PathVariable Long orderId) {
        Long userId = getCurrentUserId();
        payService.confirmPayment(userId, orderId);
        return Result.success("支付确认成功", null);
    }
    
    /**
     * 支付回调接口（由支付平台调用）
     * 注意：实际项目中需要验签和IP白名单校验
     */
    @Operation(summary = "支付回调接口")
    @PostMapping("/callback/wechat")
    public String wechatPayCallback(@RequestBody String callbackData) {
        // 这里应该实现支付回调的验签和处理逻辑
        // 暂时返回成功，实际需要根据支付平台的要求返回
        payService.handlePayCallback(callbackData, "wechat");
        return "SUCCESS";
    }
    
    /**
     * 支付宝回调接口
     */
    @Operation(summary = "支付宝回调接口")
    @PostMapping("/callback/alipay")
    public String alipayCallback(@RequestParam Map<String, String> params) {
        payService.handlePayCallback(params.toString(), "alipay");
        return "success";
    }
}

