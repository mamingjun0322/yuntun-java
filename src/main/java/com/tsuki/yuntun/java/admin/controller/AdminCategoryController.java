package com.tsuki.yuntun.java.admin.controller;

import com.tsuki.yuntun.java.admin.dto.CategoryDTO;
import com.tsuki.yuntun.java.admin.service.AdminCategoryService;
import com.tsuki.yuntun.java.common.Result;
import com.tsuki.yuntun.java.entity.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 后台分类管理Controller
 */
@Tag(name = "分类管理", description = "后台分类管理相关接口")
@RestController
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class AdminCategoryController {
    
    private final AdminCategoryService adminCategoryService;
    
    /**
     * 获取分类列表
     */
    @Operation(summary = "获取分类列表")
    @GetMapping("/list")
    public Result<List<Category>> getCategoryList() {
        List<Category> list = adminCategoryService.getCategoryList();
        return Result.success("获取成功", list);
    }
    
    /**
     * 添加分类
     */
    @Operation(summary = "添加分类")
    @PostMapping("/add")
    public Result<Void> addCategory(@Valid @RequestBody CategoryDTO dto) {
        adminCategoryService.addCategory(dto);
        return Result.success("添加成功", null);
    }
    
    /**
     * 更新分类
     */
    @Operation(summary = "更新分类")
    @PutMapping("/update/{id}")
    public Result<Void> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO dto) {
        adminCategoryService.updateCategory(id, dto);
        return Result.success("更新成功", null);
    }
    
    /**
     * 删除分类
     */
    @Operation(summary = "删除分类")
    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteCategory(@PathVariable Long id) {
        adminCategoryService.deleteCategory(id);
        return Result.success("删除成功", null);
    }
    
    /**
     * 更新分类状态
     */
    @Operation(summary = "更新分类状态")
    @PutMapping("/status/{id}")
    public Result<Void> updateCategoryStatus(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        Integer status = params.get("status");
        adminCategoryService.updateCategoryStatus(id, status);
        return Result.success("操作成功", null);
    }
}
