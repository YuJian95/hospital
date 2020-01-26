create schema base collate utf8mb4_general_ci;

create table power_account
(
    id bigint auto_increment comment '编号'
        primary key,
    name varchar(32) not null comment '登录账号 唯一',
    password varchar(512) not null comment '登录密码 使用md5加密',
    status int default 1 not null comment '账号状态 1：正常，0：锁定',
    login_time datetime null comment '最后登录时间',
    gmt_create datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint power_account_name_uindex
        unique (name)
)
    comment '账号信息表';

create table log_account_login
(
    id bigint auto_increment comment '登录记录编号'
        primary key,
    account_id bigint not null comment '账号编号',
    account_name varchar(32) null comment '账号名称',
    ip_address varchar(32) null comment 'ip地址',
    gmt_create datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint log_account_login_power_account_id_fk
        foreign key (account_id) references power_account (id)
            on update cascade on delete cascade
)
    comment '账号登录记录表 ';

create table log_operation
(
    id bigint auto_increment comment '编号'
        primary key,
    account_name varchar(32) null comment '账号名称',
    start_time bigint null comment '开始时间',
    spend_time int null comment '消耗时间',
    description varchar(512) null comment '操作描述',
    base_path varchar(512) null comment '根路径',
    uri varchar(512) null comment 'uri',
    url varchar(512) null comment 'url',
    method varchar(32) null comment '请求方法',
    ip_address varchar(32) null comment 'ip地址',
    parameter varchar(3072) null comment '请求参数',
    result text null comment '请求结果',
    gmt_create datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint log_operation_power_account_name_fk
        foreign key (account_name) references power_account (name)
            on update cascade on delete cascade
)
    comment '用户操作记录表 ';

create table power_permission
(
    id bigint auto_increment comment '权限编号'
        primary key,
    type int not null comment '权限类型 0->目录；1->菜单；2->按钮（接口绑定权限）',
    name varchar(32) not null comment '权限名称',
    value varchar(128) not null comment '权限值',
    icon varchar(128) null comment '前端图标',
    url varchar(128) null comment '前端路径',
    pid bigint null comment '父权限编号',
    status int default 1 not null comment '权限状态 1：开启，0：禁用',
    gmt_create datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint power_permission_value_uindex
        unique (value)
)
    comment '权限信息表';

create table power_account_permission_relation
(
    id bigint auto_increment comment '账号特别权限关系编号'
        primary key,
    account_id bigint not null comment '账号编号',
    permission_id bigint not null comment '权限编号',
    type int default 1 not null comment '关系类型 -1：去除权限，1：增加权限',
    gmt_create datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint power_account_permission_relation_power_account_id_fk
        foreign key (account_id) references power_account (id)
            on update cascade on delete cascade,
    constraint power_account_permission_relation_power_permission_id_fk
        foreign key (permission_id) references power_permission (id)
            on update cascade on delete cascade
)
    comment '账号特别权限关系表';

create table power_role
(
    id bigint auto_increment comment '角色编号'
        primary key,
    name varchar(32) not null comment '英文名称',
    chinese_name varchar(32) not null comment '中文名称',
    status int default 1 not null comment '角色状态 1：启用，0：禁用',
    gmt_create datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint power_role_name_uindex
        unique (name)
)
    comment '权限角色表';

create table power_account_role_relation
(
    id bigint auto_increment comment '账号角色关系编号'
        primary key,
    account_id bigint not null comment '账号编号',
    role_id bigint not null comment '角色编号',
    gmt_create datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint power_account_role_relation_power_account_id_fk
        foreign key (account_id) references power_account (id)
            on update cascade on delete cascade,
    constraint power_account_role_relation_power_role_id_fk
        foreign key (role_id) references power_role (id)
            on update cascade on delete cascade
)
    comment '账号角色关系表';

create table power_role_permission_relation
(
    id bigint auto_increment comment '角色权限关系编号'
        primary key,
    role_id bigint not null comment '角色编号',
    permission_id bigint not null comment '权限编号',
    gmt_create datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint power_role_permission_relation_power_permission_id_fk
        foreign key (permission_id) references power_permission (id)
            on update cascade,
    constraint power_role_permission_relation_power_role_id_fk
        foreign key (role_id) references power_role (id)
            on update cascade on delete cascade
)
    comment '角色权限关系表';

