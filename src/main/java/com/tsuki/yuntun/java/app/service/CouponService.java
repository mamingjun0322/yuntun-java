package com.tsuki.yuntun.java.app.service;

import com.tsuki.yuntun.java.app.vo.CouponVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 优惠券Service
 */
public interface CouponService {
    
    /**
     * 获取优惠券列表
     */
    List<CouponVO> getCouponList();
    
    /**
     * 领取优惠券
     */
    void receiveCoupon(Long userId, Long couponId);
    
    /**
     * 我的优惠券
     */
    List<CouponVO> getMyCoupons(Long userId, Integer status);
    
    /**
     * 获取可用优惠券
     */
    List<CouponVO> getAvailableCoupons(Long userId, BigDecimal totalAmount);
}

