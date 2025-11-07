package com.tsuki.yuntun.java.util;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tsuki.yuntun.java.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 微信API工具类
 */
@Slf4j
@Component
public class WxApiUtil {
    
    @Value("${wx.mini.appid}")
    private String appid;
    
    @Value("${wx.mini.secret}")
    private String secret;
    
    /**
     * 微信登录接口URL
     */
    private static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";
    
    /**
     * 调用微信code2Session接口
     * 
     * @param code 微信登录凭证
     * @return 包含openid和session_key的JSON对象
     */
    public JSONObject code2Session(String code) {
        try {
            // 构建请求参数
            String url = String.format("%s?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                    WX_LOGIN_URL, appid, secret, code);
            
            // 发送GET请求
            String response = HttpUtil.get(url);
            log.info("微信code2Session响应: {}", response);
            
            // 解析响应
            JSONObject jsonObject = JSONUtil.parseObj(response);
            
            // 检查是否有错误
            Integer errcode = jsonObject.getInt("errcode", 0);
            if (errcode != 0) {
                String errmsg = jsonObject.getStr("errmsg", "微信登录失败");
                log.error("微信登录失败: errcode={}, errmsg={}", errcode, errmsg);
                
                // 根据错误码返回友好的错误信息
                switch (errcode) {
                    case 40029:
                        throw new BusinessException("登录凭证code无效，请重新登录");
                    case 45011:
                        throw new BusinessException("API调用太频繁，请稍候再试");
                    case 40226:
                        throw new BusinessException("高风险用户，登录被拦截");
                    case -1:
                        throw new BusinessException("微信服务器繁忙，请稍候再试");
                    default:
                        throw new BusinessException("微信登录失败: " + errmsg);
                }
            }
            
            return jsonObject;
            
        } catch (Exception e) {
            log.error("调用微信code2Session接口失败", e);
            throw new BusinessException("微信登录失败，请重试");
        }
    }
    
    /**
     * 从code2Session结果中获取openid
     */
    public String getOpenid(JSONObject sessionData) {
        return sessionData.getStr("openid");
    }
    
    /**
     * 从code2Session结果中获取unionid
     */
    public String getUnionid(JSONObject sessionData) {
        return sessionData.getStr("unionid");
    }
    
    /**
     * 从code2Session结果中获取session_key
     */
    public String getSessionKey(JSONObject sessionData) {
        return sessionData.getStr("session_key");
    }
}

