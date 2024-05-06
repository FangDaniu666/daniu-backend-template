create table user
(
    id           bigint auto_increment comment '用户ID'
        primary key,
    userAccount  varchar(50)                                  not null comment '用户账号',
    userPassword varchar(255)                                 not null comment '用户密码',
    userSalt     varchar(255)                                 null comment '用户盐值',
    userEmail    varchar(100)                                 null comment '用户邮箱',
    userName     varchar(50)                                  null comment '用户昵称',
    userAvatar   varchar(255)                                 null comment '用户头像地址',
    userProfile  varchar(255)                                 null comment '用户简介',
    userType     enum ('user', 'admin', 'ban') default 'user' null comment '用户类型：user/admin/ban',
    createTime   datetime                                     not null comment '创建时间',
    updateTime   datetime                                     null comment '更新时间',
    isDelete     tinyint(1)                    default 0      not null comment '是否删除：0-未删除，1-已删除'
)
    comment '用户信息表';

