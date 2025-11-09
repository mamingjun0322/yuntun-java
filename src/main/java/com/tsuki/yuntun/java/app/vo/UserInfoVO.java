package com.tsuki.yuntun.java.app.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO {
    
    /**
     * 用户ID
     */
    private Long id;
    
    /**
     * 昵称
     */
    private String nickname;
    
    /**
     * 头像
     */
    private String avatar;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 积分
     */
    private Integer points;
    
    /**
     * 会员等级
     */
    private Integer level;
    
    /**
     * 性别 (0-未知 1-男 2-女)
     */
    private Integer gender;
    
    /**
     * 生日
     */
    private String birthday;
    
    /**
     * 状态 (1-正常 0-禁用)
     */
    private Integer status;
    
    /**
     * 优惠券数量
     */
    private Integer couponCount;
}

