package com.tsuki.yuntun.java.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.admin.dto.GoodsDTO;
import com.tsuki.yuntun.java.admin.service.AdminGoodsService;
import com.tsuki.yuntun.java.common.exception.BusinessException;
import com.tsuki.yuntun.java.entity.Goods;
import com.tsuki.yuntun.java.app.mapper.GoodsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 后台商品管理Service实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminGoodsServiceImpl implements AdminGoodsService {
    
    private final GoodsMapper goodsMapper;
    
    @Override
    public Page<Goods> getGoodsList(Long categoryId, String keyword, Integer page, Integer pageSize) {
        Page<Goods> pageParam = new Page<>(page, pageSize);
        
        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
        
        if (categoryId != null) {
            wrapper.eq(Goods::getCategoryId, categoryId);
        }
        
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Goods::getName, keyword);
        }
        
        wrapper.orderByDesc(Goods::getCreateTime);
        
        return goodsMapper.selectPage(pageParam, wrapper);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addGoods(GoodsDTO dto) {
        Goods goods = new Goods();
        BeanUtils.copyProperties(dto, goods);
        goods.setSales(0);
        goodsMapper.insert(goods);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGoods(Long id, GoodsDTO dto) {
        Goods goods = goodsMapper.selectById(id);
        if (goods == null) {
            throw new BusinessException("商品不存在");
        }
        
        BeanUtils.copyProperties(dto, goods);
        goods.setId(id);
        goodsMapper.updateById(goods);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteGoods(Long id) {
        Goods goods = goodsMapper.selectById(id);
        if (goods == null) {
            throw new BusinessException("商品不存在");
        }
        
        goodsMapper.deleteById(id);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGoodsStatus(Long id, Integer status) {
        Goods goods = goodsMapper.selectById(id);
        if (goods == null) {
            throw new BusinessException("商品不存在");
        }
        
        goods.setStatus(status);
        goodsMapper.updateById(goods);
    }
}

