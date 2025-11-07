package com.tsuki.yuntun.java.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.admin.dto.GoodsDTO;
import com.tsuki.yuntun.java.admin.service.AdminGoodsService;
import com.tsuki.yuntun.java.common.Result;
import com.tsuki.yuntun.java.entity.Goods;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 后台商品管理Controller
 */
@RestController
@RequestMapping("/admin/goods")
@RequiredArgsConstructor
public class AdminGoodsController {
    
    private final AdminGoodsService adminGoodsService;
    
    /**
     * 获取商品列表（分页）
     */
    @GetMapping("/list")
    public Result<Page<Goods>> getGoodsList(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Goods> result = adminGoodsService.getGoodsList(categoryId, keyword, page, pageSize);
        return Result.success("获取成功", result);
    }
    
    /**
     * 添加商品
     */
    @PostMapping("/add")
    public Result<Void> addGoods(@Valid @RequestBody GoodsDTO dto) {
        adminGoodsService.addGoods(dto);
        return Result.success("添加成功", null);
    }
    
    /**
     * 更新商品
     */
    @PutMapping("/update/{id}")
    public Result<Void> updateGoods(@PathVariable Long id, @Valid @RequestBody GoodsDTO dto) {
        adminGoodsService.updateGoods(id, dto);
        return Result.success("更新成功", null);
    }
    
    /**
     * 删除商品
     */
    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteGoods(@PathVariable Long id) {
        adminGoodsService.deleteGoods(id);
        return Result.success("删除成功", null);
    }
    
    /**
     * 上下架商品
     */
    @PutMapping("/status/{id}")
    public Result<Void> updateGoodsStatus(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        Integer status = params.get("status");
        adminGoodsService.updateGoodsStatus(id, status);
        return Result.success("操作成功", null);
    }
}

