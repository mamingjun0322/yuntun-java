package com.tsuki.yuntun.java.app.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.common.Result;
import com.tsuki.yuntun.java.common.base.BaseController;
import com.tsuki.yuntun.java.app.dto.AccountLoginDTO;
import com.tsuki.yuntun.java.app.dto.SendCodeDTO;
import com.tsuki.yuntun.java.app.dto.UpdateUserInfoDTO;
import com.tsuki.yuntun.java.app.dto.UserLoginDTO;
import com.tsuki.yuntun.java.app.service.UserService;
import com.tsuki.yuntun.java.app.vo.LoginVO;
import com.tsuki.yuntun.java.app.vo.PointsHistoryVO;
import com.tsuki.yuntun.java.app.vo.UserInfoVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户Controller
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends BaseController {
    
    private final UserService userService;
    
    /**
     * 发送验证码
     */
    @PostMapping("/sendCode")
    public Result<Void> sendCode(@Valid @RequestBody SendCodeDTO dto) {
        userService.sendCode(dto);
        return Result.success("验证码已发送", null);
    }
    
    /**
     * 微信登录
     */
    @PostMapping("/wxLogin")
    public Result<LoginVO> wxLogin(@Valid @RequestBody com.tsuki.yuntun.java.app.dto.WxLoginDTO dto) {
        LoginVO vo = userService.wxLogin(dto);
        return Result.success(vo);
    }
    
    /**
     * 手机号登录
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody UserLoginDTO dto) {
        LoginVO vo = userService.login(dto);
        return Result.success(vo);
    }
    
    /**
     * 账号密码登录
     */
    @PostMapping("/accountLogin")
    public Result<LoginVO> accountLogin(@Valid @RequestBody AccountLoginDTO dto) {
        LoginVO vo = userService.accountLogin(dto);
        return Result.success("登录成功", vo);
    }
    
    /**
     * 获取用户信息
     */
    @GetMapping("/info")
    public Result<UserInfoVO> getUserInfo() {
        Long userId = getCurrentUserId();
        return Result.success(userService.getUserInfo(userId));
    }
    
    /**
     * 更新用户信息
     */
    @PutMapping("/update")
    public Result<Void> updateUserInfo(@RequestBody UpdateUserInfoDTO dto) {
        Long userId = getCurrentUserId();
        userService.updateUserInfo(userId, dto);
        return Result.success();
    }
    
    /**
     * 获取用户积分
     */
    @GetMapping("/points")
    public Result<Map<String, Integer>> getUserPoints() {
        Long userId = getCurrentUserId();
        Integer points = userService.getUserPoints(userId);
        Map<String, Integer> result = new HashMap<>();
        result.put("points", points);
        return Result.success(result);
    }
    
    /**
     * 获取积分明细
     */
    @GetMapping("/points/history")
    public Result<Page<PointsHistoryVO>> getPointsHistory(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Long userId = getCurrentUserId();
        return Result.success(userService.getPointsHistory(userId, page, pageSize));
    }
    
    /**
     * 签到
     */
    @PostMapping("/signIn")
    public Result<Map<String, Integer>> signIn() {
        Long userId = getCurrentUserId();
        Integer points = userService.signIn(userId);
        Map<String, Integer> result = new HashMap<>();
        result.put("points", points);
        return Result.success(result);
    }
}

