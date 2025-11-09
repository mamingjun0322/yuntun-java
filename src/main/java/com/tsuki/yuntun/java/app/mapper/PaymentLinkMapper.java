package com.tsuki.yuntun.java.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tsuki.yuntun.java.entity.PaymentLink;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付链接Mapper
 */
@Mapper
public interface PaymentLinkMapper extends BaseMapper<PaymentLink> {
}

