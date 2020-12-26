create table power_admin
(
    id           bigint auto_increment comment '编号'
        primary key,
    icon         varchar(512) default 'http://image.baokangnet.cn/Fj637_Wy8lBHHE4xQageqJtE3_77' not null comment '头像',
    nickname     varchar(32)  default '管理员'                                                     not null comment '昵称',
    username     varchar(32)                                                                    not null comment '登录账号 唯一',
    password     varchar(512)                                                                   not null comment '登录密码 使用md5加密',
    note         varchar(200) default '管理员账号'                                                   not null comment '备注信息',
    status       int          default 1                                                         not null comment '账号状态 1：正常，0：锁定',
    first_login  timestamp                                                                      null comment '第一次登陆时间',
    login_time   datetime                                                                       null comment '最后登录时间',
    gmt_create   datetime     default CURRENT_TIMESTAMP                                         not null comment '创建时间',
    gmt_modified datetime     default CURRENT_TIMESTAMP                                         not null comment '更新时间',
    constraint power_account_name_uindex
        unique (username)
)
    comment '权限账号';

INSERT INTO little-dev.power_admin (id, icon, nickname, username, password, note, status, first_login, login_time,
                                    gmt_create, gmt_modified)
VALUES (2, 'http://image.baokangnet.cn/Fj637_Wy8lBHHE4xQageqJtE3_77',
           'test',
           'test',
           '$2a$10$ZMI/kuTGLiHPziwOfciJSeFoEQ1Jjo6vJX7EmkTARVpvmMqZ0i5SK',
           'test', 1, null, null,
           '2020-10-14 21:06:28',
           '2020-10-14 21:06:27');
INSERT INTO little-dev.power_admin (id, icon, nickname, username, password, note, status, first_login, login_time,
                                    gmt_create, gmt_modified)
VALUES (3, 'http://image.baokangnet.cn/Fj637_Wy8lBHHE4xQageqJtE3_77',
           'admin',
           'admin',
           '$2a$10$9gyTbK5VR2kGT7G0qy0.KOAIaQVi1BnWiqhIvZyTjtRBf/t6fetqa',
           'test', 1, null, null,
           '2020-10-14 21:12:08',
           '2020-10-14 21:13:14');
INSERT INTO little-dev.power_admin (id, icon, nickname, username, password, note, status, first_login, login_time,
                                    gmt_create, gmt_modified)
VALUES (5, 'http://image.baokangnet.cn/Fj637_Wy8lBHHE4xQageqJtE3_77',
           'little',
           'little',
           '$2a$10$d5BTsJDSjCi4nfFNzgiHH.UQl4QzrtIRzV.6poqj/B3RtvVMeYWGm',
           '管理员账号', 1, null, null,
           '2020-10-19 11:15:42',
           '2020-10-19 11:15:42');

create table power_menu
(
    id           bigint auto_increment comment '菜单编号'
        primary key,
    parent_id    bigint                             null comment '父级菜单',
    title        varchar(32)                        not null comment '菜单名称',
    level        int                                not null comment '菜单级数',
    sort         int                                not null comment '菜单排序',
    name         varchar(32)                        not null comment '前端路径',
    icon         varchar(32)                        not null comment '前端图标',
    hidden       int      default 1                 not null comment '前端隐藏 0：隐藏，1：显示',
    gmt_create   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint power_menu_name_uindex
        unique (name),
    constraint power_menu_title_uindex
        unique (title)
)
    comment '权限菜单 ';

INSERT INTO little-dev.power_menu (id, parent_id, title, level, sort, name, icon, hidden, gmt_create, gmt_modified)
VALUES (1, 0, '权限', 0, 0,
              'power',
              'power', 0,
              '2020-02-07 16:29:13',
              '2020-10-19 21:12:40');
INSERT INTO little-dev.power_menu (id, parent_id, title, level, sort, name, icon, hidden, gmt_create, gmt_modified)
VALUES (2, 1, '用户列表', 1, 0,
              'admin',
              'power-admin', 0,
              '2020-02-07 16:29:13',
              '2020-02-07 16:29:13');
