package com.tsuki.yuntun.java.app.controller;

import com.tsuki.yuntun.java.common.Result;
import com.tsuki.yuntun.java.app.service.ShopService;
import com.tsuki.yuntun.java.app.vo.BannerVO;
import com.tsuki.yuntun.java.app.vo.DeliveryConfigVO;
import com.tsuki.yuntun.java.app.vo.NoticeVO;
import com.tsuki.yuntun.java.app.vo.ShopInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 店铺Controller
 */
@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {
    
    private final ShopService shopService;
    
    /**
     * 获取店铺信息
     */
    @GetMapping("/info")
    public Result<ShopInfoVO> getShopInfo() {
        ShopInfoVO vo = shopService.getShopInfo();
        return Result.success("获取成功", vo);
    }
    
    /**
     * 获取轮播图
     */
    @GetMapping("/banner")
    public Result<List<BannerVO>> getBannerList() {
        List<BannerVO> list = shopService.getBannerList();
        return Result.success("获取成功", list);
    }
    
    /**
     * 获取公告列表
     */
    @GetMapping("/notice")
    public Result<List<NoticeVO>> getNoticeList() {
        List<NoticeVO> list = shopService.getNoticeList();
        return Result.success("获取成功", list);
    }
}

