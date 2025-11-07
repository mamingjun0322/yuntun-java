package com.tsuki.yuntun.java.app.service;

import com.tsuki.yuntun.java.app.vo.CategoryVO;

import java.util.List;

/**
 * 分类Service
 */
public interface CategoryService {
    
    /**
     * 获取分类列表
     */
    List<CategoryVO> getCategoryList();
}

