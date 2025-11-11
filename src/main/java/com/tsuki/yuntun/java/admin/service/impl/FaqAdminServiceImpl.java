package com.tsuki.yuntun.java.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.admin.dto.FaqDTO;
import com.tsuki.yuntun.java.admin.service.FaqAdminService;
import com.tsuki.yuntun.java.app.mapper.FaqMapper;
import com.tsuki.yuntun.java.entity.Faq;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * FAQ管理ServiceImpl
 */
@Service
@RequiredArgsConstructor
public class FaqAdminServiceImpl implements FaqAdminService {
    
    private final FaqMapper faqMapper;
    
    @Override
    public Page<Faq> getFaqList(Integer page, Integer pageSize, String keyword) {
        Page<Faq> pageParam = new Page<>(page, pageSize);
        
        LambdaQueryWrapper<Faq> wrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.hasText(keyword)) {
            wrapper.and(w -> w.like(Faq::getQuestion, keyword)
                    .or()
                    .like(Faq::getAnswer, keyword));
        }
        
        wrapper.orderByAsc(Faq::getSort)
                .orderByDesc(Faq::getCreateTime);
        
        return faqMapper.selectPage(pageParam, wrapper);
    }
    
    @Override
    public void createFaq(FaqDTO dto) {
        Faq faq = new Faq();
        BeanUtils.copyProperties(dto, faq);
        
        if (faq.getSort() == null) {
            faq.setSort(0);
        }
        
        if (faq.getStatus() == null) {
            faq.setStatus(1);
        }
        
        faqMapper.insert(faq);
    }
    
    @Override
    public void updateFaq(FaqDTO dto) {
        Faq faq = new Faq();
        BeanUtils.copyProperties(dto, faq);
        faqMapper.updateById(faq);
    }
    
    @Override
    public void deleteFaq(Long id) {
        faqMapper.deleteById(id);
    }
    
    @Override
    public Faq getFaqById(Long id) {
        return faqMapper.selectById(id);
    }
}
