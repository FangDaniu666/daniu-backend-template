-- 用户信息表
CREATE TABLE `user`
(
    `id`            bigint UNSIGNED               NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `user_account`  varchar(50)                   NOT NULL COMMENT '用户账号',
    `user_password` varchar(255)                  NOT NULL COMMENT '用户密码哈希值',
    `user_salt`     varchar(255) COMMENT '用户密码盐值',
    `user_email`    varchar(100) COMMENT '用户邮箱',
    `user_name`     varchar(50) COMMENT '用户昵称',
    `user_avatar`   varchar(255) COMMENT '用户头像地址',
    `user_profile`  varchar(255) COMMENT '用户简介',
    `user_type`     ENUM ('user', 'admin', 'ban') NOT NULL DEFAULT 'user' COMMENT '用户类型：user/admin/ban',
    `create_time`   DATETIME                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`     TINYINT(1)                    NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_user_account` (`user_account`),
    UNIQUE KEY `unique_user_email` (`user_email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户信息表';

-- 登录日志表
CREATE TABLE `login_log`
(
    `id`          INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_name`   varchar(255)     NOT NULL DEFAULT 'unknown' COMMENT '用户名',
    `login_time`  DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
    `login_ip`    varchar(255)     NOT NULL DEFAULT 'unknown' COMMENT '登录IP',
    `login_addr`  varchar(255)     NOT NULL DEFAULT 'unknown' COMMENT '登录地址',
    `driver_name` varchar(255)     NOT NULL DEFAULT 'unknown' COMMENT '驱动名称',
    `os_name`     varchar(255)     NOT NULL DEFAULT 'unknown' COMMENT '操作系统',
    `login_state` TINYINT(1)       NOT NULL DEFAULT 0 COMMENT '登录状态: 0-失败，1-成功',
    PRIMARY KEY (`id`),
    INDEX `idx_user_name` (`user_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='登录日志表';

-- 操作日志表
CREATE TABLE `operation_log`
(
    `id`               INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_name`        varchar(255)     NOT NULL DEFAULT 'unknown' COMMENT '用户名',
    `operation`        varchar(255)     NOT NULL DEFAULT 'unknown' COMMENT '操作类型',
    `operation_module` varchar(255)     NOT NULL DEFAULT 'unknown' COMMENT '操作模块',
    `operation_time`   DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    `operation_ip`     varchar(255)     NOT NULL DEFAULT 'unknown' COMMENT '操作IP',
    `operation_addr`   varchar(255)     NOT NULL DEFAULT 'unknown' COMMENT '操作地址',
    `operation_state`  TINYINT(1)       NOT NULL DEFAULT 0 COMMENT '操作状态: 0-失败，1-成功',
    PRIMARY KEY (`id`),
    INDEX `idx_user_name` (`user_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='操作日志表';
