package com.tsuki.yuntun.java.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 距离计算工具类
 */
public class DistanceUtil {
    
    /**
     * 地球半径（公里）
     */
    private static final double EARTH_RADIUS = 6371.0;
    
    /**
     * 计算两点之间的距离（Haversine公式）
     * 
     * @param lat1 纬度1
     * @param lon1 经度1
     * @param lat2 纬度2
     * @param lon2 经度2
     * @return 距离（公里）
     */
    public static BigDecimal calculateDistance(BigDecimal lat1, BigDecimal lon1, 
                                               BigDecimal lat2, BigDecimal lon2) {
        double dLat = Math.toRadians(lat2.subtract(lat1).doubleValue());
        double dLon = Math.toRadians(lon2.subtract(lon1).doubleValue());
        
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1.doubleValue())) * 
                   Math.cos(Math.toRadians(lat2.doubleValue())) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;
        
        return BigDecimal.valueOf(distance).setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * 检查是否在配送范围内
     * 
     * @param distance 实际距离
     * @param maxRange 最大配送范围
     * @return 是否在范围内
     */
    public static boolean inRange(BigDecimal distance, BigDecimal maxRange) {
        return distance.compareTo(maxRange) <= 0;
    }
}

