package com.tsuki.yuntun.java.app.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.common.constant.RedisConstant;
import com.tsuki.yuntun.java.common.exception.BusinessException;
import cn.hutool.json.JSONObject;
import com.tsuki.yuntun.java.app.dto.AccountLoginDTO;
import com.tsuki.yuntun.java.app.dto.SendCodeDTO;
import com.tsuki.yuntun.java.app.dto.UpdateUserInfoDTO;
import com.tsuki.yuntun.java.app.dto.UserLoginDTO;
import com.tsuki.yuntun.java.app.dto.WxLoginDTO;
import com.tsuki.yuntun.java.entity.PointsHistory;
import com.tsuki.yuntun.java.entity.User;
import com.tsuki.yuntun.java.entity.UserCoupon;
import com.tsuki.yuntun.java.app.mapper.PointsHistoryMapper;
import com.tsuki.yuntun.java.app.mapper.UserCouponMapper;
import com.tsuki.yuntun.java.app.mapper.UserMapper;
import com.tsuki.yuntun.java.app.service.UserService;
import com.tsuki.yuntun.java.util.SaTokenUtil;
import com.tsuki.yuntun.java.util.RedisUtil;
import com.tsuki.yuntun.java.util.WxApiUtil;
import com.tsuki.yuntun.java.app.vo.LoginVO;
import com.tsuki.yuntun.java.app.vo.PointsHistoryVO;
import com.tsuki.yuntun.java.app.vo.UserInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用户Service实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserMapper userMapper;
    private final UserCouponMapper userCouponMapper;
    private final PointsHistoryMapper pointsHistoryMapper;
    private final RedisUtil redisUtil;
    private final WxApiUtil wxApiUtil;
    private final com.tsuki.yuntun.java.app.mapper.SystemConfigMapper systemConfigMapper;
    
    /**
     * 从数据库获取短信过期时间配置
     */
    private Long getSmsExpireTime() {
        String value = getConfigValue("sms.expire_time");
        return value != null ? Long.parseLong(value) : 300L; // 默认300秒
    }
    
    /**
     * 从数据库获取签到积分配置
     */
    private Integer getSignInPoints() {
        String value = getConfigValue("points.sign_in");
        return value != null ? Integer.parseInt(value) : 10; // 默认10积分
    }
    
    /**
     * 从数据库获取订单积分配置
     */
    private Integer getOrderPoints() {
        String value = getConfigValue("points.order");
        return value != null ? Integer.parseInt(value) : 10; // 默认10积分
    }
    
    /**
     * 获取配置值
     */
    private String getConfigValue(String configKey) {
        com.tsuki.yuntun.java.entity.SystemConfig config = systemConfigMapper.selectOne(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<com.tsuki.yuntun.java.entity.SystemConfig>()
                        .eq(com.tsuki.yuntun.java.entity.SystemConfig::getConfigKey, configKey));
        return config != null ? config.getConfigValue() : null;
    }
    
    /**
     * 微信登录
     */
    @Override
    @Transactional
    public LoginVO wxLogin(WxLoginDTO dto) {
        try {
            // 1. 调用微信接口获取openid和session_key
            JSONObject sessionData = wxApiUtil.code2Session(dto.getCode());
            String openid = wxApiUtil.getOpenid(sessionData);
            String unionid = wxApiUtil.getUnionid(sessionData);
            
            if (openid == null || openid.isEmpty()) {
                throw new BusinessException("微信登录失败，未获取到openid");
            }
            
            log.info("微信登录成功，openid: {}, unionid: {}", openid, unionid);
            
            // 2. 查询用户是否存在
            User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                    .eq(User::getOpenid, openid));
            
            boolean isNewUser = false;
            
            // 3. 如果用户不存在，创建新用户
            if (user == null) {
                user = new User();
                user.setOpenid(openid);
                user.setUnionid(unionid);
                
                // 设置用户昵称和头像（如果前端传了）
                if (dto.getNickName() != null && !dto.getNickName().isEmpty()) {
                    user.setNickname(dto.getNickName());
                }
                if (dto.getAvatar() != null && !dto.getAvatar().isEmpty()) {
                    user.setAvatar(dto.getAvatar());
                }
                
                // 设置默认值
                user.setPoints(0);
                user.setStatus(1); // 正常状态
                
                userMapper.insert(user);
                isNewUser = true;
                
                log.info("创建新用户成功，userId: {}", user.getId());
            } else {
                // 更新unionid（如果微信返回了）
                if (unionid != null && !unionid.isEmpty() && 
                    (user.getUnionid() == null || user.getUnionid().isEmpty())) {
                    user.setUnionid(unionid);
                    userMapper.updateById(user);
                }
                
                log.info("用户已存在，userId: {}", user.getId());
            }
            
            // 4. 生成token
            String token = SaTokenUtil.login(user.getId());
            
            // 5. 获取用户信息
            UserInfoVO userInfo = getUserInfo(user.getId());
            
            // 6. 构建返回结果
            return LoginVO.builder()
                    .token(token)
                    .isNewUser(isNewUser)
                    .userInfo(userInfo)
                    .build();
                    
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("微信登录失败", e);
            throw new BusinessException("微信登录失败，请重试");
        }
    }
    
    @Override
    public void sendCode(SendCodeDTO dto) {
        // 生成6位随机验证码
        String code = RandomUtil.randomNumbers(6);
        
        // 存入Redis，5分钟过期
        String key = RedisConstant.SMS_CODE_PREFIX + dto.getPhone();
        redisUtil.set(key, code, getSmsExpireTime(), TimeUnit.SECONDS);
        
        // TODO: 这里应该调用短信服务发送验证码
        log.info("手机号：{}，验证码：{}", dto.getPhone(), code);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginVO login(UserLoginDTO dto) {
        // 验证验证码
        String key = RedisConstant.SMS_CODE_PREFIX + dto.getPhone();
        Object codeObj = redisUtil.get(key);
        if (codeObj == null) {
            throw new BusinessException("验证码已过期");
        }
        
        String code = codeObj.toString();
        if (!code.equals(dto.getCode())) {
            throw new BusinessException("验证码错误");
        }
        
        // 删除验证码
        redisUtil.delete(key);
        
        // 查询用户是否存在
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getPhone, dto.getPhone()));
        
        boolean isNewUser = false;
        if (user == null) {
            // 用户不存在，自动注册
            user = new User();
            user.setPhone(dto.getPhone());
            user.setNickname("用户" + dto.getPhone().substring(7));
            user.setAvatar("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
            user.setPoints(0);
            user.setLevel(1);
            userMapper.insert(user);
            isNewUser = true;
        }
        
        // 生成token
        String token = SaTokenUtil.login(user.getId());
        
        // 获取用户信息
        UserInfoVO userInfo = getUserInfo(user.getId());
        
        return LoginVO.builder()
                .token(token)
                .isNewUser(isNewUser)
                .userInfo(userInfo)
                .build();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginVO accountLogin(AccountLoginDTO dto) {
        // 查询用户
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, dto.getUsername()));
        
        boolean isNewUser = false;
        if (user == null) {
            // 用户不存在，自动注册
            user = new User();
            user.setUsername(dto.getUsername());
            user.setPassword(DigestUtil.md5Hex(dto.getPassword())); // 使用MD5 32位加密
            user.setNickname(dto.getUsername());
            user.setAvatar("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
            user.setPoints(0);
            user.setStatus(1);
            user.setLevel(1);
            userMapper.insert(user);
            isNewUser = true;
        } else {
            // 验证密码 - 使用MD5验证
            String password = DigestUtil.md5Hex(dto.getPassword());
            if (!password.equals(user.getPassword())) {
                throw new BusinessException("密码错误");
            }
        }
        
        // 生成token
        String token = SaTokenUtil.login(user.getId());
        
        // 获取用户信息
        UserInfoVO userInfo = getUserInfo(user.getId());
        
        return LoginVO.builder()
                .token(token)
                .isNewUser(isNewUser)
                .userInfo(userInfo)
                .build();
    }
    
    @Override
    public UserInfoVO getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        // 查询优惠券数量
        Long couponCount = userCouponMapper.selectCount(new LambdaQueryWrapper<UserCoupon>()
                .eq(UserCoupon::getUserId, userId)
                .eq(UserCoupon::getStatus, 0));
        
        return UserInfoVO.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .phone(user.getPhone())
                .points(user.getPoints())
                .level(user.getLevel())
                .gender(user.getGender())
                .birthday(user.getBirthday())
                .couponCount(couponCount.intValue())
                .build();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(Long userId, UpdateUserInfoDTO dto) {
        User user = new User();
        user.setId(userId);
        BeanUtils.copyProperties(dto, user);
        userMapper.updateById(user);
    }
    
    @Override
    public Integer getUserPoints(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return user.getPoints();
    }
    
    @Override
    public Page<PointsHistoryVO> getPointsHistory(Long userId, Integer page, Integer pageSize) {
        Page<PointsHistory> pageParam = new Page<>(page, pageSize);
        Page<PointsHistory> result = pointsHistoryMapper.selectPage(pageParam,
                new LambdaQueryWrapper<PointsHistory>()
                        .eq(PointsHistory::getUserId, userId)
                        .orderByDesc(PointsHistory::getCreateTime));
        
        Page<PointsHistoryVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        List<PointsHistoryVO> voList = result.getRecords().stream().map(history -> {
            PointsHistoryVO vo = new PointsHistoryVO();
            vo.setId(history.getId());
            vo.setTitle(history.getTitle());
            vo.setPoints(history.getPoints());
            vo.setType(history.getType());
            vo.setTime(history.getCreateTime());
            return vo;
        }).toList();
        voPage.setRecords(voList);
        
        return voPage;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer signIn(Long userId) {
        // 检查今天是否已签到
        String key = RedisConstant.SIGN_IN_PREFIX + userId + ":" + LocalDate.now();
        if (Boolean.TRUE.equals(redisUtil.hasKey(key))) {
            throw new BusinessException("今天已签到");
        }
        
        // 增加积分
        User user = userMapper.selectById(userId);
        user.setPoints(user.getPoints() + getSignInPoints());
        userMapper.updateById(user);
        
        // 记录积分明细
        PointsHistory history = new PointsHistory();
        history.setUserId(userId);
        history.setTitle("签到");
        history.setPoints(getSignInPoints());
        history.setType(1);
        pointsHistoryMapper.insert(history);
        
        // 记录签到状态，明天0点过期
        long seconds = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0)
                .toEpochSecond(java.time.ZoneOffset.ofHours(8))
                - LocalDateTime.now().toEpochSecond(java.time.ZoneOffset.ofHours(8));
        redisUtil.set(key, "1", seconds, TimeUnit.SECONDS);
        
        return getSignInPoints();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(Long userId, com.tsuki.yuntun.java.app.dto.UpdatePasswordDTO dto) {
        // 查询用户
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        // 验证旧密码（假设使用 BCrypt 加密，实际项目中应该使用 PasswordEncoder）
        // 这里简化处理，实际应该使用加密比对
        if (!user.getPassword().equals(dto.getOldPassword())) {
            throw new BusinessException("旧密码错误");
        }
        
        // 更新密码
        user.setPassword(dto.getNewPassword());
        userMapper.updateById(user);
        
        log.info("用户ID: {} 修改密码成功", userId);
    }
}

