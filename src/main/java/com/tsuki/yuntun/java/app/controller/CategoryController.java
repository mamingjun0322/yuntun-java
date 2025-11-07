package com.tsuki.yuntun.java.app.controller;

import com.tsuki.yuntun.java.common.Result;
import com.tsuki.yuntun.java.app.service.CategoryService;
import com.tsuki.yuntun.java.app.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分类Controller
 */
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryService categoryService;
    
    /**
     * 获取分类列表
     */
    @GetMapping("/list")
    public Result<List<CategoryVO>> getCategoryList() {
        List<CategoryVO> list = categoryService.getCategoryList();
        return Result.success("获取成功", list);
    }
}