INSERT INTO little-dev.power_menu (id, parent_id, title, level, sort, name, icon, hidden, gmt_create, gmt_modified)
VALUES (3, 1, '角色列表', 1, 0,
              'role',
              'power-role', 0,
              '2020-02-07 16:29:13',
              '2020-10-19 13:02:59');
INSERT INTO little-dev.power_menu (id, parent_id, title, level, sort, name, icon, hidden, gmt_create, gmt_modified)
VALUES (4, 1, '菜单列表', 1, 0,
              'menu',
              'power-menu', 0,
              '2020-02-07 16:29:13',
              '2020-02-07 16:29:13');
INSERT INTO little-dev.power_menu (id, parent_id, title, level, sort, name, icon, hidden, gmt_create, gmt_modified)
VALUES (5, 1, '资源列表', 1, 0,
              'resource',
              'power-resource', 0,
              '2020-02-07 16:29:13',
              '2020-02-07 16:29:13');
INSERT INTO little-dev.power_menu (id, parent_id, title, level, sort, name, icon, hidden, gmt_create, gmt_modified)
VALUES (8, 0, '系统', 0, 0,
              'system',
              'system', 0,
              '2020-10-19 21:57:44',
              '2020-10-19 21:57:44');
INSERT INTO little-dev.power_menu (id, parent_id, title, level, sort, name, icon, hidden, gmt_create, gmt_modified)
VALUES (9, 8, '数据字典', 1, 0,
              'dictionary',
              'dictionary', 0,
              '2020-10-19 21:58:15',
              '2020-10-19 21:58:15');
INSERT INTO little-dev.power_menu (id, parent_id, title, level, sort, name, icon, hidden, gmt_create, gmt_modified)
VALUES (13, 0, '用户', 0, 5,
               'user',
               'user', 0,
               '2020-10-21 23:21:02',
               '2020-10-24 13:55:18');

create table power_resource_category
(
    id           bigint auto_increment comment '分类编号'
        primary key,
    name         varchar(32)                        not null comment '分类名称',
    sort         int      default 1                 not null comment '分类排序 数值越小，越靠前',
    gmt_create   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint power_resource_category_name_uindex
        unique (name)
)
    comment '权限资源分类 ';

INSERT INTO little-dev.power_resource_category (id, name, sort, gmt_create, gmt_modified)
VALUES (1, '权限模块', 0,
           '2020-10-18 22:56:18',
           '2020-10-18 22:56:18');
INSERT INTO little-dev.power_resource_category (id, name, sort, gmt_create, gmt_modified)
VALUES (2, '系统模块', 0,
           '2020-10-19 13:03:56',
           '2020-10-19 13:03:56');

create table power_resource
(
    id           bigint auto_increment comment '资源编号'
        primary key,
    category_id  bigint                             not null comment '资源分类编号',
    name         varchar(32)                        not null comment '资源名称',
    url          varchar(32)                        not null comment '资源URL',
    description  varchar(64)                        not null comment '资源描述',
    gmt_create   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint power_resource_name_uindex
        unique (name),
    constraint power_resource_url_uindex
        unique (url)
)
    comment '权限资源 ';

create index power_resource_power_resource_category_id_fk
    on power_resource (category_id);

INSERT INTO little-dev.power_resource (id, category_id, name, url, description, gmt_create, gmt_modified)
VALUES (1, 1, '后台用户管理',
              '/power/admin/**',
              '',
              '2020-02-07 16:47:34',
              '2020-02-07 16:47:34');
INSERT INTO little-dev.power_resource (id, category_id, name, url, description, gmt_create, gmt_modified)
VALUES (2, 1, '后台用户角色管理',
              '/power/role/**',
              '',
              '2020-02-07 16:47:34',
              '2020-02-07 16:47:34');
INSERT INTO little-dev.power_resource (id, category_id, name, url, description, gmt_create, gmt_modified)
VALUES (3, 1, '后台菜单管理',
              '/power/menu/**',
              '',
              '2020-02-07 16:47:34',
              '2020-02-07 16:47:34');
