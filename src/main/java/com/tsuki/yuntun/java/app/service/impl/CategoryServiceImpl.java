package com.tsuki.yuntun.java.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tsuki.yuntun.java.common.constant.RedisConstant;
import com.tsuki.yuntun.java.entity.Category;
import com.tsuki.yuntun.java.app.mapper.CategoryMapper;
import com.tsuki.yuntun.java.app.service.CategoryService;
import com.tsuki.yuntun.java.util.RedisUtil;
import com.tsuki.yuntun.java.app.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 分类Service实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    
    private final CategoryMapper categoryMapper;
    private final RedisUtil redisUtil;
    
    @Override
    public List<CategoryVO> getCategoryList() {
        // 先从缓存获取
        List<CategoryVO> cache = redisUtil.getList(RedisConstant.CATEGORY_LIST);
        if (cache != null) {
            return cache;
        }
        
        // 从数据库查询
        List<Category> categories = categoryMapper.selectList(new LambdaQueryWrapper<Category>()
                .eq(Category::getStatus, 1)
                .orderByAsc(Category::getSort));
        
        List<CategoryVO> voList = categories.stream().map(category -> {
            CategoryVO vo = new CategoryVO();
            BeanUtils.copyProperties(category, vo);
            return vo;
        }).toList();
        
        // 存入缓存，1小时过期
        redisUtil.set(RedisConstant.CATEGORY_LIST, voList, 1, TimeUnit.HOURS);
        
        return voList;
    }
}

