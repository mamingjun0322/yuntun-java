package com.tsuki.yuntun.java.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.admin.dto.FaqDTO;
import com.tsuki.yuntun.java.entity.Faq;

/**
 * FAQ管理Service
 */
public interface FaqAdminService {
    
    /**
     * 分页查询FAQ
     */
    Page<Faq> getFaqList(Integer page, Integer pageSize, String keyword);
    
    /**
     * 创建FAQ
     */
    void createFaq(FaqDTO dto);
    
    /**
     * 更新FAQ
     */
    void updateFaq(FaqDTO dto);
    
    /**
     * 删除FAQ
     */
    void deleteFaq(Long id);
    
    /**
     * 获取FAQ详情
     */
    Faq getFaqById(Long id);
}
