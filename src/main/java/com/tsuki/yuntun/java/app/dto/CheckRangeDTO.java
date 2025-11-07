package com.tsuki.yuntun.java.app.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 检查配送范围DTO
 */
@Data
public class CheckRangeDTO {
    
    /**
     * 纬度
     */
    @NotNull(message = "纬度不能为空")
    private BigDecimal latitude;
    
    /**
     * 经度
     */
    @NotNull(message = "经度不能为空")
    private BigDecimal longitude;
}

