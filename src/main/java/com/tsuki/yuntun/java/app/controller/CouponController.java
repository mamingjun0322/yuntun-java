package com.tsuki.yuntun.java.app.controller;

import com.tsuki.yuntun.java.common.Result;
import com.tsuki.yuntun.java.common.base.BaseController;
import com.tsuki.yuntun.java.app.service.CouponService;
import com.tsuki.yuntun.java.app.vo.CouponVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 优惠券Controller
 */
@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController extends BaseController {
    
    private final CouponService couponService;
    
    /**
     * 获取优惠券列表
     */
    @GetMapping("/list")
    public Result<List<CouponVO>> getCouponList() {
        List<CouponVO> list = couponService.getCouponList();
        return Result.success(list);
    }
    
    /**
     * 领取优惠券
     */
    @PostMapping("/receive/{id}")
    public Result<Void> receiveCoupon(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        couponService.receiveCoupon(userId, id);
        return Result.success();
    }
    
    /**
     * 我的优惠券
     */
    @GetMapping("/my")
    public Result<List<CouponVO>> getMyCoupons(@RequestParam(required = false) Integer status) {
        Long userId = getCurrentUserId();
        List<CouponVO> list = couponService.getMyCoupons(userId, status);
        return Result.success(list);
    }
    
    /**
     * 获取可用优惠券
     */
    @GetMapping("/available")
    public Result<List<CouponVO>> getAvailableCoupons(@RequestParam BigDecimal totalAmount) {
        Long userId = getCurrentUserId();
        List<CouponVO> list = couponService.getAvailableCoupons(userId, totalAmount);
        return Result.success(list);
    }
}

