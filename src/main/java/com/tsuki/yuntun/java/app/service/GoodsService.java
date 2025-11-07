package com.tsuki.yuntun.java.app.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.app.vo.GoodsDetailVO;
import com.tsuki.yuntun.java.app.vo.GoodsVO;

/**
 * 商品Service
 */
public interface GoodsService {
    
    /**
     * 获取商品列表
     */
    Page<GoodsVO> getGoodsList(Long categoryId, Integer page, Integer pageSize);
    
    /**
     * 获取商品详情
     */
    GoodsDetailVO getGoodsDetail(Long id);
    
    /**
     * 搜索商品
     */
    Page<GoodsVO> searchGoods(String keyword, Integer page, Integer pageSize);
    
    /**
     * 获取推荐商品
     */
    Page<GoodsVO> getRecommendGoods(Integer page, Integer pageSize);
}

