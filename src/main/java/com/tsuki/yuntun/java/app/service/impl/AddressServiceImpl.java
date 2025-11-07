package com.tsuki.yuntun.java.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.tsuki.yuntun.java.common.exception.BusinessException;
import com.tsuki.yuntun.java.app.dto.AddressDTO;
import com.tsuki.yuntun.java.app.dto.CheckRangeDTO;
import com.tsuki.yuntun.java.entity.Address;
import com.tsuki.yuntun.java.entity.Shop;
import com.tsuki.yuntun.java.app.mapper.AddressMapper;
import com.tsuki.yuntun.java.app.mapper.ShopMapper;
import com.tsuki.yuntun.java.app.service.AddressService;
import com.tsuki.yuntun.java.app.vo.AddressVO;
import com.tsuki.yuntun.java.app.vo.CheckRangeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 地址Service实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    
    private final AddressMapper addressMapper;
    private final ShopMapper shopMapper;
    
    @Value("${app.shop.delivery-range}")
    private BigDecimal deliveryRange;
    
    @Override
    public List<AddressVO> getAddressList(Long userId) {
        List<Address> addresses = addressMapper.selectList(new LambdaQueryWrapper<Address>()
                .eq(Address::getUserId, userId)
                .orderByDesc(Address::getIsDefault)
                .orderByDesc(Address::getCreateTime));
        
        return addresses.stream().map(address -> {
            AddressVO vo = new AddressVO();
            BeanUtils.copyProperties(address, vo);
            return vo;
        }).toList();
    }
    
    @Override
    public AddressVO getAddressDetail(Long userId, Long id) {
        Address address = addressMapper.selectOne(new LambdaQueryWrapper<Address>()
                .eq(Address::getId, id)
                .eq(Address::getUserId, userId));
        
        if (address == null) {
            throw new BusinessException("地址不存在");
        }
        
        AddressVO vo = new AddressVO();
        BeanUtils.copyProperties(address, vo);
        return vo;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addAddress(Long userId, AddressDTO dto) {
        Address address = new Address();
        BeanUtils.copyProperties(dto, address);
        address.setUserId(userId);
        
        // 如果设置为默认地址，则取消其他地址的默认状态
        if (Boolean.TRUE.equals(dto.getIsDefault())) {
            addressMapper.update(null, new LambdaUpdateWrapper<Address>()
                    .eq(Address::getUserId, userId)
                    .set(Address::getIsDefault, false));
        }
        
        addressMapper.insert(address);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editAddress(Long userId, Long id, AddressDTO dto) {
        Address address = addressMapper.selectOne(new LambdaQueryWrapper<Address>()
                .eq(Address::getId, id)
                .eq(Address::getUserId, userId));
        
        if (address == null) {
            throw new BusinessException("地址不存在");
        }
        
        BeanUtils.copyProperties(dto, address);
        
        // 如果设置为默认地址，则取消其他地址的默认状态
        if (Boolean.TRUE.equals(dto.getIsDefault())) {
            addressMapper.update(null, new LambdaUpdateWrapper<Address>()
                    .eq(Address::getUserId, userId)
                    .ne(Address::getId, id)
                    .set(Address::getIsDefault, false));
        }
        
        addressMapper.updateById(address);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAddress(Long userId, Long id) {
        Address address = addressMapper.selectOne(new LambdaQueryWrapper<Address>()
                .eq(Address::getId, id)
                .eq(Address::getUserId, userId));
        
        if (address == null) {
            throw new BusinessException("地址不存在");
        }
        
        addressMapper.deleteById(id);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setDefaultAddress(Long userId, Long id) {
        Address address = addressMapper.selectOne(new LambdaQueryWrapper<Address>()
                .eq(Address::getId, id)
                .eq(Address::getUserId, userId));
        
        if (address == null) {
            throw new BusinessException("地址不存在");
        }
        
        // 取消其他地址的默认状态
        addressMapper.update(null, new LambdaUpdateWrapper<Address>()
                .eq(Address::getUserId, userId)
                .set(Address::getIsDefault, false));
        
        // 设置为默认地址
        address.setIsDefault(true);
        addressMapper.updateById(address);
    }
    
    @Override
    public CheckRangeVO checkRange(CheckRangeDTO dto) {
        // 获取店铺坐标
        Shop shop = shopMapper.selectOne(new LambdaQueryWrapper<Shop>().last("LIMIT 1"));
        if (shop == null) {
            throw new BusinessException("店铺信息不存在");
        }
        
        // 计算距离（单位：公里）
        BigDecimal distance = calculateDistance(
                shop.getLatitude(), shop.getLongitude(),
                dto.getLatitude(), dto.getLongitude());
        
        // 判断是否在配送范围内
        boolean inRange = distance.compareTo(deliveryRange) <= 0;
        
        return CheckRangeVO.builder()
                .inRange(inRange)
                .distance(distance)
                .build();
    }
    
    /**
     * 计算两点之间的距离（Haversine公式）
     */
    private BigDecimal calculateDistance(BigDecimal lat1, BigDecimal lon1, 
                                         BigDecimal lat2, BigDecimal lon2) {
        double earthRadius = 6371; // 地球半径（公里）
        
        double dLat = Math.toRadians(lat2.subtract(lat1).doubleValue());
        double dLon = Math.toRadians(lon2.subtract(lon1).doubleValue());
        
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1.doubleValue())) * 
                Math.cos(Math.toRadians(lat2.doubleValue())) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;
        
        return BigDecimal.valueOf(distance).setScale(2, RoundingMode.HALF_UP);
    }
}

