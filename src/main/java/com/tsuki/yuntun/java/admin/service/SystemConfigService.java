package com.tsuki.yuntun.java.admin.service;

import com.tsuki.yuntun.java.admin.dto.ShopConfigDTO;
import com.tsuki.yuntun.java.admin.dto.SystemConfigDTO;
import com.tsuki.yuntun.java.admin.vo.SystemConfigVO;

import java.util.List;
import java.util.Map;

/**
 * 系统配置Service
 */
public interface SystemConfigService {
    
    /**
     * 获取所有系统配置
     */
    List<SystemConfigVO> getAllConfig();
    
    /**
     * 获取配置值
     */
    String getConfigValue(String configKey);
    
    /**
     * 批量更新系统配置
     */
    void batchUpdateConfig(List<SystemConfigDTO> configs);
    
    /**
     * 获取店铺配置
     */
    Map<String, Object> getShopConfig();
    
    /**
     * 更新店铺配置
     */
    void updateShopConfig(ShopConfigDTO dto);
}

