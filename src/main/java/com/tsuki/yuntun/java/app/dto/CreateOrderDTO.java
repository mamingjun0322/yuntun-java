package com.tsuki.yuntun.java.app.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 创建订单DTO
 */
@Data
public class CreateOrderDTO {
    
    /**
     * 订单类型 1-堂食 2-外卖
     */
    @NotNull(message = "订单类型不能为空")
    private Integer type;
    
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
     * 收货地址（外卖）
     */
    private String address;
    
    /**
     * 收货人姓名（外卖）
     */
    private String receiverName;
    
    /**
     * 收货人电话（外卖）
     */
    private String receiverPhone;
    
    /**
     * 配送时间（外卖）
     */
    private String deliveryTime;
    
    /**
     * 餐具份数（外卖）
     */
    private Integer tableware;
    
    /**
     * 配送费（外卖）
     */
    private BigDecimal deliveryFee;
    
    /**
     * 打包费（外卖）
     */
    private BigDecimal packingFee;
    
    /**
     * 商品列表
     */
    @NotEmpty(message = "商品列表不能为空")
    private List<OrderGoodsItem> goodsList;
    
    /**
     * 商品金额
     */
    @NotNull(message = "商品金额不能为空")
    private BigDecimal goodsAmount;
    
    /**
     * 订单总金额
     */
    @NotNull(message = "订单总金额不能为空")
    private BigDecimal totalAmount;
    
    /**
     * 优惠券ID
     */
    private Long couponId;
    
    /**
     * 优惠券抵扣
     */
    private BigDecimal couponDiscount;
    
    /**
     * 订单备注
     */
    private String remark;
    
    @Data
    public static class OrderGoodsItem {
        private Long id;
        private String name;
        private String image;
        private BigDecimal price;
        private Integer quantity;
        private List<SpecItem> specs;
        private String remark;
    }
    
    @Data
    public static class SpecItem {
        private String name;
        private String value;
    }
}

