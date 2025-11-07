package com.tsuki.yuntun.java.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tsuki.yuntun.java.common.exception.BusinessException;
import com.tsuki.yuntun.java.entity.Coupon;
import com.tsuki.yuntun.java.entity.UserCoupon;
import com.tsuki.yuntun.java.app.mapper.CouponMapper;
import com.tsuki.yuntun.java.app.mapper.UserCouponMapper;
import com.tsuki.yuntun.java.app.service.CouponService;
import com.tsuki.yuntun.java.app.vo.CouponVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 优惠券Service实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    
    private final CouponMapper couponMapper;
    private final UserCouponMapper userCouponMapper;
    
    @Override
    public List<CouponVO> getCouponList() {
        List<Coupon> coupons = couponMapper.selectList(new LambdaQueryWrapper<Coupon>()
                .eq(Coupon::getStatus, 1)
                .gt(Coupon::getStock, 0)
                .gt(Coupon::getExpireTime, LocalDateTime.now())
                .orderByDesc(Coupon::getCreateTime));
        
        return coupons.stream().map(coupon -> {
            CouponVO vo = new CouponVO();
            BeanUtils.copyProperties(coupon, vo);
            return vo;
        }).toList();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void receiveCoupon(Long userId, Long couponId) {
        // 查询优惠券
        Coupon coupon = couponMapper.selectById(couponId);
        if (coupon == null) {
            throw new BusinessException("优惠券不存在");
        }
        
        if (coupon.getStatus() == 0) {
            throw new BusinessException("优惠券已下架");
        }
        
        if (coupon.getStock() <= 0) {
            throw new BusinessException("优惠券已领完");
        }
        
        if (coupon.getExpireTime().isBefore(LocalDateTime.now())) {
            throw new BusinessException("优惠券已过期");
        }
        
        // 检查是否已领取
        Long count = userCouponMapper.selectCount(new LambdaQueryWrapper<UserCoupon>()
                .eq(UserCoupon::getUserId, userId)
                .eq(UserCoupon::getCouponId, couponId));
        
        if (count > 0) {
            throw new BusinessException("已领取过该优惠券");
        }
        
        // 保存用户优惠券
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUserId(userId);
        userCoupon.setCouponId(couponId);
        userCoupon.setStatus(0);
        userCouponMapper.insert(userCoupon);
        
        // 减少库存
        coupon.setStock(coupon.getStock() - 1);
        couponMapper.updateById(coupon);
    }
    
    @Override
    public List<CouponVO> getMyCoupons(Long userId, Integer status) {
        MPJLambdaWrapper<UserCoupon> wrapper = new MPJLambdaWrapper<UserCoupon>()
                .selectAll(Coupon.class)
                .select(UserCoupon::getStatus)
                .leftJoin(Coupon.class, Coupon::getId, UserCoupon::getCouponId)
                .eq(UserCoupon::getUserId, userId);
        
        if (status != null) {
            wrapper.eq(UserCoupon::getStatus, status);
        }
        
        wrapper.orderByDesc(UserCoupon::getCreateTime);
        
        List<CouponVO> result = userCouponMapper.selectJoinList(CouponVO.class, wrapper);
        
        return result;
    }
    
    @Override
    public List<CouponVO> getAvailableCoupons(Long userId, BigDecimal totalAmount) {
        MPJLambdaWrapper<UserCoupon> wrapper = new MPJLambdaWrapper<UserCoupon>()
                .selectAll(Coupon.class)
                .select(UserCoupon::getStatus)
                .leftJoin(Coupon.class, Coupon::getId, UserCoupon::getCouponId)
                .eq(UserCoupon::getUserId, userId)
                .eq(UserCoupon::getStatus, 0)
                .le(Coupon::getMinAmount, totalAmount)
                .gt(Coupon::getExpireTime, LocalDateTime.now())
                .orderByDesc(Coupon::getDiscount);
        
        return userCouponMapper.selectJoinList(CouponVO.class, wrapper);
    }
}

