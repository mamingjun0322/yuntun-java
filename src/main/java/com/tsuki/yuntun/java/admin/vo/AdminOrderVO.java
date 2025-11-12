package com.tsuki.yuntun.java.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 后台订单VO
 */
@Data
public class AdminOrderVO {
    
    /**
     * 订单ID
     */
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
     * 桌号
     */
    private String tableNo;
    
    /**
     * 就餐人数
     */
    private Integer peopleCount;
    
    /**
     * 收货人姓名
     */
    private String receiverName;
    
    /**
     * 收货人电话
     */
    private String receiverPhone;
    
    /**
     * 收货地址
     */
    private String address;
    
    /**
     * 配送时间
     */
    private String deliveryTime;
    
    /**
     * 商品列表
     */
    private List<OrderGoodsVO> goodsList;
    
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
     * 订单备注
     */
    private String remark;
    
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    /**
     * 订单商品VO
     */
    @Data
    public static class OrderGoodsVO {
        private Long id;
        private String name;
        private String image;
        private BigDecimal price;
        private Integer quantity;
        private String specs;
        private String remark;
    }
}
