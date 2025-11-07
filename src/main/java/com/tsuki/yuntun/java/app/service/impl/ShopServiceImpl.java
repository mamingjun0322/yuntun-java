package com.tsuki.yuntun.java.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tsuki.yuntun.java.common.constant.RedisConstant;
import com.tsuki.yuntun.java.common.exception.BusinessException;
import com.tsuki.yuntun.java.entity.Banner;
import com.tsuki.yuntun.java.entity.Notice;
import com.tsuki.yuntun.java.entity.Shop;
import com.tsuki.yuntun.java.app.mapper.BannerMapper;
import com.tsuki.yuntun.java.app.mapper.NoticeMapper;
import com.tsuki.yuntun.java.app.mapper.ShopMapper;
import com.tsuki.yuntun.java.app.service.ShopService;
import com.tsuki.yuntun.java.util.RedisUtil;
import com.tsuki.yuntun.java.app.vo.BannerVO;
import com.tsuki.yuntun.java.app.vo.DeliveryConfigVO;
import com.tsuki.yuntun.java.app.vo.NoticeVO;
import com.tsuki.yuntun.java.app.vo.ShopInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 店铺Service实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    
    private final ShopMapper shopMapper;
    private final BannerMapper bannerMapper;
    private final NoticeMapper noticeMapper;
    private final RedisUtil redisUtil;
    
    @Value("${app.shop.delivery-range}")
    private BigDecimal deliveryRange;
    
    @Value("${app.shop.min-delivery-amount}")
    private BigDecimal minDeliveryAmount;
    
    @Value("${app.shop.delivery-fee}")
    private BigDecimal deliveryFee;
    
    @Value("${app.shop.packing-fee}")
    private BigDecimal packingFee;
    
    @Override
    public ShopInfoVO getShopInfo() {
        // 先从缓存获取
        ShopInfoVO cache = redisUtil.get(RedisConstant.SHOP_INFO, ShopInfoVO.class);
        if (cache != null) {
            return cache;
        }
        
        // 从数据库查询
        Shop shop = shopMapper.selectOne(new LambdaQueryWrapper<Shop>().last("LIMIT 1"));
        if (shop == null) {
            throw new BusinessException("店铺信息不存在");
        }
        
        ShopInfoVO vo = new ShopInfoVO();
        BeanUtils.copyProperties(shop, vo);
        
        // 存入缓存，1小时过期
        redisUtil.set(RedisConstant.SHOP_INFO, vo, 1, TimeUnit.HOURS);
        
        return vo;
    }
    
    @Override
    public List<BannerVO> getBannerList() {
        // 先从缓存获取
        List<BannerVO> cache = redisUtil.getList(RedisConstant.BANNER_LIST);
        if (cache != null) {
            return cache;
        }
        
        // 从数据库查询
        List<Banner> banners = bannerMapper.selectList(new LambdaQueryWrapper<Banner>()
                .eq(Banner::getStatus, 1)
                .orderByAsc(Banner::getSort));
        
        List<BannerVO> voList = banners.stream().map(banner -> {
            BannerVO vo = new BannerVO();
            BeanUtils.copyProperties(banner, vo);
            return vo;
        }).toList();
        
        // 存入缓存，1小时过期
        redisUtil.set(RedisConstant.BANNER_LIST, voList, 1, TimeUnit.HOURS);
        
        return voList;
    }
    
    @Override
    public List<NoticeVO> getNoticeList() {
        List<Notice> notices = noticeMapper.selectList(new LambdaQueryWrapper<Notice>()
                .eq(Notice::getStatus, 1)
                .orderByDesc(Notice::getCreateTime));
        
        return notices.stream().map(notice -> {
            NoticeVO vo = new NoticeVO();
            BeanUtils.copyProperties(notice, vo);
            return vo;
        }).toList();
    }
    
    @Override
    public DeliveryConfigVO getDeliveryConfig() {
        return DeliveryConfigVO.builder()
                .minAmount(minDeliveryAmount)
                .deliveryFee(deliveryFee)
                .packingFee(packingFee)
                .range(deliveryRange)
                .build();
    }
}

