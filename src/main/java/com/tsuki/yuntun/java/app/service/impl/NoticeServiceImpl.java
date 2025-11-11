package com.tsuki.yuntun.java.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tsuki.yuntun.java.app.mapper.NoticeMapper;
import com.tsuki.yuntun.java.app.service.NoticeService;
import com.tsuki.yuntun.java.entity.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 通知ServiceImpl（小程序端）
 */
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    
    private final NoticeMapper noticeMapper;
    
    @Override
    public List<Notice> getEnabledNotices(Integer status) {
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Notice::getStatus, status)
                .orderByDesc(Notice::getCreateTime);
        return noticeMapper.selectList(wrapper);
    }
}
