package com.tsuki.yuntun.java.app.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.app.mapper.GoodsMapper;
import com.tsuki.yuntun.java.app.mapper.GoodsSpecsMapper;
import com.tsuki.yuntun.java.app.vo.GoodsDetailVO;
import com.tsuki.yuntun.java.app.vo.GoodsVO;
import com.tsuki.yuntun.java.common.constant.RedisConstant;
import com.tsuki.yuntun.java.common.exception.BusinessException;
import com.tsuki.yuntun.java.entity.Goods;
import com.tsuki.yuntun.java.entity.GoodsSpecs;
import com.tsuki.yuntun.java.app.service.GoodsService;
import com.tsuki.yuntun.java.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 商品Service实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {
    
    private final GoodsMapper goodsMapper;
    private final GoodsSpecsMapper goodsSpecsMapper;
    private final RedisUtil redisUtil;
    
    @Override
    public Page<GoodsVO> getGoodsList(Long categoryId, Integer page, Integer pageSize) {
        Page<Goods> pageParam = new Page<>(page, pageSize);
        
        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<Goods>()
                .eq(Goods::getStatus, 1);
        
        if (categoryId != null) {
            wrapper.eq(Goods::getCategoryId, categoryId);
        }
        
        wrapper.orderByDesc(Goods::getSort).orderByDesc(Goods::getCreateTime);
        
        Page<Goods> result = goodsMapper.selectPage(pageParam, wrapper);
        
        Page<GoodsVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        List<GoodsVO> voList = result.getRecords().stream().map(this::convertToVO).toList();
        voPage.setRecords(voList);
        
        return voPage;
    }
    
    @Override
    public GoodsDetailVO getGoodsDetail(Long id) {
        // 先从缓存获取
        String key = RedisConstant.GOODS_INFO_PREFIX + id;
        GoodsDetailVO cache = redisUtil.get(key, GoodsDetailVO.class);
        if (cache != null) {
            return cache;
        }
        
        // 从数据库查询
        Goods goods = goodsMapper.selectById(id);
        if (goods == null || goods.getStatus() == 0) {
            throw new BusinessException("商品不存在或已下架");
        }
        
        GoodsDetailVO vo = new GoodsDetailVO();
        BeanUtils.copyProperties(goods, vo);
        
        // 处理图片列表
        if (goods.getImages() != null && !goods.getImages().isEmpty()) {
            vo.setImages(Arrays.asList(goods.getImages().split(",")));
        }
        
        // 查询商品规格
        if (Boolean.TRUE.equals(goods.getHasSpecs())) {
            List<GoodsSpecs> specsList = goodsSpecsMapper.selectList(
                    new LambdaQueryWrapper<GoodsSpecs>().eq(GoodsSpecs::getGoodsId, id));
            
            List<GoodsDetailVO.SpecsVO> specsVOList = specsList.stream().map(specs -> {
                GoodsDetailVO.SpecsVO specsVO = new GoodsDetailVO.SpecsVO();
                specsVO.setName(specs.getName());
                
                // 解析JSON格式的选项列表
                List<GoodsDetailVO.SpecsOptionVO> options = 
                        JSONUtil.toList(specs.getOptions(), GoodsDetailVO.SpecsOptionVO.class);
                specsVO.setOptions(options);
                
                return specsVO;
            }).toList();
            
            vo.setSpecsList(specsVOList);
        }
        
        // 存入缓存，1小时过期
        redisUtil.set(key, vo, 1, TimeUnit.HOURS);
        
        return vo;
    }
    
    @Override
    public Page<GoodsVO> searchGoods(String keyword, Integer page, Integer pageSize) {
        Page<Goods> pageParam = new Page<>(page, pageSize);
        
        Page<Goods> result = goodsMapper.selectPage(pageParam, new LambdaQueryWrapper<Goods>()
                .eq(Goods::getStatus, 1)
                .like(Goods::getName, keyword)
                .orderByDesc(Goods::getSales));
        
        Page<GoodsVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        List<GoodsVO> voList = result.getRecords().stream().map(this::convertToVO).toList();
        voPage.setRecords(voList);
        
        return voPage;
    }
    
    @Override
    public Page<GoodsVO> getRecommendGoods(Integer page, Integer pageSize) {
        Page<Goods> pageParam = new Page<>(page, pageSize);
        
        Page<Goods> result = goodsMapper.selectPage(pageParam, new LambdaQueryWrapper<Goods>()
                .eq(Goods::getStatus, 1)
                .orderByDesc(Goods::getSales)
                .last("LIMIT " + pageSize));
        
        Page<GoodsVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        List<GoodsVO> voList = result.getRecords().stream().map(this::convertToVO).toList();
        voPage.setRecords(voList);
        
        return voPage;
    }
    
    /**
     * 转换为VO
     */
    private GoodsVO convertToVO(Goods goods) {
        GoodsVO vo = new GoodsVO();
        BeanUtils.copyProperties(goods, vo);
        
        // 处理图片
        if (goods.getImages() != null && !goods.getImages().isEmpty()) {
            List<String> imageList = Arrays.asList(goods.getImages().split(","));
            vo.setImage(imageList.get(0));
            vo.setImages(imageList);
        }
        
        return vo;
    }
}