INSERT INTO little-dev.power_resource (id, category_id, name, url, description, gmt_create, gmt_modified)
VALUES (4, 1, '后台资源分类管理',
              '/power/resource/category/**',
              '',
              '2020-02-07 16:47:34',
              '2020-02-07 16:47:34');
INSERT INTO little-dev.power_resource (id, category_id, name, url, description, gmt_create, gmt_modified)
VALUES (5, 1, '后台资源管理',
              '/power/resource/**',
              '',
              '2020-02-07 16:47:34',
              '2020-02-07 16:47:34');
INSERT INTO little-dev.power_resource (id, category_id, name, url, description, gmt_create, gmt_modified)
VALUES (6, 2, '系统API日志',
              '/power/api/log/**',
              '',
              '2020-10-19 13:51:57',
              '2020-10-19 13:51:57');

create table power_role
(
    id           bigint auto_increment comment '角色编号'
        primary key,
    name         varchar(32)                        not null comment '英文名称',
    chinese_name varchar(32)                        not null comment '中文名称',
    admin_count  int      default 0                 not null comment '用户数目',
    sort         int      default 0                 not null comment '排序 越小越靠前',
    status       int      default 1                 not null comment '角色状态 1：启用，0：禁用',
    gmt_create   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint power_role_name_uindex
        unique (name)
)
    comment '权限角色';

INSERT INTO little-dev.power_role (id, name, chinese_name, admin_count, sort, status, gmt_create, gmt_modified)
VALUES (1, 'admin',
           'admin', 0, 1, 1,
           '2020-10-18 22:39:10',
           '2020-10-18 22:39:10');
INSERT INTO little-dev.power_role (id, name, chinese_name, admin_count, sort, status, gmt_create, gmt_modified)
VALUES (2, 'super-admin',
           '超级管理员', 0, 1, 1,
           '2020-10-18 22:55:23',
           '2020-10-18 22:55:23');
INSERT INTO little-dev.power_role (id, name, chinese_name, admin_count, sort, status, gmt_create, gmt_modified)
VALUES (3, 'power-admin',
           '权限管理员', 0, 1, 1,
           '2020-10-18 22:55:36',
           '2020-10-18 22:55:36');

create table power_admin_role_relation
(
    id           bigint auto_increment comment '账号角色关系编号'
        primary key,
    admin_id     bigint                             not null comment '管理员编号',
    role_id      bigint                             not null comment '角色编号',
    gmt_create   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint power_admin_role_relation_power_admin_id_fk
        foreign key (admin_id) references power_admin (id)
            on update cascade on delete cascade,
    constraint power_admin_role_relation_power_role_id_fk
        foreign key (role_id) references power_role (id)
            on update cascade on delete cascade
)
    comment '权限账号关联角色';

INSERT INTO little-dev.power_admin_role_relation (id, admin_id, role_id, gmt_create, gmt_modified)
VALUES (1, 2, 1, '2020-10-18 22:40:25',
                 '2020-10-18 22:40:25');
INSERT INTO little-dev.power_admin_role_relation (id, admin_id, role_id, gmt_create, gmt_modified)
VALUES (3, 3, 2, '2020-10-19 12:59:21',
                 '2020-10-19 12:59:21');
INSERT INTO little-dev.power_admin_role_relation (id, admin_id, role_id, gmt_create, gmt_modified)
VALUES (4, 3, 3, '2020-10-19 12:59:21',
                 '2020-10-19 12:59:21');
INSERT INTO little-dev.power_admin_role_relation (id, admin_id, role_id, gmt_create, gmt_modified)
VALUES (5, 3, 1, '2020-10-19 12:59:21',
                 '2020-10-19 12:59:21');
INSERT INTO little-dev.power_admin_role_relation (id, admin_id, role_id, gmt_create, gmt_modified)
VALUES (6, 5, 1, '2020-10-19 12:59:32',
                 '2020-10-19 12:59:32');

create table power_role_menu_relation
(
    id           bigint auto_increment comment '关系编号'
        primary key,
    role_id      bigint                             not null comment '角色编号',
    menu_id      bigint                             not null comment '菜单编号',
    gmt_create   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint power_role_menu_relation_power_menu_id_fk
        foreign key (menu_id) references power_menu (id)
            on update cascade on delete cascade,
    constraint power_role_menu_relation_power_role_id_fk
        foreign key (role_id) references power_role (id)
            on update cascade on delete cascade
)
    comment '权限角色关联菜单 ';

