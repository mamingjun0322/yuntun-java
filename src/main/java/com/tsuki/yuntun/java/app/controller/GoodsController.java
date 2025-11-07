package com.tsuki.yuntun.java.app.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.common.Result;
import com.tsuki.yuntun.java.app.service.GoodsService;
import com.tsuki.yuntun.java.app.vo.GoodsDetailVO;
import com.tsuki.yuntun.java.app.vo.GoodsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 商品Controller
 */
@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsController {
    
    private final GoodsService goodsService;
    
    /**
     * 获取商品列表
     */
    @GetMapping("/list")
    public Result<Page<GoodsVO>> getGoodsList(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize) {
        Page<GoodsVO> result = goodsService.getGoodsList(categoryId, page, pageSize);
        return Result.success("获取成功", result);
    }
    
    /**
     * 获取商品详情
     */
    @GetMapping("/detail/{id}")
    public Result<GoodsDetailVO> getGoodsDetail(@PathVariable Long id) {
        GoodsDetailVO vo = goodsService.getGoodsDetail(id);
        return Result.success("获取成功", vo);
    }
    
    /**
     * 搜索商品
     */
    @GetMapping("/search")
    public Result<Page<GoodsVO>> searchGoods(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize) {
        Page<GoodsVO> result = goodsService.searchGoods(keyword, page, pageSize);
        return Result.success("获取成功", result);
    }
    
    /**
     * 获取推荐商品
     */
    @GetMapping("/recommend")
    public Result<Page<GoodsVO>> getRecommendGoods(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize) {
        Page<GoodsVO> result = goodsService.getRecommendGoods(page, pageSize);
        return Result.success("获取成功", result);
    }
}

