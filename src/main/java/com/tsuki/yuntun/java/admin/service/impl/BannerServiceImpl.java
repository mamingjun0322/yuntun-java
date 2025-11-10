package com.tsuki.yuntun.java.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.admin.dto.BannerDTO;
import com.tsuki.yuntun.java.admin.service.BannerService;
import com.tsuki.yuntun.java.app.mapper.BannerMapper;
import com.tsuki.yuntun.java.common.exception.BusinessException;
import com.tsuki.yuntun.java.entity.Banner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 轮播图管理Service实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {
    
    private final BannerMapper bannerMapper;
    
    @Override
    public Page<Banner> getBannerList(Integer page, Integer pageSize) {
        Page<Banner> pageParam = new Page<>(page, pageSize);
        
        LambdaQueryWrapper<Banner> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Banner::getSort)
                .orderByDesc(Banner::getCreateTime);
        
        return bannerMapper.selectPage(pageParam, wrapper);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBanner(BannerDTO dto) {
        Banner banner = new Banner();
        BeanUtils.copyProperties(dto, banner);
        bannerMapper.insert(banner);
        
        log.info("添加轮播图成功：{}", dto.getImage());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBanner(Long id, BannerDTO dto) {
        Banner banner = bannerMapper.selectById(id);
        if (banner == null) {
            throw new BusinessException("轮播图不存在");
        }
        
        BeanUtils.copyProperties(dto, banner);
        banner.setId(id);
        bannerMapper.updateById(banner);
        
        log.info("更新轮播图成功：{}", id);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBanner(Long id) {
        Banner banner = bannerMapper.selectById(id);
        if (banner == null) {
            throw new BusinessException("轮播图不存在");
        }
        
        bannerMapper.deleteById(id);
        
        log.info("删除轮播图成功：{}", id);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBannerStatus(Long id, Integer status) {
        Banner banner = bannerMapper.selectById(id);
        if (banner == null) {
            throw new BusinessException("轮播图不存在");
        }
        
        banner.setStatus(status);
        bannerMapper.updateById(banner);
        
        log.info("更新轮播图状态成功：{} -> {}", id, status == 1 ? "启用" : "禁用");
    }
}