INSERT INTO little-dev.power_role_menu_relation (id, role_id, menu_id, gmt_create, gmt_modified)
VALUES (6, 2, 1, '2020-10-18 23:01:49',
                 '2020-10-18 23:01:49');
INSERT INTO little-dev.power_role_menu_relation (id, role_id, menu_id, gmt_create, gmt_modified)
VALUES (7, 2, 2, '2020-10-18 23:01:49',
                 '2020-10-18 23:01:49');
INSERT INTO little-dev.power_role_menu_relation (id, role_id, menu_id, gmt_create, gmt_modified)
VALUES (8, 2, 3, '2020-10-18 23:01:49',
                 '2020-10-18 23:01:49');
INSERT INTO little-dev.power_role_menu_relation (id, role_id, menu_id, gmt_create, gmt_modified)
VALUES (9, 2, 4, '2020-10-18 23:01:49',
                 '2020-10-18 23:01:49');
INSERT INTO little-dev.power_role_menu_relation (id, role_id, menu_id, gmt_create, gmt_modified)
VALUES (10, 2, 5, '2020-10-18 23:01:49',
                  '2020-10-18 23:01:49');
INSERT INTO little-dev.power_role_menu_relation (id, role_id, menu_id, gmt_create, gmt_modified)
VALUES (30, 3, 1, '2020-10-19 21:58:50',
                  '2020-10-19 21:58:50');
INSERT INTO little-dev.power_role_menu_relation (id, role_id, menu_id, gmt_create, gmt_modified)
VALUES (31, 3, 2, '2020-10-19 21:58:50',
                  '2020-10-19 21:58:50');
INSERT INTO little-dev.power_role_menu_relation (id, role_id, menu_id, gmt_create, gmt_modified)
VALUES (32, 3, 3, '2020-10-19 21:58:50',
                  '2020-10-19 21:58:50');
INSERT INTO little-dev.power_role_menu_relation (id, role_id, menu_id, gmt_create, gmt_modified)
VALUES (33, 3, 4, '2020-10-19 21:58:50',
                  '2020-10-19 21:58:50');
INSERT INTO little-dev.power_role_menu_relation (id, role_id, menu_id, gmt_create, gmt_modified)
VALUES (34, 3, 5, '2020-10-19 21:58:50',
                  '2020-10-19 21:58:50');
INSERT INTO little-dev.power_role_menu_relation (id, role_id, menu_id, gmt_create, gmt_modified)
VALUES (35, 3, 8, '2020-10-19 21:58:50',
                  '2020-10-19 21:58:50');
INSERT INTO little-dev.power_role_menu_relation (id, role_id, menu_id, gmt_create, gmt_modified)
VALUES (36, 3, 9, '2020-10-19 21:58:50',
                  '2020-10-19 21:58:50');
INSERT INTO little-dev.power_role_menu_relation (id, role_id, menu_id, gmt_create, gmt_modified)
VALUES (44, 1, 1, '2020-11-24 21:27:41',
                  '2020-11-24 21:27:41');
INSERT INTO little-dev.power_role_menu_relation (id, role_id, menu_id, gmt_create, gmt_modified)
VALUES (45, 1, 2, '2020-11-24 21:27:41',
                  '2020-11-24 21:27:41');
INSERT INTO little-dev.power_role_menu_relation (id, role_id, menu_id, gmt_create, gmt_modified)
VALUES (46, 1, 3, '2020-11-24 21:27:41',
                  '2020-11-24 21:27:41');
INSERT INTO little-dev.power_role_menu_relation (id, role_id, menu_id, gmt_create, gmt_modified)
VALUES (47, 1, 4, '2020-11-24 21:27:41',
                  '2020-11-24 21:27:41');
INSERT INTO little-dev.power_role_menu_relation (id, role_id, menu_id, gmt_create, gmt_modified)
VALUES (48, 1, 5, '2020-11-24 21:27:41',
                  '2020-11-24 21:27:41');
