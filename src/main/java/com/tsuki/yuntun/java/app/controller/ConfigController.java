package com.tsuki.yuntun.java.app.controller;

import com.tsuki.yuntun.java.common.Result;
import com.tsuki.yuntun.java.app.service.ShopService;
import com.tsuki.yuntun.java.app.vo.DeliveryConfigVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置Controller
 */
@RestController
@RequestMapping("/config")
@RequiredArgsConstructor
public class ConfigController {
    
    private final ShopService shopService;
    
    /**
     * 获取配送配置
     */
    @GetMapping("/delivery")
    public Result<DeliveryConfigVO> getDeliveryConfig() {
        DeliveryConfigVO vo = shopService.getDeliveryConfig();
        return Result.success("获取成功", vo);
    }
}

