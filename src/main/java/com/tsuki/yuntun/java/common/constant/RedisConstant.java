package com.tsuki.yuntun.java.common.constant;

/**
 * Redis常量
 */
public interface RedisConstant {
    
    /**
     * 验证码key前缀
     */
    String SMS_CODE_PREFIX = "sms:code:";
    
    /**
     * 用户token前缀
     */
    String USER_TOKEN_PREFIX = "user:token:";
    
    /**
     * 用户信息缓存前缀
     */
    String USER_INFO_PREFIX = "user:info:";
    
    /**
     * 商品信息缓存前缀
     */
    String GOODS_INFO_PREFIX = "goods:info:";
    
    /**
     * 商品列表缓存前缀
     */
    String GOODS_LIST_PREFIX = "goods:list:";
    
    /**
     * 分类列表缓存前缀
     */
    String CATEGORY_LIST = "category:list";
    
    /**
     * 店铺信息缓存
     */
    String SHOP_INFO = "shop:info";
    
    /**
     * 轮播图缓存
     */
    String BANNER_LIST = "banner:list";
    
    /**
     * 签到记录前缀
     */
    String SIGN_IN_PREFIX = "sign:in:";
}