INSERT INTO little-dev.power_role_menu_relation (id, role_id, menu_id, gmt_create, gmt_modified)
VALUES (49, 1, 8, '2020-11-24 21:27:41',
                  '2020-11-24 21:27:41');
INSERT INTO little-dev.power_role_menu_relation (id, role_id, menu_id, gmt_create, gmt_modified)
VALUES (50, 1, 9, '2020-11-24 21:27:41',
                  '2020-11-24 21:27:41');
INSERT INTO little-dev.power_role_menu_relation (id, role_id, menu_id, gmt_create, gmt_modified)
VALUES (51, 1, 13, '2020-11-24 21:27:41',
                   '2020-11-24 21:27:41');
create table power_role_resource_relation
(
    id           bigint auto_increment comment '关系编号'
        primary key,
    role_id      bigint                             not null comment '角色编号',
    resource_id  bigint                             not null comment '资源编号',
    gmt_create   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint power_role_resource_relation_power_resource_id_fk
        foreign key (resource_id) references power_resource (id)
            on update cascade on delete cascade,
    constraint power_role_resource_relation_power_role_id_fk
        foreign key (role_id) references power_role (id)
            on update cascade on delete cascade
)
    comment '权限角色关联资源 ';

INSERT INTO little-dev.power_role_resource_relation (id, role_id, resource_id, gmt_create, gmt_modified)
VALUES (1, 2, 1, '2020-10-18 23:10:02',
                 '2020-10-18 23:10:02');
INSERT INTO little-dev.power_role_resource_relation (id, role_id, resource_id, gmt_create, gmt_modified)
VALUES (2, 2, 2, '2020-10-18 23:10:02',
                 '2020-10-18 23:10:02');
INSERT INTO little-dev.power_role_resource_relation (id, role_id, resource_id, gmt_create, gmt_modified)
VALUES (3, 2, 3, '2020-10-18 23:10:02',
                 '2020-10-18 23:10:02');
INSERT INTO little-dev.power_role_resource_relation (id, role_id, resource_id, gmt_create, gmt_modified)
VALUES (4, 2, 4, '2020-10-18 23:10:02',
                 '2020-10-18 23:10:02');
INSERT INTO little-dev.power_role_resource_relation (id, role_id, resource_id, gmt_create, gmt_modified)
VALUES (5, 2, 5, '2020-10-18 23:10:02',
                 '2020-10-18 23:10:02');
INSERT INTO little-dev.power_role_resource_relation (id, role_id, resource_id, gmt_create, gmt_modified)
VALUES (6, 3, 1, '2020-10-18 23:10:05',
                 '2020-10-18 23:10:05');
INSERT INTO little-dev.power_role_resource_relation (id, role_id, resource_id, gmt_create, gmt_modified)
VALUES (7, 3, 2, '2020-10-18 23:10:05',
                 '2020-10-18 23:10:05');
INSERT INTO little-dev.power_role_resource_relation (id, role_id, resource_id, gmt_create, gmt_modified)
VALUES (8, 3, 3, '2020-10-18 23:10:05',
                 '2020-10-18 23:10:05');
INSERT INTO little-dev.power_role_resource_relation (id, role_id, resource_id, gmt_create, gmt_modified)
VALUES (9, 3, 4, '2020-10-18 23:10:05',
                 '2020-10-18 23:10:05');
INSERT INTO little-dev.power_role_resource_relation (id, role_id, resource_id, gmt_create, gmt_modified)
VALUES (10, 3, 5, '2020-10-18 23:10:05',
                  '2020-10-18 23:10:05');
INSERT INTO little-dev.power_role_resource_relation (id, role_id, resource_id, gmt_create, gmt_modified)
VALUES (42, 1, 1, '2020-10-19 21:10:55',
                  '2020-10-19 21:10:55');
INSERT INTO little-dev.power_role_resource_relation (id, role_id, resource_id, gmt_create, gmt_modified)
VALUES (43, 1, 2, '2020-10-19 21:10:55',
                  '2020-10-19 21:10:55');
