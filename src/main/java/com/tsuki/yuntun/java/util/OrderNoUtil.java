package com.tsuki.yuntun.java.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 订单号生成工具类
 */
public class OrderNoUtil {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final AtomicInteger SEQUENCE = new AtomicInteger(1000);
    
    /**
     * 生成订单号
     * 格式：yyyyMMddHHmmss + 4位序列号
     */
    public static String generateOrderNo() {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        int sequence = SEQUENCE.getAndIncrement();
        
        // 序列号重置
        if (sequence >= 9999) {
            SEQUENCE.set(1000);
        }
        
        return timestamp + sequence;
    }
    
    /**
     * 生成订单号（带前缀）
     */
    public static String generateOrderNo(String prefix) {
        return prefix + generateOrderNo();
    }
}

