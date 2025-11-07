package com.tsuki.yuntun.java.app.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsuki.yuntun.java.common.exception.BusinessException;
import com.tsuki.yuntun.java.app.dto.CommentOrderDTO;
import com.tsuki.yuntun.java.app.dto.CreateOrderDTO;
import com.tsuki.yuntun.java.entity.Order;
import com.tsuki.yuntun.java.entity.OrderComment;
import com.tsuki.yuntun.java.entity.OrderGoods;
import com.tsuki.yuntun.java.app.mapper.OrderCommentMapper;
import com.tsuki.yuntun.java.app.mapper.OrderGoodsMapper;
import com.tsuki.yuntun.java.app.mapper.OrderMapper;
import com.tsuki.yuntun.java.app.service.OrderService;
import com.tsuki.yuntun.java.app.vo.OrderVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 订单Service实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    
    private final OrderMapper orderMapper;
    private final OrderGoodsMapper orderGoodsMapper;
    private final OrderCommentMapper orderCommentMapper;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createOrder(Long userId, CreateOrderDTO dto) {
        // 创建订单
        Order order = new Order();
        BeanUtils.copyProperties(dto, order);
        order.setUserId(userId);
        order.setOrderNo(generateOrderNo());
        order.setStatus(1); // 待支付
        
        // 设置配送费和打包费
        if (dto.getType() == 2) {
            order.setDeliveryFee(dto.getDeliveryFee() != null ? dto.getDeliveryFee() : BigDecimal.ZERO);
            order.setPackingFee(dto.getPackingFee() != null ? dto.getPackingFee() : BigDecimal.ZERO);
        } else {
            order.setDeliveryFee(BigDecimal.ZERO);
            order.setPackingFee(BigDecimal.ZERO);
        }
        
        order.setCouponDiscount(dto.getCouponDiscount() != null ? dto.getCouponDiscount() : BigDecimal.ZERO);
        order.setCommented(false);
        
        orderMapper.insert(order);
        
        // 保存订单商品
        for (CreateOrderDTO.OrderGoodsItem item : dto.getGoodsList()) {
            OrderGoods orderGoods = new OrderGoods();
            orderGoods.setOrderId(order.getId());
            orderGoods.setGoodsId(item.getId());
            orderGoods.setName(item.getName());
            orderGoods.setImage(item.getImage());
            orderGoods.setPrice(item.getPrice());
            orderGoods.setQuantity(item.getQuantity());
            orderGoods.setSpecs(item.getSpecs() != null ? JSONUtil.toJsonStr(item.getSpecs()) : null);
            orderGoods.setRemark(item.getRemark());
            orderGoodsMapper.insert(orderGoods);
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("orderId", order.getId());
        result.put("orderNo", order.getOrderNo());
        
        return result;
    }
    
    @Override
    public Page<OrderVO> getOrderList(Long userId, Integer status, Integer page, Integer pageSize) {
        Page<Order> pageParam = new Page<>(page, pageSize);
        
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<Order>()
                .eq(Order::getUserId, userId);
        
        if (status != null && status > 0) {
            wrapper.eq(Order::getStatus, status);
        }
        
        wrapper.orderByDesc(Order::getCreateTime);
        
        Page<Order> result = orderMapper.selectPage(pageParam, wrapper);
        
        Page<OrderVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        List<OrderVO> voList = result.getRecords().stream().map(this::convertToVO).toList();
        voPage.setRecords(voList);
        
        return voPage;
    }
    
    @Override
    public OrderVO getOrderDetail(Long userId, Long id) {
        Order order = orderMapper.selectOne(new LambdaQueryWrapper<Order>()
                .eq(Order::getId, id)
                .eq(Order::getUserId, userId));
        
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        return convertToVO(order);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(Long userId, Long id, String reason) {
        Order order = orderMapper.selectOne(new LambdaQueryWrapper<Order>()
                .eq(Order::getId, id)
                .eq(Order::getUserId, userId));
        
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        if (order.getStatus() != 1) {
            throw new BusinessException("只能取消待支付的订单");
        }
        
        order.setStatus(5); // 已取消
        order.setCancelReason(reason);
        orderMapper.updateById(order);
    }
    
    @Override
    public Integer getOrderStatus(Long userId, Long id) {
        Order order = orderMapper.selectOne(new LambdaQueryWrapper<Order>()
                .eq(Order::getId, id)
                .eq(Order::getUserId, userId));
        
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        return order.getStatus();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void commentOrder(Long userId, Long id, CommentOrderDTO dto) {
        Order order = orderMapper.selectOne(new LambdaQueryWrapper<Order>()
                .eq(Order::getId, id)
                .eq(Order::getUserId, userId));
        
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        if (order.getStatus() != 4) {
            throw new BusinessException("只能评价已完成的订单");
        }
        
        if (Boolean.TRUE.equals(order.getCommented())) {
            throw new BusinessException("订单已评价");
        }
        
        // 保存评价
        OrderComment comment = new OrderComment();
        comment.setOrderId(id);
        comment.setUserId(userId);
        comment.setGoodsScore(dto.getGoodsScore());
        comment.setServiceScore(dto.getServiceScore());
        comment.setContent(dto.getContent());
        comment.setImages(dto.getImages() != null ? String.join(",", dto.getImages()) : null);
        comment.setAnonymous(dto.getAnonymous());
        orderCommentMapper.insert(comment);
        
        // 更新订单评价状态
        order.setCommented(true);
        orderMapper.updateById(order);
    }
    
    /**
     * 生成订单号
     */
    private String generateOrderNo() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int random = (int) ((Math.random() * 9 + 1) * 1000);
        return timestamp + random;
    }
    
    /**
     * 转换为VO
     */
    private OrderVO convertToVO(Order order) {
        OrderVO vo = new OrderVO();
        BeanUtils.copyProperties(order, vo);
        
        // 查询订单商品
        List<OrderGoods> goodsList = orderGoodsMapper.selectList(
                new LambdaQueryWrapper<OrderGoods>().eq(OrderGoods::getOrderId, order.getId()));
        
        List<OrderVO.OrderGoodsVO> goodsVOList = goodsList.stream().map(goods -> {
            OrderVO.OrderGoodsVO goodsVO = new OrderVO.OrderGoodsVO();
            goodsVO.setId(goods.getId());
            goodsVO.setName(goods.getName());
            goodsVO.setImage(goods.getImage());
            goodsVO.setPrice(goods.getPrice());
            goodsVO.setQuantity(goods.getQuantity());
            goodsVO.setRemark(goods.getRemark());
            
            // 解析规格
            if (goods.getSpecs() != null && !goods.getSpecs().isEmpty()) {
                List<OrderVO.SpecVO> specs = JSONUtil.toList(goods.getSpecs(), OrderVO.SpecVO.class);
                goodsVO.setSpecs(specs);
            }
            
            return goodsVO;
        }).collect(Collectors.toList());
        
        vo.setGoodsList(goodsVOList);
        
        return vo;
    }
}

