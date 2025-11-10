package com.tsuki.yuntun.java.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tsuki.yuntun.java.admin.dto.CategoryDTO;
import com.tsuki.yuntun.java.admin.service.AdminCategoryService;
import com.tsuki.yuntun.java.app.mapper.CategoryMapper;
import com.tsuki.yuntun.java.common.exception.BusinessException;
import com.tsuki.yuntun.java.entity.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后台分类管理Service实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {
    
    private final CategoryMapper categoryMapper;
    
    @Override
    public List<Category> getCategoryList() {
        return categoryMapper.selectList(new LambdaQueryWrapper<Category>()
                .orderByAsc(Category::getSort));
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCategory(CategoryDTO dto) {
        // 检查分类名称是否已存在
        Long count = categoryMapper.selectCount(new LambdaQueryWrapper<Category>()
                .eq(Category::getName, dto.getName()));
        if (count > 0) {
            throw new BusinessException("分类名称已存在");
        }
        
        Category category = new Category();
        BeanUtils.copyProperties(dto, category);
        category.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);
        categoryMapper.insert(category);
        
        log.info("添加分类成功：{}", dto.getName());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCategory(Long id, CategoryDTO dto) {
        Category category = categoryMapper.selectById(id);
        if (category == null) {
            throw new BusinessException("分类不存在");
        }
        
        // 检查分类名称是否已被其他分类使用
        Long count = categoryMapper.selectCount(new LambdaQueryWrapper<Category>()
                .eq(Category::getName, dto.getName())
                .ne(Category::getId, id));
        if (count > 0) {
            throw new BusinessException("分类名称已存在");
        }
        
        BeanUtils.copyProperties(dto, category);
        category.setId(id);
        categoryMapper.updateById(category);
        
        log.info("更新分类成功：{}", dto.getName());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(Long id) {
        Category category = categoryMapper.selectById(id);
        if (category == null) {
            throw new BusinessException("分类不存在");
        }
        
        categoryMapper.deleteById(id);
        
        log.info("删除分类成功：{}", category.getName());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCategoryStatus(Long id, Integer status) {
        Category category = categoryMapper.selectById(id);
        if (category == null) {
            throw new BusinessException("分类不存在");
        }
        
        category.setStatus(status);
        categoryMapper.updateById(category);
        
        log.info("更新分类状态成功：{} -> {}", category.getName(), status == 1 ? "启用" : "禁用");
    }
}
