package com.tsuki.yuntun.java.app.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 检查配送范围VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckRangeVO {
    
    /**
     * 是否在配送范围内
     */
    private Boolean inRange;
    
    /**
     * 距离（公里）
     */
    private BigDecimal distance;
}

