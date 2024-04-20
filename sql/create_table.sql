# 数据库初始化

-- 创建库
create database if not exists my_bi_db;
set global innodb_large_prefix=on;
set global innodb_file_per_table=on;
set global innodb_file_format=BARRACUDA;
set global innodb_file_format_max=BARRACUDA;
-- 切换库
use my_bi_db;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_userAccount (userAccount)
) comment '用户' collate = utf8mb4_unicode_ci row_format=DYNAMIC;

-- 图表信息表
create table if not exists chart
(
    id           bigint auto_increment comment 'id' primary key,
    name         varchar(256)                           not null comment '图表名称',
    goal         text                                   null comment '分析目标',
    chartData    text                                   null comment '图表数据',
    charType     varchar(128)                           null comment '图表类型',
    userId       bigint                                 not null comment '图表所属用户id',
    genChart     text                                   null comment '生成的图表数据',
    genResult    text                                   null comment '生成的分析结论',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除'
) comment '图表信息' collate = utf8mb4_unicode_ci;

