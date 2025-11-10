package com.tsuki.yuntun.java.admin.service;

import com.tsuki.yuntun.java.admin.dto.CategoryDTO;
import com.tsuki.yuntun.java.entity.Category;

import java.util.List;

/**
 * 后台分类管理Service
 */
public interface AdminCategoryService {
    
    /**
     * 获取分类列表
     */
    List<Category> getCategoryList();
    
    /**
     * 添加分类
     */
    void addCategory(CategoryDTO dto);
    
    /**
     * 更新分类
     */
    void updateCategory(Long id, CategoryDTO dto);
    
    /**
     * 删除分类
     */
    void deleteCategory(Long id);
    
    /**
     * 更新分类状态
     */
    void updateCategoryStatus(Long id, Integer status);
}
