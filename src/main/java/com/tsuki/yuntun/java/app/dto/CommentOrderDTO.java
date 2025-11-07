package com.tsuki.yuntun.java.app.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 订单评价DTO
 */
@Data
public class CommentOrderDTO {
    
    /**
     * 商品评分
     */
    @NotNull(message = "商品评分不能为空")
    @Min(value = 1, message = "商品评分最低1分")
    @Max(value = 5, message = "商品评分最高5分")
    private Integer goodsScore;
    
    /**
     * 服务评分
     */
    @NotNull(message = "服务评分不能为空")
    @Min(value = 1, message = "服务评分最低1分")
    @Max(value = 5, message = "服务评分最高5分")
    private Integer serviceScore;
    
    /**
     * 评价内容
     */
    @NotBlank(message = "评价内容不能为空")
    private String content;
    
    /**
     * 评价图片
     */
    private List<String> images;
    
    /**
     * 是否匿名
     */
    private Boolean anonymous;
}

