package com.tsuki.yuntun.java.app.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.tsuki.yuntun.java.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper
 */
@Mapper
public interface UserMapper extends MPJBaseMapper<User> {
}

