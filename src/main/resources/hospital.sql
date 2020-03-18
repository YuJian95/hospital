create table if not exists hospital_clinic
(
    id            bigint auto_increment comment '诊室编号'
        constraint `PRIMARY`
        primary key,
    outpatient_id bigint                             not null comment '所属门诊',
    address       varchar(128)                       not null comment '诊室地址',
    gmt_create    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified  datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '诊室信息表';

create table if not exists hospital_doctor
(
    id           bigint auto_increment comment '医生编号'
        constraint `PRIMARY`
        primary key,
    name         varchar(32)   not null comment '医生姓名',
    gender       int default 1 not null comment '性别：1，男；2，女',
    job_title    varchar(32)   not null comment '医生职称',
    specialty    varchar(512)  not null comment '医生专长',
    gmt_create   datetime      not null comment '创建时间',
    gmt_modified datetime      not null comment '更新时间'
)
    comment '医生信息表';

create table if not exists hospital_info
(
    id           bigint auto_increment comment '医院编号 从1001开始'
        constraint `PRIMARY`
        primary key,
    name         varchar(32)  not null comment '医院名称',
    phone        varchar(32)  null comment '医院电话',
    address      varchar(128) null comment '医院地址',
    description  varchar(512) null comment '医院简介',
    picture      varchar(512) null comment '医院图片',
    gmt_create   datetime     not null comment '创建时间',
    gmt_modified datetime     not null comment '更新时间',
    constraint hospital_info_phone_uindex
        unique (phone)
)
    comment '医院信息表';

create table if not exists hospital_special
(
    id           bigint auto_increment comment '专科编号'
        constraint `PRIMARY`
        primary key,
    name         varchar(32)  not null comment '专科名称',
    description  varchar(512) not null comment '专科简介',
    gmt_create   datetime     not null comment '创建时间',
    gmt_modified datetime     not null comment '更新时间',
    constraint hospital_special_name_uindex
        unique (name)
)
    comment '医院专科表';

create table if not exists hospital_outpatient
(
    id           bigint auto_increment comment '门诊编号'
        constraint `PRIMARY`
        primary key,
    name         varchar(32) not null comment '门诊名称',
    special_id   bigint      not null comment '所属专科',
    gmt_create   datetime    not null comment '创建时间',
    gmt_modified datetime    not null comment '更新时间',
    constraint hospital_outpatient_hospital_special_id_fk
        foreign key (special_id) references hospital_special (id)
            on update cascade on delete cascade
)
    comment '医院门诊表';

create table if not exists hospital_outpatient_relation
(
    id            bigint auto_increment comment '关系编号'
        constraint `PRIMARY`
        primary key,
    hospital_id   bigint                             not null comment '医院编号',
    outpatient_id bigint                             not null comment '门诊编号',
    gmt_create    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified  datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint hospital_outpatient_relation_hospital_info_id_fk
        foreign key (hospital_id) references hospital_info (id)
            on update cascade on delete cascade,
    constraint hospital_outpatient_relation_hospital_outpatient_id_fk
        foreign key (outpatient_id) references hospital_outpatient (id)
            on update cascade on delete cascade
)
    comment '医院门诊关系表';

create table if not exists hospital_special_relation
(
    id           bigint auto_increment comment '关系编号'
        constraint `PRIMARY`
        primary key,
    hospital_id  bigint                             not null comment '医院编号',
    special_id   bigint                             not null comment '专科编号',
    gmt_create   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint hospital_special_relation_hospital_info_id_fk
        foreign key (hospital_id) references hospital_info (id)
            on update cascade on delete cascade,
    constraint hospital_special_relation_hospital_special_id_fk
        foreign key (special_id) references hospital_special (id)
            on update cascade on delete cascade
);

create table if not exists power_account
(
    id           bigint auto_increment comment '编号'
        constraint `PRIMARY`
        primary key,
    name         varchar(32)                        not null comment '登录账号 唯一',
    password     varchar(512)                       not null comment '登录密码 使用md5加密',
    status       int      default 1                 not null comment '账号状态 1：正常，0：锁定',
    login_time   datetime                           null comment '最后登录时间',
    gmt_create   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint power_account_name_uindex
        unique (name)
)
    comment '账号信息表';

create table if not exists log_account_login
(
    id           bigint auto_increment comment '登录记录编号'
        constraint `PRIMARY`
        primary key,
    account_id   bigint                             not null comment '账号编号',
    account_name varchar(32)                        null comment '账号名称',
    ip_address   varchar(32)                        null comment 'ip地址',
    gmt_create   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint log_account_login_power_account_id_fk
        foreign key (account_id) references power_account (id)
            on update cascade on delete cascade
)
    comment '账号登录记录表';

create table if not exists log_operation
(
    id           bigint auto_increment comment '编号'
        constraint `PRIMARY`
        primary key,
    account_name varchar(32)                        null comment '账号名称',
    start_time   bigint                             null comment '开始时间',
    spend_time   int                                null comment '消耗时间',
    description  varchar(512)                       null comment '操作描述',
    base_path    varchar(512)                       null comment '根路径',
    uri          varchar(512)                       null comment 'uri',
    url          varchar(512)                       null comment 'url',
    method       varchar(32)                        null comment '请求方法',
    ip_address   varchar(32)                        null comment 'ip地址',
    parameter    varchar(3072)                      null comment '请求参数',
    result       text                               null comment '请求结果',
    gmt_create   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint log_operation_power_account_name_fk
        foreign key (account_name) references power_account (name)
            on update cascade on delete cascade
)
    comment '用户操作记录表';

create table if not exists power_menu
(
    id           bigint auto_increment comment '菜单编号'
        constraint `PRIMARY`
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
    comment '权限菜单表';

create table if not exists power_resource_category
(
    id           bigint auto_increment comment '分类编号'
        constraint `PRIMARY`
        primary key,
    name         varchar(32)                        not null comment '分类名称',
    sort         int      default 1                 not null comment '分类排序 数值越小，越靠前',
    gmt_create   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint power_resource_category_name_uindex
        unique (name)
)
    comment '权限资源分类表';

create table if not exists power_resource
(
    id           bigint auto_increment comment '资源编号'
        constraint `PRIMARY`
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
        unique (url),
    constraint power_resource_power_resource_category_id_fk
        foreign key (category_id) references power_resource_category (id)
            on update cascade on delete cascade
)
    comment '权限资源表';

create table if not exists power_role
(
    id           bigint auto_increment comment '角色编号'
        constraint `PRIMARY`
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
    comment '权限角色表';

create table if not exists power_account_role_relation
(
    id           bigint auto_increment comment '账号角色关系编号'
        constraint `PRIMARY`
        primary key,
    account_id   bigint                             not null comment '账号编号',
    role_id      bigint                             not null comment '角色编号',
    gmt_create   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint power_account_role_relation_power_account_id_fk
        foreign key (account_id) references power_account (id)
            on update cascade on delete cascade,
    constraint power_account_role_relation_power_role_id_fk
        foreign key (role_id) references power_role (id)
            on update cascade on delete cascade
)
    comment '账号角色关系表';

create table if not exists power_role_menu_relation
(
    id           bigint auto_increment comment '关系编号'
        constraint `PRIMARY`
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
    comment '角色菜单关系表';

create table if not exists power_role_resource_relation
(
    id           bigint auto_increment comment '关系编号'
        constraint `PRIMARY`
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
    comment '角色资源关系表';

create table if not exists user_basic_info
(
    id           bigint auto_increment comment '编号'
        constraint `PRIMARY`
        primary key,
    name         varchar(32)                        not null comment '姓名',
    avatar_url   varchar(512)                       not null comment '用户头像',
    phone        varchar(32)                        not null comment '手机号',
    gmt_create   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint user_basic_info_phone_uindex
        unique (phone),
    constraint user_basic_info_power_account_name_fk
        foreign key (phone) references power_account (name)
            on update cascade on delete cascade
)
    comment '用户基础信息表';

create table if not exists user_case
(
    id           bigint auto_increment comment '记录编号'
        constraint `PRIMARY`
        primary key,
    account_id   bigint                             not null comment '账号编号',
    order_id     bigint                             not null comment '预约编号',
    doctor_id    bigint                             not null comment '医生编号',
    content      varchar(512)                       not null comment '病例详情',
    gmt_create   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint user_case_hospital_doctor_id_fk
        foreign key (doctor_id) references hospital_doctor (id)
            on update cascade on delete cascade,
    constraint user_case_power_account_id_fk
        foreign key (account_id) references power_account (id)
            on update cascade on delete cascade
)
    comment '用户病例表';

create table if not exists user_medical_card
(
    id                    bigint auto_increment comment '就诊卡号'
        constraint `PRIMARY`
        primary key,
    name                  varchar(32)                        not null comment '姓名',
    gender                int      default 1                 not null comment '性别 男：1，女：2',
    phone                 varchar(32)                        not null comment '手机号',
    identification_number varchar(32)                        not null comment '证件号',
    birth_date            datetime                           not null comment '出生日期',
    gmt_create            datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified          datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint user_medical_card_identification_number_uindex
        unique (identification_number)
)
    comment '用户就诊卡信息表';

create table if not exists user_medical_card_relation
(
    id           bigint auto_increment comment '关系编号'
        constraint `PRIMARY`
        primary key,
    type         int                                not null comment '关系类型 0：本人，1：父母，2：兄弟/姐妹，3：伴侣，4：子女，5：同事/朋友，6：其他',
    account_id   bigint                             not null comment '账号编号',
    card_id      bigint                             not null comment '就诊卡编号',
    gmt_create   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint user_medical_card_relation_power_account_id_fk
        foreign key (account_id) references power_account (id)
            on update cascade on delete cascade,
    constraint user_medical_card_relation_user_medical_card_id_fk
        foreign key (card_id) references user_medical_card (id)
            on update cascade on delete cascade
)
    comment '用户就诊卡关系表';

create table if not exists visit_order
(
    id           bigint auto_increment comment '预约编号'
        constraint `PRIMARY`
        primary key,
    plan_id      bigint                             not null comment '出诊编号',
    card_id      bigint                             not null comment '就诊卡号',
    status       int      default 0                 not null comment '预约状态 0：未开始，1：未按时就诊，2：取消预约挂号，3：已完成',
    gmt_create   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '出诊预约表';

create table if not exists visit_plan
(
    id           bigint auto_increment comment '出诊编号'
        constraint `PRIMARY`
        primary key,
    hospital_id  bigint                             not null comment '医院编号',
    special_id   bigint                             not null comment '专科编号',
    outpatient   bigint                             not null comment '门诊编号',
    clinic_id    bigint                             not null comment '诊室编号',
    doctor_id    bigint                             not null comment '医生编号',
    time_period  int      default 1                 not null comment '时间段 1： 8点半~9点，2： 9点~9点半，3： 9点半~10点，4： 10点~10点半，5： 11点~11点半，6： 11点半~12点，7：2点~2点半，8： 2点半~3点，9： 3点~3点半，10： 3点半~4点，11： 4点~4点半，12： 4点半~5点，13： 5点~5点半，14：5点半~6点',
    day          datetime default CURRENT_TIMESTAMP not null comment '出诊日期',
    gmt_create   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint visit_plan_hospital_clinic_id_fk
        foreign key (clinic_id) references hospital_clinic (id)
            on update cascade on delete cascade,
    constraint visit_plan_hospital_doctor_id_fk
        foreign key (doctor_id) references hospital_doctor (id)
            on update cascade on delete cascade,
    constraint visit_plan_hospital_info_id_fk
        foreign key (hospital_id) references hospital_info (id)
            on update cascade on delete cascade,
    constraint visit_plan_hospital_outpatient_id_fk
        foreign key (outpatient) references hospital_outpatient (id)
            on update cascade on delete cascade,
    constraint visit_plan_hospital_special_id_fk
        foreign key (special_id) references hospital_special (id)
            on update cascade on delete cascade
)
    comment '出诊信息表';

