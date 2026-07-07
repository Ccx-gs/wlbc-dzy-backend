-- ============================================================
-- 积分 + 优惠券系统 - 增量迁移脚本（安全，不删数据）
-- 使用方法：在 MySQL 中执行此文件即可
-- ============================================================

USE shopping_platform;

-- 1. 修改 user 表：加积分字段
ALTER TABLE `user` ADD COLUMN `points` INT NOT NULL DEFAULT 0 COMMENT '可用积分';

-- 2. 修改 order_main 表：加优惠券和积分字段
ALTER TABLE `order_main`
    ADD COLUMN `coupon_id`       BIGINT        DEFAULT NULL COMMENT '使用的用户优惠券ID',
    ADD COLUMN `discount_amount` DECIMAL(10,2) DEFAULT 0.00 COMMENT '优惠券减免金额',
    ADD COLUMN `points_earned`   INT           DEFAULT 0    COMMENT '本单获得的积分';

-- 3. 创建优惠券模板表
CREATE TABLE IF NOT EXISTS `coupon` (
    `id`                BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`              VARCHAR(100)  NOT NULL                COMMENT '券名称',
    `description`       VARCHAR(500)  DEFAULT NULL            COMMENT '使用说明',
    `scope_type`        TINYINT       NOT NULL DEFAULT 1       COMMENT '适用范围：1=全场通用 2=指定分类 3=指定商品',
    `target_id`         BIGINT        DEFAULT NULL            COMMENT '适用范围ID（分类ID/商品ID）',
    `discount_type`     TINYINT       NOT NULL DEFAULT 1       COMMENT '折扣类型：1=满减券 2=折扣券',
    `discount_value`    DECIMAL(10,2) NOT NULL                COMMENT '满减金额或折扣率（0.85=85折）',
    `max_discount`      DECIMAL(10,2) DEFAULT NULL            COMMENT '折扣券最高减免上限',
    `min_order_amount`  DECIMAL(10,2) NOT NULL DEFAULT 0.00   COMMENT '最低消费门槛',
    `total_quantity`    INT           NOT NULL DEFAULT 0       COMMENT '发行总量',
    `issued_quantity`   INT           NOT NULL DEFAULT 0       COMMENT '已发放数量',
    `points_required`   INT           NOT NULL DEFAULT 0       COMMENT '兑换所需积分（0=不可兑换）',
    `per_user_limit`    INT           NOT NULL DEFAULT 0       COMMENT '每人限兑次数（0=不限）',
    `valid_days`        INT           NOT NULL DEFAULT 30      COMMENT '领取后有效天数',
    `status`            TINYINT       NOT NULL DEFAULT 1       COMMENT '状态：0=下架 1=上架',
    `is_deleted`        TINYINT       NOT NULL DEFAULT 0       COMMENT '逻辑删除',
    `create_time`       DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='优惠券模板';

-- 4. 创建用户优惠券表
CREATE TABLE IF NOT EXISTS `user_coupon` (
    `id`              BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`         BIGINT      NOT NULL                COMMENT '用户ID',
    `coupon_id`       BIGINT      NOT NULL                COMMENT '优惠券模板ID',
    `status`          TINYINT     NOT NULL DEFAULT 0       COMMENT '状态：0=未使用 1=已使用 2=已过期',
    `acquire_time`    DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '获取时间',
    `expire_time`     DATETIME    NOT NULL                COMMENT '过期时间',
    `use_time`        DATETIME    DEFAULT NULL            COMMENT '使用时间',
    `order_no`        VARCHAR(32) DEFAULT NULL            COMMENT '使用的订单号',
    `is_deleted`      TINYINT     NOT NULL DEFAULT 0       COMMENT '逻辑删除',
    `create_time`     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    INDEX `idx_uc_user` (`user_id`),
    INDEX `idx_uc_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户持有的优惠券';

-- 5. 创建积分流水表
CREATE TABLE IF NOT EXISTS `points_record` (
    `id`              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`         BIGINT       NOT NULL                COMMENT '用户ID',
    `points`          INT          NOT NULL                COMMENT '积分变动：正=获得 负=消耗',
    `type`            TINYINT      NOT NULL                COMMENT '类型：1=下单奖励 2=兑换优惠券 3=活动赠送',
    `description`     VARCHAR(255) DEFAULT NULL            COMMENT '变动说明',
    `related_id`      VARCHAR(64)  DEFAULT NULL            COMMENT '关联订单号或兑换记录ID',
    `balance_after`   INT          NOT NULL                COMMENT '变动后积分余额',
    `create_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    INDEX `idx_pr_user` (`user_id`),
    INDEX `idx_pr_type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='积分流水记录';

-- 6. 插入预设优惠券数据
INSERT INTO `coupon` (`name`, `description`, `scope_type`, `discount_type`, `discount_value`, `min_order_amount`, `total_quantity`, `points_required`, `per_user_limit`, `valid_days`, `status`) VALUES
('满50减5',   '全场通用，满50元立减5元',   1, 1, 5.00,  50.00,  1000, 500,  3, 30, 1),
('满100减12', '全场通用，满100元立减12元', 1, 1, 12.00, 100.00, 1000, 1000, 3, 30, 1),
('满150减20', '全场通用，满150元立减20元', 1, 1, 20.00, 150.00, 800,  1600, 2, 30, 1),
('满200减30', '全场通用，满200元立减30元', 1, 1, 30.00, 200.00, 500,  2400, 2, 30, 1),
('满100打9折','全场通用，满100元享9折，最高减30元', 1, 2, 0.90, 100.00, 600, 1200, 2, 30, 1);
