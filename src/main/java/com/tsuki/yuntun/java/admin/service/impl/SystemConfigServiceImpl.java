package com.tsuki.yuntun.java.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.tsuki.yuntun.java.admin.dto.ShopConfigDTO;
import com.tsuki.yuntun.java.admin.dto.SystemConfigDTO;
import com.tsuki.yuntun.java.admin.service.SystemConfigService;
import com.tsuki.yuntun.java.admin.vo.SystemConfigVO;
import com.tsuki.yuntun.java.app.mapper.ShopMapper;
import com.tsuki.yuntun.java.app.mapper.SystemConfigMapper;
import com.tsuki.yuntun.java.common.exception.BusinessException;
import com.tsuki.yuntun.java.entity.Shop;
import com.tsuki.yuntun.java.entity.SystemConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统配置Service实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SystemConfigServiceImpl implements SystemConfigService {
    
    private final SystemConfigMapper systemConfigMapper;
    private final ShopMapper shopMapper;
    
    @Override
    public List<SystemConfigVO> getAllConfig() {
        List<SystemConfig> configs = systemConfigMapper.selectList(null);
        return configs.stream().map(config -> {
            SystemConfigVO vo = new SystemConfigVO();
            BeanUtils.copyProperties(config, vo);
            return vo;
        }).collect(Collectors.toList());
    }
    
    @Override
    public String getConfigValue(String configKey) {
        SystemConfig config = systemConfigMapper.selectOne(
                new LambdaQueryWrapper<SystemConfig>()
                        .eq(SystemConfig::getConfigKey, configKey));
        return config != null ? config.getConfigValue() : null;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchUpdateConfig(List<SystemConfigDTO> configs) {
        for (SystemConfigDTO dto : configs) {
            SystemConfig config = systemConfigMapper.selectOne(
                    new LambdaQueryWrapper<SystemConfig>()
                            .eq(SystemConfig::getConfigKey, dto.getConfigKey()));
            
            if (config != null) {
                // 更新现有配置
                config.setConfigValue(dto.getConfigValue());
                if (dto.getConfigDesc() != null) {
                    config.setConfigDesc(dto.getConfigDesc());
                }
                if (dto.getConfigType() != null) {
                    config.setConfigType(dto.getConfigType());
                }
                systemConfigMapper.updateById(config);
            } else {
                // 插入新配置
                SystemConfig newConfig = new SystemConfig();
                BeanUtils.copyProperties(dto, newConfig);
                systemConfigMapper.insert(newConfig);
            }
        }
        
        log.info("批量更新系统配置成功，数量：{}", configs.size());
    }
    
    @Override
    public Map<String, Object> getShopConfig() {
        Shop shop = shopMapper.selectOne(new LambdaQueryWrapper<Shop>().last("LIMIT 1"));
        if (shop == null) {
            throw new BusinessException("店铺信息不存在");
        }
        
        Map<String, Object> config = new HashMap<>();
        config.put("deliveryRange", shop.getDeliveryRange());
        config.put("minDeliveryAmount", shop.getMinDeliveryAmount());
        config.put("deliveryFee", shop.getDeliveryFee());
        config.put("packingFee", shop.getPackingFee());
        
        return config;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateShopConfig(ShopConfigDTO dto) {
        Shop shop = shopMapper.selectOne(new LambdaQueryWrapper<Shop>().last("LIMIT 1"));
        if (shop == null) {
            throw new BusinessException("店铺信息不存在");
        }
        
        shop.setDeliveryRange(dto.getDeliveryRange());
        shop.setMinDeliveryAmount(dto.getMinDeliveryAmount());
        shop.setDeliveryFee(dto.getDeliveryFee());
        shop.setPackingFee(dto.getPackingFee());
        
        shopMapper.updateById(shop);
        
        log.info("更新店铺配置成功：{}", dto);
    }
}

