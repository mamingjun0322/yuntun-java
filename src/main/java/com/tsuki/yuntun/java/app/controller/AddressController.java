package com.tsuki.yuntun.java.app.controller;

import com.tsuki.yuntun.java.common.Result;
import com.tsuki.yuntun.java.common.base.BaseController;
import com.tsuki.yuntun.java.app.dto.AddressDTO;
import com.tsuki.yuntun.java.app.dto.CheckRangeDTO;
import com.tsuki.yuntun.java.app.service.AddressService;
import com.tsuki.yuntun.java.app.vo.AddressVO;
import com.tsuki.yuntun.java.app.vo.CheckRangeVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 地址Controller
 */
@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController extends BaseController {
    
    private final AddressService addressService;
    
    /**
     * 获取地址列表
     */
    @GetMapping("/list")
    public Result<List<AddressVO>> getAddressList() {
        Long userId = getCurrentUserId();
        List<AddressVO> list = addressService.getAddressList(userId);
        return Result.success(list);
    }
    
    /**
     * 获取地址详情
     */
    @GetMapping("/detail/{id}")
    public Result<AddressVO> getAddressDetail(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        AddressVO vo = addressService.getAddressDetail(userId, id);
        return Result.success(vo);
    }
    
    /**
     * 新增地址
     */
    @PostMapping("/add")
    public Result<Void> addAddress(@Valid @RequestBody AddressDTO dto) {
        Long userId = getCurrentUserId();
        addressService.addAddress(userId, dto);
        return Result.success();
    }
    
    /**
     * 编辑地址
     */
    @PutMapping("/edit/{id}")
    public Result<Void> editAddress(@PathVariable Long id, @Valid @RequestBody AddressDTO dto) {
        Long userId = getCurrentUserId();
        addressService.editAddress(userId, id, dto);
        return Result.success();
    }
    
    /**
     * 删除地址
     */
    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteAddress(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        addressService.deleteAddress(userId, id);
        return Result.success();
    }
    
    /**
     * 设为默认地址
     */
    @PutMapping("/setDefault/{id}")
    public Result<Void> setDefaultAddress(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        addressService.setDefaultAddress(userId, id);
        return Result.success();
    }
    
    /**
     * 检查配送范围
     */
    @PostMapping("/checkRange")
    public Result<CheckRangeVO> checkRange(@Valid @RequestBody CheckRangeDTO dto) {
        CheckRangeVO vo = addressService.checkRange(dto);
        return Result.success(vo);
    }
}

