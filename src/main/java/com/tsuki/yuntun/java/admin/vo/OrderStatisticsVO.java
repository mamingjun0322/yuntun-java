package com.tsuki.yuntun.java.admin.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单统计VO
 */
@Data
public class OrderStatisticsVO {
    
    /**
     * 今日订单数
     */
    private Long todayOrderCount;
    
    /**
     * 今日营业额
     */
    private BigDecimal todayRevenue;
    
    /**
     * 待处理订单数
     */
    private Long pendingOrderCount;
    
    /**
     * 总订单数
     */
    private Long totalOrderCount;
    
    /**
     * 总营业额
     */
    private BigDecimal totalRevenue;
    
    /**
     * 最近订单列表
     */
    private List<RecentOrderVO> recentOrders;
    
    /**
     * 订单趋势
     */
    private List<OrderTrendVO> orderTrend;
    
    /**
     * 订单类型分布
     */
    private List<OrderTypeDistributionVO> categoryDistribution;
    
    /**
     * 最近订单VO
     */
    @Data
    public static class RecentOrderVO {
        private Long id;
        private String orderNo;
        private Integer type;
        private Integer status;
        private BigDecimal totalAmount;
        private String createTime;
    }
    
    /**
     * 订单趋势VO
     */
    @Data
    public static class OrderTrendVO {
        private String date;
        private Long count;
        private BigDecimal amount;
    }
    
    /**
     * 订单类型分布VO
     */
    @Data
    public static class OrderTypeDistributionVO {
        private String name;
        private Long value;
    }
}
