package com.tsuki.yuntun.java.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.admin.dto.PaymentLinkDTO;
import com.tsuki.yuntun.java.admin.vo.PaymentLinkVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 收款码配置Service
 */
public interface PaymentLinkService {
    
    /**
     * 获取收款码配置列表（分页）
     */
    Page<PaymentLinkVO> getPaymentLinkList(Integer page, Integer pageSize);
    
    /**
     * 获取所有收款码配置（不分页）
     */
    List<PaymentLinkVO> getAllPaymentLinks();
    
    /**
     * 根据金额获取收款码配置
     */
    PaymentLinkVO getPaymentLinkByAmount(BigDecimal amount);
    
    /**
     * 获取收款码配置详情
     */
    PaymentLinkVO getPaymentLinkDetail(Long id);
    
    /**
     * 添加收款码配置
     */
    void addPaymentLink(PaymentLinkDTO dto);
    
    /**
     * 更新收款码配置
     */
    void updatePaymentLink(Long id, PaymentLinkDTO dto);
    
    /**
     * 删除收款码配置
     */
    void deletePaymentLink(Long id);
    
    /**
     * 启用/禁用收款码配置
     */
    void toggleStatus(Long id);
}

