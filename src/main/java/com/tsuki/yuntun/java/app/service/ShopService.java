package com.tsuki.yuntun.java.app.service;

import com.tsuki.yuntun.java.app.vo.BannerVO;
import com.tsuki.yuntun.java.app.vo.DeliveryConfigVO;
import com.tsuki.yuntun.java.app.vo.NoticeVO;
import com.tsuki.yuntun.java.app.vo.ShopInfoVO;

import java.util.List;

/**
 * 店铺Service
 */
public interface ShopService {
    
    /**
     * 获取店铺信息
     */
    ShopInfoVO getShopInfo();
    
    /**
     * 获取轮播图
     */
    List<BannerVO> getBannerList();
    
    /**
     * 获取公告列表
     */
    List<NoticeVO> getNoticeList();
    
    /**
     * 获取配送配置
     */
    DeliveryConfigVO getDeliveryConfig();
}

