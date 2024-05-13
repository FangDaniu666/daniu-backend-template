create table user
(
    id            bigint auto_increment comment '用户ID'
        primary key,
    user_account  varchar(50)                                  not null comment '用户账号',
    user_password varchar(255)                                 not null comment '用户密码',
    user_salt     varchar(255)                                 null comment '用户盐值',
    user_email    varchar(100)                                 null comment '用户邮箱',
    user_name     varchar(50)                                  null comment '用户昵称',
    user_avatar   varchar(255)                                 null comment '用户头像地址',
    user_profile  varchar(255)                                 null comment '用户简介',
    user_type     enum ('user', 'admin', 'ban') default 'user' null comment '用户类型：user/admin/ban',
    create_time   datetime                                     not null comment '创建时间',
    update_time   datetime                                     null comment '更新时间',
    is_delete     tinyint(1)                    default 0      not null comment '是否删除：0-未删除，1-已删除'
)
    comment '用户信息表';


create table login_log
(
    id          int auto_increment comment '主键'
        primary key,
    user_name   varchar(255) null comment '用户名',
    login_time  datetime     null comment '登录时间',
    login_ip    varchar(255) null comment '登录IP',
    login_addr  varchar(255) null comment '登录地址',
    driver_name varchar(255) null comment '驱动名称',
    os_name     varchar(255) null comment '操作系统',
    login_state int          null comment '登录状态: 0-失败，1-成功'
);