INSERT INTO little-dev.power_role_resource_relation (id, role_id, resource_id, gmt_create, gmt_modified)
VALUES (44, 1, 3, '2020-10-19 21:10:55',
                  '2020-10-19 21:10:55');
INSERT INTO little-dev.power_role_resource_relation (id, role_id, resource_id, gmt_create, gmt_modified)
VALUES (45, 1, 4, '2020-10-19 21:10:55',
                  '2020-10-19 21:10:55');
INSERT INTO little-dev.power_role_resource_relation (id, role_id, resource_id, gmt_create, gmt_modified)
VALUES (46, 1, 5, '2020-10-19 21:10:55',
                  '2020-10-19 21:10:55');
INSERT INTO little-dev.power_role_resource_relation (id, role_id, resource_id, gmt_create, gmt_modified)
VALUES (47, 1, 6, '2020-10-19 21:10:55',
                  '2020-10-19 21:10:55');
create table system_api_log
(
    id           bigint auto_increment comment '编号'
        primary key,
    type         varchar(32)  not null comment '类型 微信端：wx，管理端：admin',
    user_name    varchar(32)  null comment '操作用户',
    description  varchar(128) not null comment '操作描述',
    start_time   datetime     not null comment '操作时间',
    spend_time   double       null comment '消耗时间',
    base_path    varchar(128) null comment '请求域名',
    uri          varchar(128) null comment 'URI',
    url          varchar(512) null comment 'URL',
    method       varchar(32)  not null comment '请求类型 post、put、get、delete',
    ip           varchar(32)  null comment 'IP地址',
    parameter    text         null comment '请求参数',
    result       text         null comment '返回结果',
    gmt_create   datetime     not null comment '创建时间',
    gmt_modified datetime     not null comment '更新时间'
)
    comment '系统接口日志 ';

create table system_dictionary
(
    id           bigint auto_increment comment '编号'
        primary key,
    parent_id    varchar(32)                            null comment '父对象',
    code         varchar(32)                            not null comment '字典码',
    show_value   varchar(32)                            not null comment '显示值',
    type         varchar(32)                            not null comment '类型',
    sort         int                                    not null comment '排序（从1开始排序）',
    status       int          default 0                 not null comment '状态(-1 ： 全部，0：正常，1：锁定)',
    remark       varchar(128) default ''                null comment '备注说明(小于120字)',
    logic_delete int          default 0                 not null comment '逻辑删除 0：正常，1：删除',
    created_by   bigint       default 10000004          not null comment '创建人',
    gmt_create   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    modified_by  bigint       default 10000004          not null comment '更新人',
    gmt_modified datetime     default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '系统数据字典';

create table system_login_log
(
    id           bigint auto_increment comment '记录编号'
        primary key,
    type         int                                 not null comment '类型0：微信端1：管理端',
    account_id   bigint                              not null comment '小程序用户编号/管理员编号',
    name         varchar(255)                        not null comment '账号名称',
    ip_address   varchar(255)                        null comment 'ip地址',
    gmt_create   timestamp default CURRENT_TIMESTAMP not null comment '创建日期',
    gmt_modified timestamp default CURRENT_TIMESTAMP not null comment '更新日期'
)
    comment '系统用户登录日志' charset = utf8;

create index log_admin_login_power_admin_id_fk
    on system_login_log (account_id);


create table user_info
(
    id           bigint auto_increment comment '用户编号'
        primary key,
    nickname     varchar(32)                           not null comment '昵称',
    username     varchar(32)                           not null comment '用户名',
    open_id      varchar(64)                           null comment 'OpenID',
    phone        varchar(32)                           not null comment '手机号',
    password     varchar(255)                          not null comment '密码',
    status       int         default 1                 not null comment '状态 0：禁用，1：启用',
    logic_delete varchar(10) default 'no'              not null comment '逻辑删除 yes：删除，no：正常',
    gmt_create   datetime    default CURRENT_TIMESTAMP not null comment '注册日期',
    gmt_modified datetime    default CURRENT_TIMESTAMP not null comment '修改日期'
)
    comment '用户信息 ';

