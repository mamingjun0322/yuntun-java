package com.tsuki.yuntun.java.app.service;

import com.tsuki.yuntun.java.entity.Notice;

import java.util.List;

/**
 * 通知Service（小程序端）
 */
public interface NoticeService {
    
    /**
     * 获取启用的通知列表
     */
    List<Notice> getEnabledNotices(Integer status);
}
