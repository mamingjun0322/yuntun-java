-- ========================================
-- 云吞点餐系统 - 数据库建表SQL (根据实体类生成)
-- 数据库名：yuntun_db
-- 字符集：utf8mb4
-- 创建时间：2024-11-07
-- ========================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS `yuntun_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `yuntun_db`;

-- ========================================
-- 1. 用户表 (user)
-- ========================================
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` VARCHAR(50) DEFAULT NULL COMMENT '用户名',
  `password` VARCHAR(255) DEFAULT NULL COMMENT '密码',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像',
  `points` INT(11) DEFAULT 0 COMMENT '积分',
  `status` INT(11) DEFAULT 1 COMMENT '状态(1-正常 0-禁用)',
  `level` INT(11) DEFAULT 0 COMMENT '会员等级',
  `gender` INT(11) DEFAULT 0 COMMENT '性别(0-未知 1-男 2-女)',
  `birthday` VARCHAR(20) DEFAULT NULL COMMENT '生日',
  `openid` VARCHAR(64) DEFAULT NULL COMMENT '微信openid',
  `unionid` VARCHAR(64) DEFAULT NULL COMMENT '微信unionid',
  `deleted` INT(11) DEFAULT 0 COMMENT '逻辑删除(0-未删除 1-已删除)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_phone` (`phone`),
  KEY `idx_openid` (`openid`),
  KEY `idx_unionid` (`unionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 插入测试用户 (密码: admin123)
INSERT INTO `user` (`username`, `password`, `phone`, `nickname`, `avatar`, `points`, `status`, `level`) 
VALUES 
('test_user', '$2a$10$N.ZF9FvSTW3zvVz6PH8dau8gF/E7bBRwqK/Y8y8O5rYn0L/Z0ZWQG', '13800138000', '测试用户', 'https://example.com/avatar.jpg', 100, 1, 1);

-- ========================================
-- 2. 管理员表 (admin)
-- ========================================
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(255) NOT NULL COMMENT '密码',
  `name` VARCHAR(50) DEFAULT NULL COMMENT '姓名',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像',
  `role` INT(11) DEFAULT 2 COMMENT '角色(1-超级管理员 2-普通管理员)',
  `status` INT(11) DEFAULT 1 COMMENT '状态(1-启用 0-禁用)',
  `deleted` INT(11) DEFAULT 0 COMMENT '逻辑删除(0-未删除 1-已删除)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  KEY `idx_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员表';

-- 插入默认管理员 (用户名: admin, 密码: admin123)
INSERT INTO `admin` (`username`, `password`, `name`, `role`, `status`) 
VALUES 
('admin', '$2a$10$N.ZF9FvSTW3zvVz6PH8dau8gF/E7bBRwqK/Y8y8O5rYn0L/Z0ZWQG', '超级管理员', 1, 1);

-- ========================================
-- 3. 店铺表 (shop)
-- ========================================
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` VARCHAR(100) NOT NULL COMMENT '店铺名称',
  `image` VARCHAR(255) DEFAULT NULL COMMENT '店铺图片',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
  `address` VARCHAR(255) DEFAULT NULL COMMENT '店铺地址',
  `latitude` DECIMAL(10, 6) DEFAULT NULL COMMENT '纬度',
  `longitude` DECIMAL(10, 6) DEFAULT NULL COMMENT '经度',
  `business_hours` VARCHAR(100) DEFAULT NULL COMMENT '营业时间',
  `delivery_range` DECIMAL(10, 2) DEFAULT 5.00 COMMENT '配送范围(km)',
  `min_delivery_amount` DECIMAL(10, 2) DEFAULT 20.00 COMMENT '最低配送金额',
  `delivery_fee` DECIMAL(10, 2) DEFAULT 5.00 COMMENT '配送费',
  `packing_fee` DECIMAL(10, 2) DEFAULT 2.00 COMMENT '打包费',
  `intro` TEXT COMMENT '店铺介绍',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='店铺表';

-- 插入默认店铺
INSERT INTO `shop` (`name`, `image`, `phone`, `address`, `latitude`, `longitude`, `business_hours`, `delivery_range`, `min_delivery_amount`, `delivery_fee`, `packing_fee`, `intro`) 
VALUES 
('云吞点餐', 'https://example.com/shop.jpg', '400-888-8888', '广东省深圳市南山区科技园', 22.537500, 113.934500, '09:00-22:00', 5.00, 20.00, 5.00, 2.00, '欢迎光临云吞点餐！');

-- ========================================
-- 4. 分类表 (category)
-- ========================================
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` VARCHAR(50) NOT NULL COMMENT '分类名称',
  `sort` INT(11) DEFAULT 0 COMMENT '排序',
  `status` INT(11) DEFAULT 1 COMMENT '状态(1-启用 0-禁用)',
  `deleted` INT(11) DEFAULT 0 COMMENT '逻辑删除(0-未删除 1-已删除)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_sort` (`sort`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分类表';

-- 插入默认分类
INSERT INTO `category` (`name`, `sort`, `status`) VALUES
('云吞系列', 1, 1),
('面食系列', 2, 1),
('小吃系列', 3, 1),
('饮品系列', 4, 1);

-- ========================================
-- 5. 商品表 (goods)
-- ========================================
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `category_id` BIGINT(20) NOT NULL COMMENT '分类ID',
  `name` VARCHAR(100) NOT NULL COMMENT '商品名称',
  `images` TEXT COMMENT '商品图片(多张用逗号分隔)',
  `price` DECIMAL(10, 2) NOT NULL COMMENT '商品价格',
  `original_price` DECIMAL(10, 2) DEFAULT NULL COMMENT '原价',
  `description` TEXT COMMENT '商品描述',
  `detail` TEXT COMMENT '商品详情',
  `sales` INT(11) DEFAULT 0 COMMENT '销量',
  `stock` INT(11) DEFAULT 999 COMMENT '库存',
  `tag` VARCHAR(50) DEFAULT NULL COMMENT '标签',
  `sort` INT(11) DEFAULT 0 COMMENT '排序',
  `status` INT(11) DEFAULT 1 COMMENT '状态(1-上架 0-下架)',
  `has_specs` TINYINT(1) DEFAULT 0 COMMENT '是否有规格(1-是 0-否)',
  `deleted` INT(11) DEFAULT 0 COMMENT '逻辑删除(0-未删除 1-已删除)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_status` (`status`),
  KEY `idx_sort` (`sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品表';

-- 插入示例商品
INSERT INTO `goods` (`category_id`, `name`, `images`, `price`, `original_price`, `description`, `detail`, `stock`, `sales`, `status`, `has_specs`, `tag`) VALUES
(1, '鲜虾云吞', 'https://example.com/goods1.jpg', 18.00, 20.00, '新鲜虾仁制作，皮薄馅大', '新鲜虾仁制作，皮薄馅大，口感鲜美', 999, 0, 1, 1, '招牌'),
(1, '鲜肉云吞', 'https://example.com/goods2.jpg', 15.00, 18.00, '精选猪肉，口感鲜美', '精选猪肉，口感鲜美，回味无穷', 999, 0, 1, 1, '热销'),
(2, '云吞面', 'https://example.com/goods3.jpg', 22.00, 25.00, '云吞+鲜虾面，经典搭配', '云吞+鲜虾面，经典搭配，美味可口', 999, 0, 1, 0, '推荐'),
(3, '炸云吞', 'https://example.com/goods4.jpg', 12.00, 15.00, '酥脆可口，外焦里嫩', '酥脆可口，外焦里嫩，香气扑鼻', 999, 0, 1, 0, NULL);

-- ========================================
-- 6. 商品规格表 (goods_specs)
-- ========================================
DROP TABLE IF EXISTS `goods_specs`;
CREATE TABLE `goods_specs` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `goods_id` BIGINT(20) NOT NULL COMMENT '商品ID',
  `name` VARCHAR(50) NOT NULL COMMENT '规格名称',
  `options` TEXT COMMENT '规格选项(JSON格式)',
  `deleted` INT(11) DEFAULT 0 COMMENT '逻辑删除(0-未删除 1-已删除)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_goods_id` (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品规格表';

-- 插入示例规格
INSERT INTO `goods_specs` (`goods_id`, `name`, `options`) VALUES
(1, '份量', '[{"name":"小份(8个)","price":18.00},{"name":"大份(12个)","price":26.00}]'),
(2, '份量', '[{"name":"小份(8个)","price":15.00},{"name":"大份(12个)","price":22.00}]');

-- ========================================
-- 7. 地址表 (address)
-- ========================================
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `name` VARCHAR(50) NOT NULL COMMENT '联系人姓名',
  `phone` VARCHAR(20) NOT NULL COMMENT '联系电话',
  `region` VARCHAR(200) DEFAULT NULL COMMENT '省市区',
  `address` VARCHAR(255) NOT NULL COMMENT '详细地址',
  `latitude` DECIMAL(10, 6) DEFAULT NULL COMMENT '纬度',
  `longitude` DECIMAL(10, 6) DEFAULT NULL COMMENT '经度',
  `is_default` TINYINT(1) DEFAULT 0 COMMENT '是否默认地址(1-是 0-否)',
  `deleted` INT(11) DEFAULT 0 COMMENT '逻辑删除(0-未删除 1-已删除)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_is_default` (`is_default`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='地址表';

-- ========================================
-- 8. 订单表 (orders)
-- 注意：表名是orders，因为order是MySQL关键字
-- ========================================
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_no` VARCHAR(32) NOT NULL COMMENT '订单号',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `type` INT(11) DEFAULT 2 COMMENT '订单类型(1-堂食 2-外卖)',
  `status` INT(11) DEFAULT 1 COMMENT '订单状态(1-待支付 2-制作中 3-配送中 4-已完成 5-已取消)',
  `table_no` VARCHAR(20) DEFAULT NULL COMMENT '桌号(堂食)',
  `people_count` INT(11) DEFAULT NULL COMMENT '就餐人数(堂食)',
  `address_id` BIGINT(20) DEFAULT NULL COMMENT '地址ID(外卖)',
  `receiver_name` VARCHAR(50) DEFAULT NULL COMMENT '收货人姓名(外卖)',
  `receiver_phone` VARCHAR(20) DEFAULT NULL COMMENT '收货人电话(外卖)',
  `address` VARCHAR(500) DEFAULT NULL COMMENT '收货地址(外卖)',
  `delivery_time` VARCHAR(50) DEFAULT NULL COMMENT '配送时间(外卖)',
  `tableware` INT(11) DEFAULT 1 COMMENT '餐具份数(外卖)',
  `goods_amount` DECIMAL(10, 2) NOT NULL COMMENT '商品金额',
  `delivery_fee` DECIMAL(10, 2) DEFAULT 0.00 COMMENT '配送费',
  `packing_fee` DECIMAL(10, 2) DEFAULT 0.00 COMMENT '打包费',
  `coupon_discount` DECIMAL(10, 2) DEFAULT 0.00 COMMENT '优惠券抵扣',
  `total_amount` DECIMAL(10, 2) NOT NULL COMMENT '订单总金额',
  `coupon_id` BIGINT(20) DEFAULT NULL COMMENT '优惠券ID',
  `payment_url` VARCHAR(500) DEFAULT NULL COMMENT '支付链接',
  `payment_link_id` BIGINT(20) DEFAULT NULL COMMENT '支付链接ID',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '订单备注',
  `pay_type` INT(11) DEFAULT NULL COMMENT '支付方式(1-微信 2-支付宝 3-余额)',
  `pay_time` DATETIME DEFAULT NULL COMMENT '支付时间',
  `finish_time` DATETIME DEFAULT NULL COMMENT '完成时间',
  `cancel_reason` VARCHAR(200) DEFAULT NULL COMMENT '取消原因',
  `commented` TINYINT(1) DEFAULT 0 COMMENT '是否已评价(1-是 0-否)',
  `deleted` INT(11) DEFAULT 0 COMMENT '逻辑删除(0-未删除 1-已删除)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- ========================================
-- 9. 订单商品表 (order_goods)
-- ========================================
DROP TABLE IF EXISTS `order_goods`;
CREATE TABLE `order_goods` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_id` BIGINT(20) NOT NULL COMMENT '订单ID',
  `goods_id` BIGINT(20) NOT NULL COMMENT '商品ID',
  `name` VARCHAR(100) NOT NULL COMMENT '商品名称',
  `image` VARCHAR(255) DEFAULT NULL COMMENT '商品图片',
  `price` DECIMAL(10, 2) NOT NULL COMMENT '商品价格',
  `quantity` INT(11) NOT NULL COMMENT '购买数量',
  `specs` TEXT COMMENT '商品规格(JSON格式)',
  `remark` VARCHAR(200) DEFAULT NULL COMMENT '备注',
  `deleted` INT(11) DEFAULT 0 COMMENT '逻辑删除(0-未删除 1-已删除)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_goods_id` (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单商品表';

-- ========================================
-- 10. 订单评价表 (order_comment)
-- ========================================
DROP TABLE IF EXISTS `order_comment`;
CREATE TABLE `order_comment` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_id` BIGINT(20) NOT NULL COMMENT '订单ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `goods_score` INT(11) DEFAULT 5 COMMENT '商品评分(1-5分)',
  `service_score` INT(11) DEFAULT 5 COMMENT '服务评分(1-5分)',
  `content` TEXT COMMENT '评价内容',
  `images` TEXT COMMENT '评价图片(多张用逗号分隔)',
  `anonymous` TINYINT(1) DEFAULT 0 COMMENT '是否匿名(1-是 0-否)',
  `deleted` INT(11) DEFAULT 0 COMMENT '逻辑删除(0-未删除 1-已删除)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单评价表';

-- ========================================
-- 11. 优惠券表 (coupon)
-- ========================================
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` VARCHAR(100) NOT NULL COMMENT '优惠券名称',
  `discount` DECIMAL(10, 2) NOT NULL COMMENT '优惠金额',
  `min_amount` DECIMAL(10, 2) DEFAULT 0.00 COMMENT '最低消费金额',
  `expire_time` DATETIME NOT NULL COMMENT '过期时间',
  `stock` INT(11) DEFAULT 999 COMMENT '库存',
  `status` INT(11) DEFAULT 1 COMMENT '状态(1-上架 0-下架)',
  `deleted` INT(11) DEFAULT 0 COMMENT '逻辑删除(0-未删除 1-已删除)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_status` (`status`),
  KEY `idx_expire_time` (`expire_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='优惠券表';

-- 插入示例优惠券
INSERT INTO `coupon` (`name`, `discount`, `min_amount`, `expire_time`, `stock`, `status`) VALUES
('新人专享券', 5.00, 20.00, DATE_ADD(NOW(), INTERVAL 30 DAY), 1000, 1),
('满50减10', 10.00, 50.00, DATE_ADD(NOW(), INTERVAL 60 DAY), 500, 1),
('满100减20', 20.00, 100.00, DATE_ADD(NOW(), INTERVAL 60 DAY), 300, 1);

-- ========================================
-- 12. 用户优惠券表 (user_coupon)
-- ========================================
DROP TABLE IF EXISTS `user_coupon`;
CREATE TABLE `user_coupon` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `coupon_id` BIGINT(20) NOT NULL COMMENT '优惠券ID',
  `status` INT(11) DEFAULT 0 COMMENT '状态(0-未使用 1-已使用 2-已过期)',
  `use_time` DATETIME DEFAULT NULL COMMENT '使用时间',
  `deleted` INT(11) DEFAULT 0 COMMENT '逻辑删除(0-未删除 1-已删除)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_coupon_id` (`coupon_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户优惠券表';

-- ========================================
-- 13. 积分明细表 (points_history)
-- ========================================
DROP TABLE IF EXISTS `points_history`;
CREATE TABLE `points_history` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `title` VARCHAR(100) DEFAULT NULL COMMENT '标题',
  `points` INT(11) NOT NULL COMMENT '积分变动值(正数为增加，负数为减少)',
  `type` INT(11) NOT NULL COMMENT '类型(1-签到 2-消费 3-退款)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_type` (`type`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='积分明细表';

-- ========================================
-- 14. 轮播图表 (banner)
-- ========================================
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `image` VARCHAR(255) NOT NULL COMMENT '图片URL',
  `url` VARCHAR(255) DEFAULT NULL COMMENT '跳转链接',
  `sort` INT(11) DEFAULT 0 COMMENT '排序',
  `status` INT(11) DEFAULT 1 COMMENT '状态(1-启用 0-禁用)',
  `deleted` INT(11) DEFAULT 0 COMMENT '逻辑删除(0-未删除 1-已删除)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_sort` (`sort`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='轮播图表';

-- 插入示例轮播图
INSERT INTO `banner` (`image`, `url`, `sort`, `status`) VALUES
('https://example.com/banner1.jpg', NULL, 1, 1),
('https://example.com/banner2.jpg', NULL, 2, 1),
('https://example.com/banner3.jpg', NULL, 3, 1);

-- ========================================
-- 15. 支付链接配置表 (payment_link)
-- ========================================
DROP TABLE IF EXISTS `payment_link`;
CREATE TABLE `payment_link` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `amount` DECIMAL(10, 2) NOT NULL COMMENT '支付金额（元）',
  `payment_url` VARCHAR(500) NOT NULL COMMENT '支付链接URL',
  `payment_type` VARCHAR(20) DEFAULT NULL COMMENT '支付类型(wechat-微信 alipay-支付宝)',
  `qr_code_url` VARCHAR(500) DEFAULT NULL COMMENT '二维码图片URL（可选）',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '描述说明',
  `status` INT(11) DEFAULT 1 COMMENT '状态(1-启用 0-禁用)',
  `sort` INT(11) DEFAULT 0 COMMENT '排序',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_status` (`status`),
  KEY `idx_sort` (`sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='支付链接配置表';

-- ========================================
-- 16. 系统配置表 (system_config)
-- ========================================
DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `config_key` VARCHAR(100) NOT NULL COMMENT '配置键',
  `config_value` TEXT COMMENT '配置值',
  `config_desc` VARCHAR(255) DEFAULT NULL COMMENT '配置描述',
  `config_type` VARCHAR(20) DEFAULT 'string' COMMENT '配置类型(string/number/boolean)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_config_key` (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表';

-- ========================================
-- 17. 公告表 (notice)
-- ========================================
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `content` TEXT NOT NULL COMMENT '公告内容',
  `status` INT(11) DEFAULT 1 COMMENT '状态(1-启用 0-禁用)',
  `deleted` INT(11) DEFAULT 0 COMMENT '逻辑删除(0-未删除 1-已删除)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='公告表';

-- 插入示例公告
INSERT INTO `notice` (`content`, `status`) VALUES
('欢迎使用云吞点餐系统，祝您用餐愉快！', 1),
('新用户注册即送5元优惠券，快来领取吧！', 1);

-- ========================================
-- 创建完成提示
-- ========================================
SELECT '========================================' AS '';
SELECT '数据库表创建完成！' AS '提示';
SELECT '数据库名：yuntun_db' AS '';
SELECT '共创建17张表，100%匹配实体类定义：' AS '';
SELECT '1. user - 用户表' AS '';
SELECT '2. admin - 管理员表' AS '';
SELECT '3. shop - 店铺表' AS '';
SELECT '4. category - 分类表' AS '';
SELECT '5. goods - 商品表' AS '';
SELECT '6. goods_specs - 商品规格表' AS '';
SELECT '7. address - 地址表' AS '';
SELECT '8. orders - 订单表 (注意不是order)' AS '';
SELECT '9. order_goods - 订单商品表' AS '';
SELECT '10. order_comment - 订单评价表' AS '';
SELECT '11. coupon - 优惠券表' AS '';
SELECT '12. user_coupon - 用户优惠券表' AS '';
SELECT '13. points_history - 积分明细表' AS '';
SELECT '14. banner - 轮播图表' AS '';
SELECT '15. payment_link - 支付链接配置表' AS '';
SELECT '16. system_config - 系统配置表' AS '';
SELECT '17. notice - 公告表' AS '';
SELECT '========================================' AS '';
SELECT '默认管理员：admin / admin123' AS '';
SELECT '测试用户：test_user / admin123' AS '';
SELECT '========================================' AS '';

