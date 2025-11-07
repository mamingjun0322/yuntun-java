package com.tsuki.yuntun.java.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单评价表
 */
@Data
@TableName("order_comment")
public class OrderComment implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 订单ID
     */
    private Long orderId;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 商品评分
     */
    private Integer goodsScore;
    
    /**
     * 服务评分
     */
    private Integer serviceScore;
    
    /**
     * 评价内容
     */
    private String content;
    
    /**
     * 评价图片（多张用逗号分隔）
     */
    private String images;
    
    /**
     * 是否匿名
     */
    private Boolean anonymous;
    
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
}

