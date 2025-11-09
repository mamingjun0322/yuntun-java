package com.tsuki.yuntun.java.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.admin.dto.PaymentLinkDTO;
import com.tsuki.yuntun.java.admin.service.PaymentLinkService;
import com.tsuki.yuntun.java.admin.vo.PaymentLinkVO;
import com.tsuki.yuntun.java.app.mapper.PaymentLinkMapper;
import com.tsuki.yuntun.java.common.exception.BusinessException;
import com.tsuki.yuntun.java.entity.PaymentLink;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 支付链接Service实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentLinkServiceImpl implements PaymentLinkService {
    
    private final PaymentLinkMapper paymentLinkMapper;
    
    @Override
    public Page<PaymentLinkVO> getPaymentLinkList(Integer page, Integer pageSize) {
        Page<PaymentLink> pageParam = new Page<>(page, pageSize);
        
        Page<PaymentLink> result = paymentLinkMapper.selectPage(pageParam,
                new LambdaQueryWrapper<PaymentLink>()
                        .orderByAsc(PaymentLink::getSort)
                        .orderByAsc(PaymentLink::getAmount));
        
        Page<PaymentLinkVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        List<PaymentLinkVO> voList = result.getRecords().stream().map(this::convertToVO).collect(Collectors.toList());
        voPage.setRecords(voList);
        
        return voPage;
    }
    
    @Override
    public List<PaymentLinkVO> getAllPaymentLinks() {
        List<PaymentLink> links = paymentLinkMapper.selectList(
                new LambdaQueryWrapper<PaymentLink>()
                        .eq(PaymentLink::getStatus, 1)
                        .orderByAsc(PaymentLink::getSort)
                        .orderByAsc(PaymentLink::getAmount));
        
        return links.stream().map(this::convertToVO).collect(Collectors.toList());
    }
    
    @Override
    public PaymentLinkVO getPaymentLinkByAmount(BigDecimal amount) {
        // 查找完全匹配的金额
        PaymentLink link = paymentLinkMapper.selectOne(
                new LambdaQueryWrapper<PaymentLink>()
                        .eq(PaymentLink::getAmount, amount)
                        .eq(PaymentLink::getStatus, 1)
                        .last("LIMIT 1"));
        
        if (link == null) {
            // 如果没有完全匹配，查找最接近的金额（向上取整）
            link = paymentLinkMapper.selectOne(
                    new LambdaQueryWrapper<PaymentLink>()
                            .ge(PaymentLink::getAmount, amount)
                            .eq(PaymentLink::getStatus, 1)
                            .orderByAsc(PaymentLink::getAmount)
                            .last("LIMIT 1"));
        }
        
        if (link == null) {
            throw new BusinessException("未找到匹配的支付链接，请联系管理员添加对应金额的支付链接");
        }
        
        return convertToVO(link);
    }
    
    @Override
    public PaymentLinkVO getPaymentLinkDetail(Long id) {
        PaymentLink link = paymentLinkMapper.selectById(id);
        if (link == null) {
            throw new BusinessException("支付链接不存在");
        }
        return convertToVO(link);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addPaymentLink(PaymentLinkDTO dto) {
        // 检查该金额是否已存在
        Long count = paymentLinkMapper.selectCount(
                new LambdaQueryWrapper<PaymentLink>()
                        .eq(PaymentLink::getAmount, dto.getAmount())
                        .eq(PaymentLink::getPaymentType, dto.getPaymentType()));
        
        if (count > 0) {
            throw new BusinessException("该金额的支付链接已存在");
        }
        
        PaymentLink link = new PaymentLink();
        BeanUtils.copyProperties(dto, link);
        
        // 设置默认值
        if (link.getPaymentType() == null) {
            link.setPaymentType("wechat");
        }
        if (link.getStatus() == null) {
            link.setStatus(1);
        }
        if (link.getSort() == null) {
            link.setSort(0);
        }
        
        paymentLinkMapper.insert(link);
        log.info("添加支付链接成功：金额={}, URL={}", link.getAmount(), link.getPaymentUrl());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePaymentLink(Long id, PaymentLinkDTO dto) {
        PaymentLink link = paymentLinkMapper.selectById(id);
        if (link == null) {
            throw new BusinessException("支付链接不存在");
        }
        
        // 如果修改了金额，检查新金额是否已存在
        if (dto.getAmount() != null && !dto.getAmount().equals(link.getAmount())) {
            Long count = paymentLinkMapper.selectCount(
                    new LambdaQueryWrapper<PaymentLink>()
                            .eq(PaymentLink::getAmount, dto.getAmount())
                            .eq(PaymentLink::getPaymentType, dto.getPaymentType())
                            .ne(PaymentLink::getId, id));
            
            if (count > 0) {
                throw new BusinessException("该金额的支付链接已存在");
            }
        }
        
        BeanUtils.copyProperties(dto, link);
        link.setId(id);
        paymentLinkMapper.updateById(link);
        
        log.info("更新支付链接成功：ID={}, 金额={}", id, link.getAmount());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePaymentLink(Long id) {
        PaymentLink link = paymentLinkMapper.selectById(id);
        if (link == null) {
            throw new BusinessException("支付链接不存在");
        }
        
        paymentLinkMapper.deleteById(id);
        log.info("删除支付链接成功：ID={}, 金额={}", id, link.getAmount());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void toggleStatus(Long id) {
        PaymentLink link = paymentLinkMapper.selectById(id);
        if (link == null) {
            throw new BusinessException("支付链接不存在");
        }
        
        link.setStatus(link.getStatus() == 1 ? 0 : 1);
        paymentLinkMapper.updateById(link);
        
        log.info("切换支付链接状态成功：ID={}, 新状态={}", id, link.getStatus());
    }
    
    /**
     * 转换为VO
     */
    private PaymentLinkVO convertToVO(PaymentLink link) {
        PaymentLinkVO vo = new PaymentLinkVO();
        BeanUtils.copyProperties(link, vo);
        return vo;
    }
}

