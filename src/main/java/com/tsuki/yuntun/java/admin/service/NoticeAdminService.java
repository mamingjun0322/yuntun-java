package com.tsuki.yuntun.java.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.admin.dto.NoticeDTO;
import com.tsuki.yuntun.java.entity.Notice;

/**
 * 通知管理Service
 */
public interface NoticeAdminService {
    
    /**
     * 分页查询通知
     */
    Page<Notice> getNoticeList(Integer page, Integer pageSize, String keyword);
    
    /**
     * 创建通知
     */
    void createNotice(NoticeDTO dto);
    
    /**
     * 更新通知
     */
    void updateNotice(NoticeDTO dto);
    
    /**
     * 删除通知
     */
    void deleteNotice(Long id);
    
    /**
     * 获取通知详情
     */
    Notice getNoticeById(Long id);
}
