package com.tsuki.yuntun.java.app.service;

import com.tsuki.yuntun.java.app.dto.AddressDTO;
import com.tsuki.yuntun.java.app.dto.CheckRangeDTO;
import com.tsuki.yuntun.java.app.vo.AddressVO;
import com.tsuki.yuntun.java.app.vo.CheckRangeVO;

import java.util.List;

/**
 * 地址Service
 */
public interface AddressService {
    
    /**
     * 获取地址列表
     */
    List<AddressVO> getAddressList(Long userId);
    
    /**
     * 获取地址详情
     */
    AddressVO getAddressDetail(Long userId, Long id);
    
    /**
     * 新增地址
     */
    void addAddress(Long userId, AddressDTO dto);
    
    /**
     * 编辑地址
     */
    void editAddress(Long userId, Long id, AddressDTO dto);
    
    /**
     * 删除地址
     */
    void deleteAddress(Long userId, Long id);
    
    /**
     * 设为默认地址
     */
    void setDefaultAddress(Long userId, Long id);
    
    /**
     * 检查配送范围
     */
    CheckRangeVO checkRange(CheckRangeDTO dto);
}

