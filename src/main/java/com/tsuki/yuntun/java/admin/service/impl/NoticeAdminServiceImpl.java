package com.tsuki.yuntun.java.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.admin.dto.NoticeDTO;
import com.tsuki.yuntun.java.admin.service.NoticeAdminService;
import com.tsuki.yuntun.java.app.mapper.NoticeMapper;
import com.tsuki.yuntun.java.entity.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 通知管理ServiceImpl
 */
@Service
@RequiredArgsConstructor
public class NoticeAdminServiceImpl implements NoticeAdminService {
    
    private final NoticeMapper noticeMapper;
    
    @Override
    public Page<Notice> getNoticeList(Integer page, Integer pageSize, String keyword) {
        Page<Notice> pageParam = new Page<>(page, pageSize);
        
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.hasText(keyword)) {
            wrapper.like(Notice::getContent, keyword);
        }
        
        wrapper.orderByDesc(Notice::getCreateTime);
        
        return noticeMapper.selectPage(pageParam, wrapper);
    }
    
    @Override
    public void createNotice(NoticeDTO dto) {
        Notice notice = new Notice();
        BeanUtils.copyProperties(dto, notice);
        
        if (notice.getStatus() == null) {
            notice.setStatus(1);
        }
        
        noticeMapper.insert(notice);
    }
    
    @Override
    public void updateNotice(NoticeDTO dto) {
        Notice notice = new Notice();
        BeanUtils.copyProperties(dto, notice);
        noticeMapper.updateById(notice);
    }
    
    @Override
    public void deleteNotice(Long id) {
        noticeMapper.deleteById(id);
    }
    
    @Override
    public Notice getNoticeById(Long id) {
        return noticeMapper.selectById(id);
    }
}
