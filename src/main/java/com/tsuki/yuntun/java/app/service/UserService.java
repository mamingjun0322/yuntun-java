package com.tsuki.yuntun.java.app.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.app.dto.AccountLoginDTO;
import com.tsuki.yuntun.java.app.dto.SendCodeDTO;
import com.tsuki.yuntun.java.app.dto.UpdateUserInfoDTO;
import com.tsuki.yuntun.java.app.dto.UserLoginDTO;
import com.tsuki.yuntun.java.app.vo.LoginVO;
import com.tsuki.yuntun.java.app.vo.PointsHistoryVO;
import com.tsuki.yuntun.java.app.vo.UserInfoVO;

/**
 * 用户Service
 */
public interface UserService {
    
    /**
     * 微信登录
     */
    LoginVO wxLogin(com.tsuki.yuntun.java.app.dto.WxLoginDTO dto);
    
    /**
     * 发送验证码
     */
    void sendCode(SendCodeDTO dto);
    
    /**
     * 手机号登录
     */
    LoginVO login(UserLoginDTO dto);
    
    /**
     * 账号密码登录
     */
    LoginVO accountLogin(AccountLoginDTO dto);
    
    /**
     * 获取用户信息
     */
    UserInfoVO getUserInfo(Long userId);
    
    /**
     * 更新用户信息
     */
    void updateUserInfo(Long userId, UpdateUserInfoDTO dto);
    
    /**
     * 获取用户积分
     */
    Integer getUserPoints(Long userId);
    
    /**
     * 获取积分明细
     */
    Page<PointsHistoryVO> getPointsHistory(Long userId, Integer page, Integer pageSize);
    
    /**
     * 签到
     */
    Integer signIn(Long userId);
    
    /**
     * 修改密码
     */
    void updatePassword(Long userId, com.tsuki.yuntun.java.app.dto.UpdatePasswordDTO dto);
}

