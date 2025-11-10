package com.tsuki.yuntun.java.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.admin.dto.BannerDTO;
import com.tsuki.yuntun.java.entity.Banner;

/**
 * 轮播图管理Service
 */
public interface BannerService {
    
    /**
     * 获取轮播图列表（分页）
     */
    Page<Banner> getBannerList(Integer page, Integer pageSize);
    
    /**
     * 添加轮播图
     */
    void addBanner(BannerDTO dto);
    
    /**
     * 更新轮播图
     */
    void updateBanner(Long id, BannerDTO dto);
    
    /**
     * 删除轮播图
     */
    void deleteBanner(Long id);
    
    /**
     * 更新轮播图状态
     */
    void updateBannerStatus(Long id, Integer status);
}
