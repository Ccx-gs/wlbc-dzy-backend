-- ============================================================
-- B2C Shopping Platform - Core Table DDL
-- Engine: InnoDB | Charset: utf8mb4
-- ============================================================

CREATE DATABASE IF NOT EXISTS shopping_platform
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE shopping_platform;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '??ID',
    `username`      VARCHAR(50)  NOT NULL                COMMENT '???',
    `password`      VARCHAR(128) NOT NULL                COMMENT '???BCrypt?',
    `nickname`      VARCHAR(50)  DEFAULT NULL            COMMENT '??',
    `email`         VARCHAR(100) DEFAULT NULL            COMMENT '??',
    `phone`         VARCHAR(20)  DEFAULT NULL            COMMENT '???',
    `avatar`        VARCHAR(255) DEFAULT NULL            COMMENT '??URL',
    `gender`        TINYINT      DEFAULT 0              COMMENT '???0-?? 1-? 2-?',
    `status`        TINYINT      NOT NULL DEFAULT 1      COMMENT '???0-?? 1-??',
    `is_deleted`    TINYINT      NOT NULL DEFAULT 0      COMMENT '????',
    `create_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '????',
    `update_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '????',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='???';

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
    `id`            BIGINT      NOT NULL AUTO_INCREMENT COMMENT '??ID',
    `parent_id`     BIGINT      NOT NULL DEFAULT 0       COMMENT '???ID?0????',
    `name`          VARCHAR(50) NOT NULL                COMMENT '????',
    `sort_order`    INT         NOT NULL DEFAULT 0       COMMENT '???',
    `status`        TINYINT     NOT NULL DEFAULT 1       COMMENT '???0-?? 1-??',
    `is_deleted`    TINYINT     NOT NULL DEFAULT 0       COMMENT '????',
    `create_time`   DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '????',
    `update_time`   DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '????',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='?????';

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
    `id`            BIGINT        NOT NULL AUTO_INCREMENT COMMENT '??ID',
    `category_id`   BIGINT        NOT NULL                COMMENT '????ID',
    `name`          VARCHAR(100)  NOT NULL                COMMENT '????',
    `subtitle`      VARCHAR(200)  DEFAULT NULL            COMMENT '???',
    `main_image`    VARCHAR(255)  DEFAULT NULL            COMMENT '??URL',
    `detail`        TEXT          DEFAULT NULL            COMMENT '????',
    `price`         DECIMAL(10,2) NOT NULL                COMMENT '??',
    `stock`         INT           NOT NULL DEFAULT 0       COMMENT '????',
    `sales`         INT           NOT NULL DEFAULT 0       COMMENT '??',
    `version`       INT           NOT NULL DEFAULT 0       COMMENT '??????',
    `status`        TINYINT       NOT NULL DEFAULT 1       COMMENT '???0-?? 1-??',
    `is_deleted`    TINYINT       NOT NULL DEFAULT 0       COMMENT '????',
    `create_time`   DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '????',
    `update_time`   DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '????',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='???';

DROP TABLE IF EXISTS `order_main`;
CREATE TABLE `order_main` (
    `id`               BIGINT        NOT NULL AUTO_INCREMENT COMMENT '??ID',
    `order_no`         VARCHAR(32)   NOT NULL                COMMENT '???',
    `user_id`          BIGINT        NOT NULL                COMMENT '??ID',
    `total_amount`     DECIMAL(10,2) NOT NULL                COMMENT '?????',
    `pay_amount`       DECIMAL(10,2) NOT NULL                COMMENT '????',
    `status`           TINYINT       NOT NULL DEFAULT 1       COMMENT '?????0-??? 1-??? 2-??? 3-??? 4-???',
    `receiver_name`    VARCHAR(50)   DEFAULT NULL            COMMENT '?????',
    `receiver_phone`   VARCHAR(20)   DEFAULT NULL            COMMENT '??????',
    `receiver_address` VARCHAR(255)  DEFAULT NULL            COMMENT '????',
    `remark`           VARCHAR(500)  DEFAULT NULL            COMMENT '????',
    `pay_time`         DATETIME      DEFAULT NULL            COMMENT '????',
    `is_deleted`       TINYINT       NOT NULL DEFAULT 0       COMMENT '????',
    `create_time`      DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '????',
    `update_time`      DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '????',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='????';

DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item` (
    `id`            BIGINT        NOT NULL AUTO_INCREMENT COMMENT '??ID',
    `order_id`      BIGINT        NOT NULL                COMMENT '??ID',
    `product_id`    BIGINT        NOT NULL                COMMENT '??ID',
    `product_name`  VARCHAR(100)  NOT NULL                COMMENT '??????',
    `product_image` VARCHAR(255)  DEFAULT NULL            COMMENT '??????',
    `unit_price`    DECIMAL(10,2) NOT NULL                COMMENT '?????',
    `quantity`      INT           NOT NULL                COMMENT '????',
    `total_price`   DECIMAL(10,2) NOT NULL                COMMENT '????',
    `is_deleted`    TINYINT       NOT NULL DEFAULT 0       COMMENT '????',
    `create_time`   DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '????',
    `update_time`   DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '????',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='?????';

CREATE INDEX idx_user_phone         ON `user` (`phone`);
CREATE INDEX idx_category_parent    ON `category` (`parent_id`);
CREATE INDEX idx_product_category   ON `product` (`category_id`);
CREATE INDEX idx_product_name       ON `product` (`name`);
CREATE INDEX idx_order_main_user    ON `order_main` (`user_id`);
CREATE INDEX idx_order_main_status  ON `order_main` (`status`);
CREATE INDEX idx_order_item_order   ON `order_item` (`order_id`);
