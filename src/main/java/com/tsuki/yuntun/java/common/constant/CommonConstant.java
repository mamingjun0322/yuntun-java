package com.tsuki.yuntun.java.common.constant;

/**
 * 通用常量
 */
public interface CommonConstant {
    
    /**
     * UTF-8编码
     */
    String UTF8 = "UTF-8";
    
    /**
     * 成功状态码
     */
    Integer SUCCESS_CODE = 200;
    
    /**
     * 失败状态码
     */
    Integer ERROR_CODE = 500;
    
    /**
     * 未授权状态码
     */
    Integer UNAUTHORIZED_CODE = 401;
    
    /**
     * 无权限状态码
     */
    Integer FORBIDDEN_CODE = 403;
    
    /**
     * 资源不存在状态码
     */
    Integer NOT_FOUND_CODE = 404;
    
    /**
     * 默认页码
     */
    Integer DEFAULT_PAGE = 1;
    
    /**
     * 默认每页大小
     */
    Integer DEFAULT_PAGE_SIZE = 10;
    
    /**
     * 最大每页大小
     */
    Integer MAX_PAGE_SIZE = 100;
    
    /**
     * 状态：启用
     */
    Integer STATUS_ENABLE = 1;
    
    /**
     * 状态：禁用
     */
    Integer STATUS_DISABLE = 0;
    
    /**
     * 默认密码
     */
    String DEFAULT_PASSWORD = "123456";
}

