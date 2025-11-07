package com.tsuki.yuntun.java.app.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单VO
 */
@Data
public class OrderVO {
    
    /**
     * 订单ID
     */
    private Long id;
    
    /**
     * 订单号
     */
    private String orderNo;
    
    /**
     * 订单类型
     */
    private Integer type;
    
    /**
     * 订单状态
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
     * 是否已评价
     */
    private Boolean commented;
    
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @Data
    public static class OrderGoodsVO {
        private Long id;
        private String name;
        private String image;
        private BigDecimal price;
        private Integer quantity;
        private List<SpecVO> specs;
        private String remark;
    }
    
    @Data
    public static class SpecVO {
        private String name;
        private String value;
    }
}

