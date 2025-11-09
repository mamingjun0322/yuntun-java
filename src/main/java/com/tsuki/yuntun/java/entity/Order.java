package com.tsuki.yuntun.java.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单表
 */
@Data
@TableName("orders")
public class Order implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 订单号
     */
    private String orderNo;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 订单类型 1-堂食 2-外卖
     */
    private Integer type;
    
    /**
     * 订单状态 1-待支付 2-制作中 3-配送中 4-已完成 5-已取消
     */
    private Integer status;
    
    /**
     * 桌号（堂食）
     */
    private String tableNo;
    
    /**
     * 就餐人数（堂食）
     */
    private Integer peopleCount;
    
    /**
     * 地址ID（外卖）
     */
    private Long addressId;
    
    /**
     * 收货人姓名（外卖）
     */
    private String receiverName;
    
    /**
     * 收货人电话（外卖）
     */
    private String receiverPhone;
    
    /**
     * 收货地址（外卖）
     */
    private String address;
    
    /**
     * 配送时间（外卖）
     */
    private String deliveryTime;
    
    /**
     * 餐具份数（外卖）
     */
    private Integer tableware;
    
    /**
     * 商品金额
     */
    private BigDecimal goodsAmount;
    
    /**
     * 配送费
     */
    private BigDecimal deliveryFee;
    
    /**
     * 打包费
     */
    private BigDecimal packingFee;
    
    /**
     * 优惠券抵扣
     */
    private BigDecimal couponDiscount;
    
    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;
    
    /**
     * 支付链接
     */
    private String paymentUrl;
    
    /**
     * 支付链接ID
     */
    private Long paymentLinkId;
    
    /**
     * 优惠券ID
     */
    private Long couponId;
    
    /**
     * 订单备注
     */
    private String remark;
    
    /**
     * 支付方式 1-微信 2-支付宝 3-余额
     */
    private Integer payType;
    
    /**
     * 支付时间
     */
    private LocalDateTime payTime;
    
    /**
     * 完成时间
     */
    private LocalDateTime finishTime;
    
    /**
     * 取消原因
     */
    private String cancelReason;
    
    /**
     * 是否已评价
     */
    private Boolean commented;
    
    /**
     * 逻辑删除标志
     */
    @TableLogic
    private Integer deleted;
    
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}

