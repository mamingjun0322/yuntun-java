package com.tsuki.yuntun.java.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.admin.dto.PaymentLinkDTO;
import com.tsuki.yuntun.java.admin.vo.PaymentLinkVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 支付链接Service
 */
public interface PaymentLinkService {
    
    /**
     * 获取支付链接列表（分页）
     */
    Page<PaymentLinkVO> getPaymentLinkList(Integer page, Integer pageSize);
    
    /**
     * 获取所有支付链接（不分页）
     */
    List<PaymentLinkVO> getAllPaymentLinks();
    
    /**
     * 根据金额获取支付链接
     */
    PaymentLinkVO getPaymentLinkByAmount(BigDecimal amount);
    
    /**
     * 获取支付链接详情
     */
    PaymentLinkVO getPaymentLinkDetail(Long id);
    
    /**
     * 添加支付链接
     */
    void addPaymentLink(PaymentLinkDTO dto);
    
    /**
     * 更新支付链接
     */
    void updatePaymentLink(Long id, PaymentLinkDTO dto);
    
    /**
     * 删除支付链接
     */
    void deletePaymentLink(Long id);
    
    /**
     * 启用/禁用支付链接
     */
    void toggleStatus(Long id);
}

