package com.tsuki.yuntun.java.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.admin.dto.GoodsDTO;
import com.tsuki.yuntun.java.entity.Goods;

/**
 * 后台商品管理Service
 */
public interface AdminGoodsService {
    
    /**
     * 获取商品列表（分页）
     */
    Page<Goods> getGoodsList(Long categoryId, String keyword, Integer page, Integer pageSize);
    
    /**
     * 添加商品
     */
    void addGoods(GoodsDTO dto);
    
    /**
     * 更新商品
     */
    void updateGoods(Long id, GoodsDTO dto);
    
    /**
     * 删除商品
     */
    void deleteGoods(Long id);
    
    /**
     * 上下架商品
     */
    void updateGoodsStatus(Long id, Integer status);
}

