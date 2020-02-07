/*
 Navicat Premium Data Transfer

 Source Server         : yang
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost
 Source Schema         : hospital

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 07/02/2020 20:12:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hospital_doctor
-- ----------------------------
DROP TABLE IF EXISTS `hospital_doctor`;
CREATE TABLE `hospital_doctor`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '医生编号',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '医生姓名',
  `gender` int(11) NOT NULL DEFAULT 1 COMMENT '性别：1，男；2，女',
  `job_title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '医生职称',
  `specialty` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '医生专长',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '医生信息表 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for hospital_info
-- ----------------------------
DROP TABLE IF EXISTS `hospital_info`;
CREATE TABLE `hospital_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '医院编号 从1001开始',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '医院名称',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '医院电话',
  `address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '医院地址',
  `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '医院简介',
  `picture` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '医院图片',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `hospital_info_phone_uindex`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1001 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '医院信息表 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hospital_info
-- ----------------------------
INSERT INTO `hospital_info` VALUES (1000, '广东省中医院', '123', '广州大学城', '广东省中医院', 'http://img1.imgtn.bdimg.com/it/u=1854537091,1525426764&fm=26&gp=0.jpg', '2020-02-05 13:06:55', '2020-02-05 13:06:55');

-- ----------------------------
-- Table structure for hospital_outpatient
-- ----------------------------
DROP TABLE IF EXISTS `hospital_outpatient`;
CREATE TABLE `hospital_outpatient`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '门诊编号',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '门诊名称',
  `special_id` bigint(20) NOT NULL COMMENT '所属专科',
  `hospital_id` bigint(20) NOT NULL COMMENT '所属医院',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `hospital_outpatient_hospital_info_id_fk`(`hospital_id`) USING BTREE,
  INDEX `hospital_outpatient_hospital_special_id_fk`(`special_id`) USING BTREE,
  CONSTRAINT `hospital_outpatient_hospital_info_id_fk` FOREIGN KEY (`hospital_id`) REFERENCES `hospital_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `hospital_outpatient_hospital_special_id_fk` FOREIGN KEY (`special_id`) REFERENCES `hospital_special` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '医院门诊表 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hospital_outpatient
-- ----------------------------
INSERT INTO `hospital_outpatient` VALUES (1, '儿童发烧', 1, 1000, '2020-02-05 13:09:40', '2020-02-05 13:09:40');
INSERT INTO `hospital_outpatient` VALUES (2, '普通门诊', 1, 1000, '2020-02-05 13:09:54', '2020-02-05 13:09:54');
INSERT INTO `hospital_outpatient` VALUES (3, '皮肤治疗', 2, 1000, '2020-02-05 21:14:33', '2020-02-05 21:14:36');

-- ----------------------------
-- Table structure for hospital_special
-- ----------------------------
DROP TABLE IF EXISTS `hospital_special`;
CREATE TABLE `hospital_special`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '专科编号',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '专科名称',
  `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '专科简介',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `hospital_special_name_uindex`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '医院专科表 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hospital_special
-- ----------------------------
INSERT INTO `hospital_special` VALUES (1, '儿科', '儿童相关治疗', '2020-02-05 13:08:40', '2020-02-05 13:08:40');
INSERT INTO `hospital_special` VALUES (2, '皮肤科', '皮肤治疗', '2020-02-05 21:13:29', '2020-02-05 21:13:31');

-- ----------------------------
-- Table structure for hospital_special_relation
-- ----------------------------
DROP TABLE IF EXISTS `hospital_special_relation`;
CREATE TABLE `hospital_special_relation`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '关系编号',
  `hospital_id` bigint(20) NOT NULL COMMENT '医院编号',
  `special_id` bigint(20) NOT NULL COMMENT '专科编号',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `hospital_special_relation_hospital_info_id_fk`(`hospital_id`) USING BTREE,
  INDEX `hospital_special_relation_hospital_special_id_fk`(`special_id`) USING BTREE,
  CONSTRAINT `hospital_special_relation_hospital_info_id_fk` FOREIGN KEY (`hospital_id`) REFERENCES `hospital_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `hospital_special_relation_hospital_special_id_fk` FOREIGN KEY (`special_id`) REFERENCES `hospital_special` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ' ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hospital_special_relation
-- ----------------------------
INSERT INTO `hospital_special_relation` VALUES (1, 1000, 1, '2020-02-05 13:09:09', '2020-02-05 13:09:09');
INSERT INTO `hospital_special_relation` VALUES (2, 1000, 2, '2020-02-05 21:14:01', '2020-02-05 21:14:01');

-- ----------------------------
-- Table structure for log_account_login
-- ----------------------------
DROP TABLE IF EXISTS `log_account_login`;
CREATE TABLE `log_account_login`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '登录记录编号',
  `account_id` bigint(20) NOT NULL COMMENT '账号编号',
  `account_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账号名称',
  `ip_address` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `log_account_login_power_account_id_fk`(`account_id`) USING BTREE,
  CONSTRAINT `log_account_login_power_account_id_fk` FOREIGN KEY (`account_id`) REFERENCES `power_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '账号登录记录表 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log_account_login
-- ----------------------------
INSERT INTO `log_account_login` VALUES (1, 1, 'admin', '0:0:0:0:0:0:0:1', '2020-01-25 13:27:42', '2020-01-25 13:27:42');
INSERT INTO `log_account_login` VALUES (2, 1, 'admin', '0:0:0:0:0:0:0:1', '2020-01-25 13:28:36', '2020-01-25 13:28:36');
INSERT INTO `log_account_login` VALUES (3, 1, 'admin', '0:0:0:0:0:0:0:1', '2020-01-26 10:22:25', '2020-01-26 10:22:25');
INSERT INTO `log_account_login` VALUES (4, 1, 'admin', '0:0:0:0:0:0:0:1', '2020-01-26 16:30:12', '2020-01-26 16:30:12');
INSERT INTO `log_account_login` VALUES (5, 1, 'admin', '0:0:0:0:0:0:0:1', '2020-01-30 15:03:45', '2020-01-30 15:03:45');
INSERT INTO `log_account_login` VALUES (6, 1, 'admin', '0:0:0:0:0:0:0:1', '2020-02-02 12:33:45', '2020-02-02 12:33:45');
INSERT INTO `log_account_login` VALUES (7, 1, 'admin', '0:0:0:0:0:0:0:1', '2020-02-03 13:10:10', '2020-02-03 13:10:10');
INSERT INTO `log_account_login` VALUES (8, 1, 'admin', '0:0:0:0:0:0:0:1', '2020-02-05 13:04:13', '2020-02-05 13:04:13');
INSERT INTO `log_account_login` VALUES (9, 1, 'admin', '0:0:0:0:0:0:0:1', '2020-02-06 12:18:37', '2020-02-06 12:18:37');
INSERT INTO `log_account_login` VALUES (10, 1, 'admin', '0:0:0:0:0:0:0:1', '2020-02-06 13:55:08', '2020-02-06 13:55:08');

-- ----------------------------
-- Table structure for log_operation
-- ----------------------------
DROP TABLE IF EXISTS `log_operation`;
CREATE TABLE `log_operation`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `account_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账号名称',
  `start_time` bigint(20) NULL DEFAULT NULL COMMENT '开始时间',
  `spend_time` int(11) NULL DEFAULT NULL COMMENT '消耗时间',
  `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作描述',
  `base_path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '根路径',
  `uri` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'uri',
  `url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'url',
  `method` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `ip_address` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `parameter` varchar(3072) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求结果',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `log_operation_power_account_name_fk`(`account_name`) USING BTREE,
  CONSTRAINT `log_operation_power_account_name_fk` FOREIGN KEY (`account_name`) REFERENCES `power_account` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户操作记录表 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log_operation
-- ----------------------------
INSERT INTO `log_operation` VALUES (1, 'admin', 1580036647189, 107, '获取账号拥有所有权限（包括特殊权限）', 'http://localhost:8080', '/base/power/account/permission', 'http://localhost:8080/base/power/account/permission', 'GET', NULL, '{arg0=1}', 'CommonResult{code=200, message=\'操作成功\', data=[PowerPermission [Hash = 2137469282, id=1, type=0, name=权限模块, value=power:all, icon=null, url=null, pid=0, status=1, gmtCreate=Mon Jan 27 02:17:26 CST 2020, gmtModified=Mon Jan 27 02:17:26 CST 2020, serialVersionUID=1], PowerPermission [Hash = 193460305, id=2, type=0, name=日志模块, value=log:all, icon=null, url=null, pid=0, status=1, gmtCreate=Mon Jan 27 02:17:26 CST 2020, gmtModified=Mon Jan 27 02:17:26 CST 2020, serialVersionUID=1], PowerPermission [Hash = 1525330454, id=10, type=1, name=权限角色, value=power:role:all, icon=null, url=null, pid=1, status=1, gmtCreate=Mon Jan 27 02:17:26 CST 2020, gmtModified=Mon Jan 27 02:17:26 CST 2020, serialVersionUID=1], PowerPermission [Hash = 728351962, id=11, type=1, name=权限账号, value=power:account:all, icon=null, url=null, pid=1, status=1, gmtCreate=Mon Jan 27 02:17:26 CST 2020, gmtModified=Mon Jan 27 02:17:26 CST 2020, serialVersionUID=1], PowerPermission [Hash = 1833110772, id=12, type=1, name=权限权值, value=power:permission:all, icon=null, url=null, pid=1, status=1, gmtCreate=Mon Jan 27 02:17:26 CST 2020, gmtModified=Mon Jan 27 02:17:26 CST 2020, serialVersionUID=1], PowerPermission [Hash = 1504846973, id=20, type=1, name=操作日志, value=log:operation:all, icon=null, url=null, pid=2, status=1, gmtCreate=Mon Jan 27 02:17:26 CST 2020, gmtModified=Mon Jan 27 02:17:26 CST 2020, serialVersionUID=1], PowerPermission [Hash = 1447515999, id=21, type=1, name=登录日志, value=log:account:login:all, icon=null, url=null, pid=2, status=1, gmtCreate=Mon Jan 27 02:17:26 CST 2020, gmtModified=Mon Jan 27 02:17:26 CST 2020, serialVersionUID=1], PowerPermission [Hash = 862787161, id=100, type=2, name=获取当前账号信息, value=power:account:info:get, icon=null, url=null, pid=10, status=1, gmtCreate=Mon Jan 27 02:17:27 CST 2020, gmtModified=Mon Jan 27 02:17:27 CST 2020, serialVersionUID=1], PowerPermission [Hash = 1186760812, id=101, type=2, name=更新账号状态, value=power:account:status:put, icon=null, url=null, pid=10, status=1, gmtCreate=Mon Jan 27 02:17:27 CST 2020, gmtModified=Mon Jan 27 02:17:27 CST 2020, serialVersionUID=1], PowerPermission [Hash = 249945354, id=102, type=2, name=更新账号分配角色, value=power:account:role:post, icon=null, url=null, pid=10, status=1, gmtCreate=Mon Jan 27 02:17:27 CST 2020, gmtModified=Mon Jan 27 02:17:27 CST 2020, serialVersionUID=1], PowerPermission [Hash = 1660198438, id=103, type=2, name=获取账号所有权限, value=power:account:permission:get, icon=null, url=null, pid=10, status=1, gmtCreate=Mon Jan 27 02:17:27 CST 2020, gmtModified=Mon Jan 27 02:17:27 CST 2020, serialVersionUID=1], PowerPermission [Hash = 972067839, id=120, type=2, name=分页：搜索权限角色, value=power:role:list:get, icon=null, url=null, pid=11, status=1, gmtCreate=Mon Jan 27 02:17:27 CST 2020, gmtModified=Mon Jan 27 02:17:27 CST 2020, serialVersionUID=1], PowerPermission [Hash = 464360261, id=121, type=2, name=增加权限角色, value=power:role:post, icon=null, url=null, pid=11, status=1, gmtCreate=Mon Jan 27 02:17:27 CST 2020, gmtModified=Mon Jan 27 02:17:27 CST 2020, serialVersionUID=1], PowerPermission [Hash = 989860023, id=122, type=2, name=修改权限角色, value=power:role:put, icon=null, url=null, pid=11, status=1, gmtCreate=Mon Jan 27 02:17:27 CST 2020, gmtModified=Mon Jan 27 02:17:27 CST 2020, serialVersionUID=1], PowerPermission [Hash = 2109975647, id=123, type=2, name=获取角色所有权限, value=power:role:permission:get, icon=null, url=null, pid=11, status=1, gmtCreate=Mon Jan 27 02:17:28 CST 2020, gmtModified=Mon Jan 27 02:17:28 CST 2020, serialVersionUID=1], PowerPermission [Hash = 1083287411, id=124, type=2, name=更新角色所有权限, value=power:role:permission:put, icon=null, url=null, pid=11, status=1, gmtCreate=Mon Jan 27 02:17:28 CST 2020, gmtModified=Mon Jan 27 02:17:28 CST 2020, serialVersionUID=1], PowerPermission [Hash = 936453023, id=140, type=2, name=分页：搜索权限权值, value=power:permission:list:get, icon=null, url=null, pid=12, status=1, gmtCreate=Mon Jan 27 02:17:28 CST 2020, gmtModified=Mon Jan 27 02:17:28 CST 2020, serialVersionUID=1], PowerPermission [Hash = 216991866, id=141, type=2, name=增加权限权值, value=power:permission:post, icon=null, url=null, pid=12, status=1, gmtCreate=Mon Jan 27 02:17:28 CST 2020, gmtModified=Mon Jan 27 02:17:28 CST 2020, serialVersionUID=1], PowerPermission [Hash = 1815014636, id=142, type=2, name=修改权限权值, value=power:permission:put, icon=null, url=null, pid=12, status=1, gmtCreate=Mon Jan 27 02:17:28 CST 2020, gmtModified=Mon Jan 27 02:17:28 CST 2020, serialVersionUID=1], PowerPermission [Hash = 558457893, id=143, type=2, name=获取所有权限权值, value=power:permission:tree:get, icon=null, url=null, pid=12, status=1, gmtCreate=Mon Jan 27 02:17:28 CST 2020, gmtModified=Mon Jan 27 02:17:28 CST 2020, serialVersionUID=1], PowerPermission [Hash = 523925064, id=144, type=2, name=获取相应父级权限, value=power:permission:list:type:get, icon=null, url=null, pid=12, status=1, gmtCreate=Mon Jan 27 02:17:28 CST 2020, gmtModified=Mon Jan 27 02:17:28 CST 2020, serialVersionUID=1], PowerPermission [Hash = 1801589005, id=200, type=2, name=分页：搜索账号登录日志, value=log:account:login:list:get, icon=null, url=null, pid=21, status=1, gmtCreate=Mon Jan 27 02:17:29 CST 2020, gmtModified=Mon Jan 27 02:17:29 CST 2020, serialVersionUID=1], PowerPermission [Hash = 1890638081, id=220, type=2, name=分页：搜索账号操作日志, value=log:opreation:list:get, icon=null, url=null, pid=20, status=1, gmtCreate=Mon Jan 27 02:17:29 CST 2020, gmtModified=Mon Jan 27 02:17:29 CST 2020, serialVersionUID=1]]}', '2020-01-26 11:04:07', '2020-01-26 11:04:07');
INSERT INTO `log_operation` VALUES (2, 'admin', 1580039604971, 58, '获取当前账号信息', 'http://localhost:8080', '/base/power/account/info', 'http://localhost:8080/base/power/account/info', 'GET', NULL, NULL, 'CommonResult{code=200, message=\'操作成功\', data=PowerAccount [Hash = 2087717713, id=1, name=admin, password=null, status=1, loginTime=Sun Jan 26 18:22:25 CST 2020, gmtCreate=Sat Jan 25 16:04:14 CST 2020, gmtModified=Sat Jan 25 16:04:14 CST 2020, serialVersionUID=1]}', '2020-01-26 11:53:25', '2020-01-26 11:53:25');
INSERT INTO `log_operation` VALUES (3, NULL, 1580056210556, 1615, '账号登录', 'http://localhost:8080', '/base/power/account/login', 'http://localhost:8080/base/power/account/login', 'GET', NULL, '[{arg0=admin}, {arg1=admin}]', 'CommonResult{code=200, message=\'操作成功\', data=Bearer eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1ODA2NjEwMTEsInN1YiI6ImFkbWluIiwiY3JlYXRlZCI6MTU4MDA1NjIxMTA0NX0.8v-GcMs-2CJ8ZCJUSU0ztsOXDZ8X08BO4jGlg4bQtSqrxkLJH7ETRhVLDPIdbf1JdwQtjwtvdRBaNd8RRd5atA}', '2020-01-26 16:30:12', '2020-01-26 16:30:12');
INSERT INTO `log_operation` VALUES (4, 'admin', 1580056262432, 150, '分页：搜索账号登录日志', 'http://localhost:8080', '/base/log/account/login/list', 'http://localhost:8080/base/log/account/login/list', 'GET', NULL, '[{arg0=}, {arg1=11}, {arg2=15}]', 'CommonResult{code=200, message=\'操作成功\', data=CommonPage{pageNum=1, pageSize=0, totalPage=1, total=0, list=[]}}', '2020-01-26 16:31:03', '2020-01-26 16:31:03');
INSERT INTO `log_operation` VALUES (5, 'admin', 1580056276874, 106, '分页：搜索账号登录日志', 'http://localhost:8080', '/base/log/account/login/list', 'http://localhost:8080/base/log/account/login/list', 'GET', NULL, '[{arg0=admin}, {arg1=11}, {arg2=15}]', 'CommonResult{code=200, message=\'操作成功\', data=CommonPage{pageNum=1, pageSize=0, totalPage=1, total=0, list=[]}}', '2020-01-26 16:31:17', '2020-01-26 16:31:17');
INSERT INTO `log_operation` VALUES (6, 'admin', 1580056542464, 319, '分页：搜索账号登录日志', 'http://localhost:8080', '/base/log/account/login/list', 'http://localhost:8080/base/log/account/login/list', 'GET', NULL, '[{arg0=admin}, {arg1=11}, {arg2=15}]', 'CommonResult{code=200, message=\'操作成功\', data=CommonPage{pageNum=11, pageSize=15, totalPage=1, total=4, list=Page{count=true, pageNum=11, pageSize=15, startRow=150, endRow=165, total=4, pages=1, countSignal=false, orderBy=\'null\', orderByOnly=false, reasonable=false, pageSizeZero=false}}}', '2020-01-26 16:35:43', '2020-01-26 16:35:43');
INSERT INTO `log_operation` VALUES (7, 'admin', 1580056563081, 369, '分页：搜索账号登录日志', 'http://localhost:8080', '/base/log/account/login/list', 'http://localhost:8080/base/log/account/login/list', 'GET', NULL, '[{arg0=admin}, {arg1=1}, {arg2=15}]', 'CommonResult{code=200, message=\'操作成功\', data=CommonPage{pageNum=1, pageSize=15, totalPage=1, total=4, list=Page{count=true, pageNum=1, pageSize=15, startRow=0, endRow=15, total=4, pages=1, countSignal=false, orderBy=\'null\', orderByOnly=false, reasonable=false, pageSizeZero=false}}}', '2020-01-26 16:36:03', '2020-01-26 16:36:03');
INSERT INTO `log_operation` VALUES (8, NULL, 1580396623765, 1599, '账号登录', 'http://localhost:8080', '/base/power/account/login', 'http://localhost:8080/base/power/account/login', 'GET', NULL, '[{arg0=admin}, {arg1=admin}]', 'CommonResult{code=200, message=\'操作成功\', data=Bearer eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1ODEwMDE0MjQsInN1YiI6ImFkbWluIiwiY3JlYXRlZCI6MTU4MDM5NjYyNDI2M30.60i-XQQOD9n8OV21IWcS9vXf9DjhA3JyeLepn9ZLJbkJsE-eTiBIEwx0ejxmw4fFtwoggHZqGYDRIOx4aLuVEA}', '2020-01-30 15:03:45', '2020-01-30 15:03:45');
INSERT INTO `log_operation` VALUES (9, 'admin', 1580396661291, 728, '发送注册短信', 'http://localhost:8080', '/base/user/basic/message', 'http://localhost:8080/base/user/basic/message', 'GET', NULL, '{arg0=15812572219}', 'CommonResult{code=500, message=\'操作失败\', data=null}', '2020-01-30 15:04:22', '2020-01-30 15:04:22');
INSERT INTO `log_operation` VALUES (10, 'admin', 1580397273283, 613, '发送注册短信', 'http://localhost:8080', '/base/user/basic/message', 'http://localhost:8080/base/user/basic/message', 'GET', NULL, '{arg0=15812572219}', 'CommonResult{code=500, message=\'操作失败\', data=null}', '2020-01-30 15:14:34', '2020-01-30 15:14:34');
INSERT INTO `log_operation` VALUES (11, 'admin', 1580397352986, 302, '发送注册短信', 'http://localhost:8080', '/base/user/basic/message', 'http://localhost:8080/base/user/basic/message', 'GET', NULL, '{arg0=15812572219}', 'CommonResult{code=500, message=\'操作失败\', data=null}', '2020-01-30 15:15:53', '2020-01-30 15:15:53');
INSERT INTO `log_operation` VALUES (12, 'admin', 1580397438674, 43134, '发送注册短信', 'http://localhost:8080', '/base/user/basic/message', 'http://localhost:8080/base/user/basic/message', 'GET', NULL, '{arg0=15812572219}', 'CommonResult{code=500, message=\'操作失败\', data=null}', '2020-01-30 15:18:02', '2020-01-30 15:18:02');
INSERT INTO `log_operation` VALUES (13, 'admin', 1580397671466, 1043, '发送注册短信', 'http://localhost:8080', '/base/user/basic/message', 'http://localhost:8080/base/user/basic/message', 'GET', NULL, '{arg0=15812572219}', 'CommonResult{code=200, message=\'操作成功\', data=null}', '2020-01-30 15:21:13', '2020-01-30 15:21:13');
INSERT INTO `log_operation` VALUES (14, 'admin', 1580397721242, 83, '验证短信码', 'http://localhost:8080', '/base/user/basic/code', 'http://localhost:8080/base/user/basic/code', 'POST', NULL, '[{arg0=15812572219}, {arg1=760955}]', 'CommonResult{code=200, message=\'操作成功\', data=true}', '2020-01-30 15:22:01', '2020-01-30 15:22:01');
INSERT INTO `log_operation` VALUES (15, 'admin', 1580398672278, 98, '分页：搜索用户信息', 'http://localhost:8080', '/base/user/basic/list', 'http://localhost:8080/base/user/basic/list', 'GET', NULL, '[{arg0=}, {arg1=}, {arg2=0}, {arg3=1}, {arg4=11}]', 'CommonResult{code=200, message=\'操作成功\', data=CommonPage{pageNum=1, pageSize=11, totalPage=0, total=0, list=Page{count=true, pageNum=1, pageSize=11, startRow=0, endRow=11, total=0, pages=0, countSignal=true, orderBy=\'null\', orderByOnly=false, reasonable=false, pageSizeZero=false}}}', '2020-01-30 15:37:52', '2020-01-30 15:37:52');
INSERT INTO `log_operation` VALUES (16, NULL, 1580646823520, 1585, '账号登录', 'http://localhost:8080', '/hospital/power/account/login', 'http://localhost:8080/hospital/power/account/login', 'GET', NULL, '[{arg0=admin}, {arg1=admin}]', 'CommonResult{code=200, message=\'操作成功\', data=Bearer eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1ODEyNTE2MjQsInN1YiI6ImFkbWluIiwiY3JlYXRlZCI6MTU4MDY0NjgyNDA0Mn0.NJ016ZpqngETegO2B0Gu8ufF1TvXnZCltVS5ZFkzmH8MYutMR8cGNYE3ZQ4Lz0ZgmLrn6YX6Dyj6ExHYwORHOQ}', '2020-02-02 12:33:45', '2020-02-02 12:33:45');
INSERT INTO `log_operation` VALUES (17, 'admin', 1580647462980, 280, '修改权限权值', 'http://localhost:8080', '/hospital/power/permission/1', 'http://localhost:8080/hospital/power/permission/1', 'PUT', NULL, 'PowerPermissionParam(type=0, name=权限模块, value=power:all, icon=test, url=/test, pid=0, status=1)', 'CommonResult{code=200, message=\'操作成功\', data=null}', '2020-02-02 12:44:23', '2020-02-02 12:44:23');
INSERT INTO `log_operation` VALUES (18, NULL, 1580735408934, 794, '账号登录', 'http://localhost:8080', '/hospital/power/account/login', 'http://localhost:8080/hospital/power/account/login', 'GET', NULL, '[{arg0=admin}, {arg1=admin}]', 'CommonResult{code=200, message=\'操作成功\', data=Bearer eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1ODEzNDAyMDksInN1YiI6ImFkbWluIiwiY3JlYXRlZCI6MTU4MDczNTQwOTQwMX0.DhmjQVlHLWVHQqHK37TL_C_tZYyW_HcdujlyESuU5Zriyc9yNTdjSzqsFUT4mhcF6_oI9C_ofXFTIK8W-9fZ_g}', '2020-02-03 13:10:10', '2020-02-03 13:10:10');
INSERT INTO `log_operation` VALUES (19, 'admin', 1580735445023, 90, '分页：搜索就诊卡信息', 'http://localhost:8080', '/hospital/user/card/list', 'http://localhost:8080/hospital/user/card/list', 'GET', NULL, '[{arg0=}, {arg1=}, {arg2=0}, {arg3=1}, {arg4=11}]', 'CommonResult{code=200, message=\'操作成功\', data=CommonPage{pageNum=1, pageSize=11, totalPage=0, total=0, list=Page{count=true, pageNum=1, pageSize=11, startRow=0, endRow=11, total=0, pages=0, countSignal=true, orderBy=\'null\', orderByOnly=false, reasonable=false, pageSizeZero=false}}}', '2020-02-03 13:10:45', '2020-02-03 13:10:45');
INSERT INTO `log_operation` VALUES (20, 'admin', 1580735460588, 102, '获取用户就诊卡', 'http://localhost:8080', '/hospital/user/card/list/1', 'http://localhost:8080/hospital/user/card/list/1', 'GET', NULL, NULL, 'CommonResult{code=200, message=\'操作成功\', data=[]}', '2020-02-03 13:11:01', '2020-02-03 13:11:01');
INSERT INTO `log_operation` VALUES (21, 'admin', 1580735990582, 492, '添加就诊卡', 'http://localhost:8080', '/hospital/user/card/1', 'http://localhost:8080/hospital/user/card/1', 'POST', NULL, 'UserMedicalCardParam(type=0, name=陈利建, gender=1, phone=15812572219, identificationNumber=441323199509292312, birthDate=Mon Jan 19 15:05:35 CST 1970)', 'CommonResult{code=200, message=\'操作成功\', data=null}', '2020-02-03 13:19:51', '2020-02-03 13:19:51');
INSERT INTO `log_operation` VALUES (22, 'admin', 1580736008423, 266, '分页：搜索就诊卡信息', 'http://localhost:8080', '/hospital/user/card/list', 'http://localhost:8080/hospital/user/card/list', 'GET', NULL, '[{arg0=}, {arg1=}, {arg2=0}, {arg3=1}, {arg4=11}]', 'CommonResult{code=200, message=\'操作成功\', data=CommonPage{pageNum=1, pageSize=11, totalPage=1, total=1, list=Page{count=true, pageNum=1, pageSize=11, startRow=0, endRow=11, total=1, pages=1, countSignal=false, orderBy=\'null\', orderByOnly=false, reasonable=false, pageSizeZero=false}}}', '2020-02-03 13:20:09', '2020-02-03 13:20:09');
INSERT INTO `log_operation` VALUES (23, 'admin', 1580736027610, 153, '获取用户就诊卡', 'http://localhost:8080', '/hospital/user/card/list/1', 'http://localhost:8080/hospital/user/card/list/1', 'GET', NULL, NULL, 'CommonResult{code=200, message=\'操作成功\', data=[UserMedicalCardDTO(relationId=1, type=0, id=1, name=陈利建, gender=1, phone=15812572219, identificationNumber=441323199509292312, birthDate=Mon Jan 19 15:05:36 CST 1970, gmtCreate=Mon Feb 03 21:19:51 CST 2020, gmtModified=Mon Feb 03 21:19:51 CST 2020)]}', '2020-02-03 13:20:28', '2020-02-03 13:20:28');
INSERT INTO `log_operation` VALUES (24, 'admin', 1580736039718, 96, '检查就诊卡数目是否超过限制', 'http://localhost:8080', '/hospital/user/card/number/1', 'http://localhost:8080/hospital/user/card/number/1', 'GET', NULL, NULL, 'CommonResult{code=200, message=\'操作成功\', data=false}', '2020-02-03 13:20:40', '2020-02-03 13:20:40');
INSERT INTO `log_operation` VALUES (25, 'admin', 1580736193430, 45, '检查就诊卡数目是否超过限制', 'http://localhost:8080', '/hospital/user/card/number/441323199509292312', 'http://localhost:8080/hospital/user/card/number/441323199509292312', 'GET', NULL, NULL, 'CommonResult{code=404, message=\'不存在，该账号编号！\', data=null}', '2020-02-03 13:23:13', '2020-02-03 13:23:13');
INSERT INTO `log_operation` VALUES (26, 'admin', 1580736228757, 47, '检查是否存在，身份证对应就诊卡信息', 'http://localhost:8080', '/hospital/user/card/identification/441323199509292312', 'http://localhost:8080/hospital/user/card/identification/441323199509292312', 'GET', NULL, NULL, 'CommonResult{code=200, message=\'操作成功\', data=true}', '2020-02-03 13:23:49', '2020-02-03 13:23:49');
INSERT INTO `log_operation` VALUES (27, 'admin', 1580736234034, 46, '检查是否存在，身份证对应就诊卡信息', 'http://localhost:8080', '/hospital/user/card/identification/0', 'http://localhost:8080/hospital/user/card/identification/0', 'GET', NULL, NULL, 'CommonResult{code=200, message=\'操作成功\', data=false}', '2020-02-03 13:23:54', '2020-02-03 13:23:54');
INSERT INTO `log_operation` VALUES (28, 'admin', 1580736312318, 237, '修改就诊卡', 'http://localhost:8080', '/hospital/user/card/1', 'http://localhost:8080/hospital/user/card/1', 'PUT', NULL, 'UserMedicalCardUpdateParam(type=1, id=0, name=陈, phone=123, gender=1)', 'CommonResult{code=200, message=\'操作成功\', data=null}', '2020-02-03 13:25:13', '2020-02-03 13:25:13');
INSERT INTO `log_operation` VALUES (29, 'admin', 1580736395263, 152, '删除就诊卡', 'http://localhost:8080', '/hospital/user/card/1', 'http://localhost:8080/hospital/user/card/1', 'DELETE', NULL, NULL, 'CommonResult{code=200, message=\'操作成功\', data=null}', '2020-02-03 13:26:35', '2020-02-03 13:26:35');
INSERT INTO `log_operation` VALUES (30, 'admin', 1580736411183, 57, '检查是否存在，身份证对应就诊卡信息', 'http://localhost:8080', '/hospital/user/card/identification/441323199509292312', 'http://localhost:8080/hospital/user/card/identification/441323199509292312', 'GET', NULL, NULL, 'CommonResult{code=200, message=\'操作成功\', data=true}', '2020-02-03 13:26:51', '2020-02-03 13:26:51');
INSERT INTO `log_operation` VALUES (31, 'admin', 1580736429830, 323, '添加就诊卡', 'http://localhost:8080', '/hospital/user/card/1', 'http://localhost:8080/hospital/user/card/1', 'POST', NULL, 'UserMedicalCardParam(type=0, name=陈利建, gender=1, phone=15812572219, identificationNumber=441323199509292312, birthDate=Mon Jan 19 15:05:35 CST 1970)', 'CommonResult{code=200, message=\'操作成功\', data=null}', '2020-02-03 13:27:10', '2020-02-03 13:27:10');
INSERT INTO `log_operation` VALUES (32, 'admin', 1580736436130, 47, '检查是否存在，身份证对应就诊卡信息', 'http://localhost:8080', '/hospital/user/card/identification/441323199509292312', 'http://localhost:8080/hospital/user/card/identification/441323199509292312', 'GET', NULL, NULL, 'CommonResult{code=200, message=\'操作成功\', data=true}', '2020-02-03 13:27:16', '2020-02-03 13:27:16');
INSERT INTO `log_operation` VALUES (33, 'admin', 1580736440989, 135, '分页：搜索就诊卡信息', 'http://localhost:8080', '/hospital/user/card/list', 'http://localhost:8080/hospital/user/card/list', 'GET', NULL, '[{arg0=}, {arg1=}, {arg2=0}, {arg3=1}, {arg4=11}]', 'CommonResult{code=200, message=\'操作成功\', data=CommonPage{pageNum=1, pageSize=11, totalPage=1, total=1, list=Page{count=true, pageNum=1, pageSize=11, startRow=0, endRow=11, total=1, pages=1, countSignal=false, orderBy=\'null\', orderByOnly=false, reasonable=false, pageSizeZero=false}}}', '2020-02-03 13:27:21', '2020-02-03 13:27:21');
INSERT INTO `log_operation` VALUES (34, 'admin', 1580736449945, 110, '分页：搜索就诊卡信息', 'http://localhost:8080', '/hospital/user/card/list', 'http://localhost:8080/hospital/user/card/list', 'GET', NULL, '[{arg0=}, {arg1=15812572219}, {arg2=0}, {arg3=1}, {arg4=11}]', 'CommonResult{code=200, message=\'操作成功\', data=CommonPage{pageNum=1, pageSize=11, totalPage=1, total=1, list=Page{count=true, pageNum=1, pageSize=11, startRow=0, endRow=11, total=1, pages=1, countSignal=false, orderBy=\'null\', orderByOnly=false, reasonable=false, pageSizeZero=false}}}', '2020-02-03 13:27:30', '2020-02-03 13:27:30');
INSERT INTO `log_operation` VALUES (35, 'admin', 1580736455511, 98, '分页：搜索就诊卡信息', 'http://localhost:8080', '/hospital/user/card/list', 'http://localhost:8080/hospital/user/card/list', 'GET', NULL, '[{arg0=}, {arg1=15}, {arg2=0}, {arg3=1}, {arg4=11}]', 'CommonResult{code=200, message=\'操作成功\', data=CommonPage{pageNum=1, pageSize=11, totalPage=1, total=1, list=Page{count=true, pageNum=1, pageSize=11, startRow=0, endRow=11, total=1, pages=1, countSignal=false, orderBy=\'null\', orderByOnly=false, reasonable=false, pageSizeZero=false}}}', '2020-02-03 13:27:36', '2020-02-03 13:27:36');
INSERT INTO `log_operation` VALUES (36, 'admin', 1580736459484, 97, '分页：搜索就诊卡信息', 'http://localhost:8080', '/hospital/user/card/list', 'http://localhost:8080/hospital/user/card/list', 'GET', NULL, '[{arg0=}, {arg1=15}, {arg2=0}, {arg3=1}, {arg4=11}]', 'CommonResult{code=200, message=\'操作成功\', data=CommonPage{pageNum=1, pageSize=11, totalPage=1, total=1, list=Page{count=true, pageNum=1, pageSize=11, startRow=0, endRow=11, total=1, pages=1, countSignal=false, orderBy=\'null\', orderByOnly=false, reasonable=false, pageSizeZero=false}}}', '2020-02-03 13:27:40', '2020-02-03 13:27:40');
INSERT INTO `log_operation` VALUES (37, 'admin', 1580736470354, 80, '分页：搜索就诊卡信息', 'http://localhost:8080', '/hospital/user/card/list', 'http://localhost:8080/hospital/user/card/list', 'GET', NULL, '[{arg0=}, {arg1=120}, {arg2=0}, {arg3=1}, {arg4=11}]', 'CommonResult{code=200, message=\'操作成功\', data=CommonPage{pageNum=1, pageSize=11, totalPage=0, total=0, list=Page{count=true, pageNum=1, pageSize=11, startRow=0, endRow=11, total=0, pages=0, countSignal=true, orderBy=\'null\', orderByOnly=false, reasonable=false, pageSizeZero=false}}}', '2020-02-03 13:27:50', '2020-02-03 13:27:50');
INSERT INTO `log_operation` VALUES (38, 'admin', 1580907852395, 610, '账号登录', 'http://localhost:8080', '/hospital/power/account/login', 'http://localhost:8080/hospital/power/account/login', 'GET', NULL, '[{arg0=admin}, {arg1=admin}]', 'CommonResult{code=200, message=\'操作成功\', data=Bearer eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1ODE1MTI2NTIsInN1YiI6ImFkbWluIiwiY3JlYXRlZCI6MTU4MDkwNzg1MjY5MX0.NZDJ-lugHkVbUuXkQWn50VkNRd2BqZKKrXb_kS3utVB9KpwhR_4LgR6C_4vQWZLQK4SCpb2BrE5rGNYnZe_bUQ}', '2020-02-05 13:04:13', '2020-02-05 13:04:13');
INSERT INTO `log_operation` VALUES (39, 'admin', 1580908014989, 170, '添加医院信息', 'http://localhost:8080', '/hospital/hospital/info', 'http://localhost:8080/hospital/hospital/info', 'POST', NULL, 'HospitalInfoParam(name=广东省中医院, phone=123, address=广州大学城, description=广东省中医院, picture=http://img1.imgtn.bdimg.com/it/u=1854537091,1525426764&fm=26&gp=0.jpg)', 'CommonResult{code=200, message=\'操作成功\', data=null}', '2020-02-05 13:06:55', '2020-02-05 13:06:55');
INSERT INTO `log_operation` VALUES (40, 'admin', 1580908034891, 128, '分页：搜索医院信息', 'http://localhost:8080', '/hospital/hospital/info/list', 'http://localhost:8080/hospital/hospital/info/list', 'GET', NULL, '[{arg0=}, {arg1=1}, {arg2=11}]', 'CommonResult{code=200, message=\'操作成功\', data=CommonPage{pageNum=1, pageSize=11, totalPage=1, total=1, list=Page{count=true, pageNum=1, pageSize=11, startRow=0, endRow=11, total=1, pages=1, countSignal=false, orderBy=\'null\', orderByOnly=false, reasonable=false, pageSizeZero=false}}}', '2020-02-05 13:07:15', '2020-02-05 13:07:15');
INSERT INTO `log_operation` VALUES (41, 'admin', 1580908045861, 46, '获取医院，所属专科列表', 'http://localhost:8080', '/hospital/hospital/special/list/1', 'http://localhost:8080/hospital/hospital/special/list/1', 'GET', NULL, NULL, 'CommonResult{code=404, message=\'不存在，该医院编号！\', data=null}', '2020-02-05 13:07:26', '2020-02-05 13:07:26');
INSERT INTO `log_operation` VALUES (42, 'admin', 1580908064209, 92, '获取医院，所属专科列表', 'http://localhost:8080', '/hospital/hospital/special/list/1000', 'http://localhost:8080/hospital/hospital/special/list/1000', 'GET', NULL, NULL, 'CommonResult{code=200, message=\'操作成功\', data=null}', '2020-02-05 13:07:44', '2020-02-05 13:07:44');
INSERT INTO `log_operation` VALUES (43, 'admin', 1580908120231, 162, '添加专科信息', 'http://localhost:8080', '/hospital/hospital/special', 'http://localhost:8080/hospital/hospital/special', 'POST', NULL, 'HospitalSpecialParam(name=儿科, description=儿童相关治疗)', 'CommonResult{code=200, message=\'操作成功\', data=null}', '2020-02-05 13:08:40', '2020-02-05 13:08:40');
INSERT INTO `log_operation` VALUES (44, 'admin', 1580908128274, 98, '分页：搜索专科信息', 'http://localhost:8080', '/hospital/hospital/special/list', 'http://localhost:8080/hospital/hospital/special/list', 'GET', NULL, '[{arg0=}, {arg1=1}, {arg2=11}]', 'CommonResult{code=200, message=\'操作成功\', data=CommonPage{pageNum=1, pageSize=11, totalPage=1, total=1, list=Page{count=true, pageNum=1, pageSize=11, startRow=0, endRow=11, total=1, pages=1, countSignal=false, orderBy=\'null\', orderByOnly=false, reasonable=false, pageSizeZero=false}}}', '2020-02-05 13:08:48', '2020-02-05 13:08:48');
INSERT INTO `log_operation` VALUES (45, 'admin', 1580908148565, 281, '添加专科到医院中', 'http://localhost:8080', '/hospital/hospital/special/relation', 'http://localhost:8080/hospital/hospital/special/relation', 'POST', NULL, 'HospitalSpecialRelationParam(hospitalId=1000, specialId=1)', 'CommonResult{code=200, message=\'操作成功\', data=null}', '2020-02-05 13:09:09', '2020-02-05 13:09:09');
INSERT INTO `log_operation` VALUES (46, 'admin', 1580908179434, 521, '添加门诊信息', 'http://localhost:8080', '/hospital/hospital/outpatient', 'http://localhost:8080/hospital/hospital/outpatient', 'POST', NULL, 'HospitalOutpatientParam(name=儿童发烧, specialId=1, hospitalId=1000)', 'CommonResult{code=200, message=\'操作成功\', data=null}', '2020-02-05 13:09:40', '2020-02-05 13:09:40');
INSERT INTO `log_operation` VALUES (47, 'admin', 1580908193437, 190, '添加门诊信息', 'http://localhost:8080', '/hospital/hospital/outpatient', 'http://localhost:8080/hospital/hospital/outpatient', 'POST', NULL, 'HospitalOutpatientParam(name=普通门诊, specialId=1, hospitalId=1000)', 'CommonResult{code=200, message=\'操作成功\', data=null}', '2020-02-05 13:09:54', '2020-02-05 13:09:54');
INSERT INTO `log_operation` VALUES (48, 'admin', 1580908204666, 201, '获取医院，所属专科列表', 'http://localhost:8080', '/hospital/hospital/special/list/1000', 'http://localhost:8080/hospital/hospital/special/list/1000', 'GET', NULL, NULL, 'CommonResult{code=200, message=\'操作成功\', data=[HospitalSpecialOutpatientDTO(special=HospitalSpecial [Hash = 262803287, id=1, name=儿科, description=儿童相关治疗, gmtCreate=Wed Feb 05 21:08:40 CST 2020, gmtModified=Wed Feb 05 21:08:40 CST 2020, serialVersionUID=1], outpatientList=[HospitalOutpatient [Hash = 58774427, id=1, name=儿童发烧, specialId=1, hospitalId=1000, gmtCreate=Wed Feb 05 21:09:40 CST 2020, gmtModified=Wed Feb 05 21:09:40 CST 2020, serialVersionUID=1], HospitalOutpatient [Hash = 892549638, id=2, name=普通门诊, specialId=1, hospitalId=1000, gmtCreate=Wed Feb 05 21:09:54 CST 2020, gmtModified=Wed Feb 05 21:09:54 CST 2020, serialVersionUID=1]])]}', '2020-02-05 13:10:05', '2020-02-05 13:10:05');
INSERT INTO `log_operation` VALUES (49, 'admin', 1580991516556, 647, '账号登录', 'http://localhost:8080', '/hospital/power/account/login', 'http://localhost:8080/hospital/power/account/login', 'GET', NULL, '[{arg0=admin}, {arg1=admin}]', 'CommonResult{code=200, message=\'操作成功\', data=Bearer eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1ODE1OTYzMTYsInN1YiI6ImFkbWluIiwiY3JlYXRlZCI6MTU4MDk5MTUxNjg3NX0.eB43Q20gKkdCa5DcO_SJaLjvz6Q8yK44xKGNIZXsqg6deXACFK0rp0Lo20AOgY15q8F8k_4M3GgKBMg7DHXoag}', '2020-02-06 12:18:37', '2020-02-06 12:18:37');
INSERT INTO `log_operation` VALUES (50, 'admin', 1580996978970, 895, '上传图片，返回url', 'http://localhost:8080', '/hospital/picture/upload', 'http://localhost:8080/hospital/picture/upload', 'POST', NULL, '{arg0=org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@31e88a86}', 'CommonResult{code=200, message=\'操作成功\', data=http://q59ifzu6u.bkt.clouddn.com/FrQra0p5jUWzZgqJrI9rVElQGB51}', '2020-02-06 13:49:40', '2020-02-06 13:49:40');
INSERT INTO `log_operation` VALUES (51, NULL, 1580997306768, 1517, '账号登录', 'http://localhost:8080', '/hospital/power/account/login', 'http://localhost:8080/hospital/power/account/login', 'GET', NULL, '[{arg0=admin}, {arg1=admin}]', 'CommonResult{code=200, message=\'操作成功\', data=Bearer eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1ODE2MDIxMDcsInN1YiI6ImFkbWluIiwiY3JlYXRlZCI6MTU4MDk5NzMwNzI4NX0.yfXH5H6CLhLoENlDp0jW_NcjVU-JgbGwKI2uWhIJM7ZRZe_uZywFpN9M8E-JDJxkUjrWfSWF1YwRBsk6OlSrMQ}', '2020-02-06 13:55:08', '2020-02-06 13:55:08');

-- ----------------------------
-- Table structure for power_account
-- ----------------------------
DROP TABLE IF EXISTS `power_account`;
CREATE TABLE `power_account`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录账号 唯一',
  `password` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录密码 使用md5加密',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '账号状态 1：正常，0：锁定',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `power_account_name_uindex`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '账号信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of power_account
-- ----------------------------
INSERT INTO `power_account` VALUES (1, 'admin', '$2a$10$TULStsgoxc/40LWq97nKLe//kNPusCpbP4jJAbjCVePp3nlblUfJ.', 1, '2020-02-06 13:55:08', '2020-01-25 08:04:14', '2020-01-25 08:04:14');

-- ----------------------------
-- Table structure for power_account_permission_relation
-- ----------------------------
DROP TABLE IF EXISTS `power_account_permission_relation`;
CREATE TABLE `power_account_permission_relation`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '账号特别权限关系编号',
  `account_id` bigint(20) NOT NULL COMMENT '账号编号',
  `permission_id` bigint(20) NOT NULL COMMENT '权限编号',
  `type` int(11) NOT NULL DEFAULT 1 COMMENT '关系类型 -1：去除权限，1：增加权限',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `power_account_permission_relation_power_account_id_fk`(`account_id`) USING BTREE,
  INDEX `power_account_permission_relation_power_permission_id_fk`(`permission_id`) USING BTREE,
  CONSTRAINT `power_account_permission_relation_power_account_id_fk` FOREIGN KEY (`account_id`) REFERENCES `power_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `power_account_permission_relation_power_permission_id_fk` FOREIGN KEY (`permission_id`) REFERENCES `power_permission` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '账号特别权限关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for power_account_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `power_account_role_relation`;
CREATE TABLE `power_account_role_relation`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '账号角色关系编号',
  `account_id` bigint(20) NOT NULL COMMENT '账号编号',
  `role_id` bigint(20) NOT NULL COMMENT '角色编号',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `power_account_role_relation_power_role_id_fk`(`role_id`) USING BTREE,
  INDEX `power_account_role_relation_power_account_id_fk`(`account_id`) USING BTREE,
  CONSTRAINT `power_account_role_relation_power_account_id_fk` FOREIGN KEY (`account_id`) REFERENCES `power_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `power_account_role_relation_power_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `power_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '账号角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of power_account_role_relation
-- ----------------------------
INSERT INTO `power_account_role_relation` VALUES (1, 1, 1, '2020-01-26 18:21:18', '2020-01-26 18:21:18');

-- ----------------------------
-- Table structure for power_permission
-- ----------------------------
DROP TABLE IF EXISTS `power_permission`;
CREATE TABLE `power_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限编号',
  `type` int(11) NOT NULL COMMENT '权限类型 0->目录；1->菜单；2->按钮（接口绑定权限）',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限名称',
  `value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限值',
  `icon` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端图标',
  `url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端路径',
  `pid` bigint(20) NULL DEFAULT NULL COMMENT '父权限编号',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '权限状态 1：开启，0：禁用',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `power_permission_value_uindex`(`value`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 403 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of power_permission
-- ----------------------------
INSERT INTO `power_permission` VALUES (1, 0, '权限模块', 'power:all', NULL, NULL, 0, 1, '2020-01-26 18:17:26', '2020-02-02 12:44:23');
INSERT INTO `power_permission` VALUES (2, 0, '日志模块', 'log:all', NULL, NULL, 0, 1, '2020-01-26 18:17:26', '2020-01-26 18:17:26');
INSERT INTO `power_permission` VALUES (3, 0, '用户模块', 'user:all', NULL, NULL, 0, 1, '2020-01-30 23:34:51', '2020-01-30 23:34:51');
INSERT INTO `power_permission` VALUES (4, 0, '医院模块', 'hospital:all', NULL, NULL, 0, 1, '2020-02-04 18:44:34', '2020-02-04 18:44:34');
INSERT INTO `power_permission` VALUES (10, 1, '权限角色', 'power:role:all', NULL, NULL, 1, 1, '2020-01-26 18:17:26', '2020-01-26 18:17:26');
INSERT INTO `power_permission` VALUES (11, 1, '权限账号', 'power:account:all', NULL, NULL, 1, 1, '2020-01-26 18:17:26', '2020-01-26 18:17:26');
INSERT INTO `power_permission` VALUES (12, 1, '权限权值', 'power:permission:all', NULL, NULL, 1, 1, '2020-01-26 18:17:26', '2020-01-26 18:17:26');
INSERT INTO `power_permission` VALUES (20, 1, '操作日志', 'log:operation:all', NULL, NULL, 2, 1, '2020-01-26 18:17:26', '2020-01-26 18:17:26');
INSERT INTO `power_permission` VALUES (21, 1, '登录日志', 'log:account:login:all', NULL, NULL, 2, 1, '2020-01-26 18:17:26', '2020-01-26 18:17:26');
INSERT INTO `power_permission` VALUES (30, 1, '用户信息', 'user:basic:all', NULL, NULL, 3, 1, '2020-01-30 23:34:52', '2020-01-30 23:34:52');
INSERT INTO `power_permission` VALUES (31, 1, '就诊卡信息', 'user:card:all', NULL, NULL, 3, 1, '2020-02-04 18:44:35', '2020-02-04 18:44:35');
INSERT INTO `power_permission` VALUES (40, 1, '医院信息', 'hospital:info:all', NULL, NULL, 4, 1, '2020-02-04 18:44:35', '2020-02-04 18:44:35');
INSERT INTO `power_permission` VALUES (100, 2, '获取当前账号信息', 'power:account:info:get', NULL, NULL, 10, 1, '2020-01-26 18:17:27', '2020-01-26 18:17:27');
INSERT INTO `power_permission` VALUES (101, 2, '更新账号状态', 'power:account:status:put', NULL, NULL, 10, 1, '2020-01-26 18:17:27', '2020-01-26 18:17:27');
INSERT INTO `power_permission` VALUES (102, 2, '更新账号分配角色', 'power:account:role:post', NULL, NULL, 10, 1, '2020-01-26 18:17:27', '2020-01-26 18:17:27');
INSERT INTO `power_permission` VALUES (103, 2, '获取账号所有权限', 'power:account:permission:get', NULL, NULL, 10, 1, '2020-01-26 18:17:27', '2020-01-26 18:17:27');
INSERT INTO `power_permission` VALUES (104, 2, '管理账号注册', 'power:account:admin:register:post', NULL, NULL, 10, 1, '2020-01-29 21:23:23', '2020-01-29 21:23:23');
INSERT INTO `power_permission` VALUES (120, 2, '分页：搜索权限角色', 'power:role:list:get', NULL, NULL, 11, 1, '2020-01-26 18:17:27', '2020-01-26 18:17:27');
INSERT INTO `power_permission` VALUES (121, 2, '增加权限角色', 'power:role:post', NULL, NULL, 11, 1, '2020-01-26 18:17:27', '2020-01-26 18:17:27');
INSERT INTO `power_permission` VALUES (122, 2, '修改权限角色', 'power:role:put', NULL, NULL, 11, 1, '2020-01-26 18:17:27', '2020-01-26 18:17:27');
INSERT INTO `power_permission` VALUES (123, 2, '获取角色所有权限', 'power:role:permission:get', NULL, NULL, 11, 1, '2020-01-26 18:17:28', '2020-01-26 18:17:28');
INSERT INTO `power_permission` VALUES (124, 2, '更新角色所有权限', 'power:role:permission:put', NULL, NULL, 11, 1, '2020-01-26 18:17:28', '2020-01-26 18:17:28');
INSERT INTO `power_permission` VALUES (140, 2, '分页：搜索权限权值', 'power:permission:list:get', NULL, NULL, 12, 1, '2020-01-26 18:17:28', '2020-01-26 18:17:28');
INSERT INTO `power_permission` VALUES (141, 2, '增加权限权值', 'power:permission:post', NULL, NULL, 12, 1, '2020-01-26 18:17:28', '2020-01-26 18:17:28');
INSERT INTO `power_permission` VALUES (142, 2, '修改权限权值', 'power:permission:put', NULL, NULL, 12, 1, '2020-01-26 18:17:28', '2020-01-26 18:17:28');
INSERT INTO `power_permission` VALUES (143, 2, '获取所有权限权值', 'power:permission:tree:get', NULL, NULL, 12, 1, '2020-01-26 18:17:28', '2020-01-26 18:17:28');
INSERT INTO `power_permission` VALUES (144, 2, '获取相应父级权限', 'power:permission:list:type:get', NULL, NULL, 12, 1, '2020-01-26 18:17:28', '2020-01-26 18:17:28');
INSERT INTO `power_permission` VALUES (200, 2, '分页：搜索账号登录日志', 'log:account:login:list:get', NULL, NULL, 21, 1, '2020-01-26 18:17:29', '2020-01-26 18:17:29');
INSERT INTO `power_permission` VALUES (220, 2, '分页：搜索账号操作日志', 'log:opreation:list:get', NULL, NULL, 20, 1, '2020-01-26 18:17:29', '2020-01-26 18:17:29');
INSERT INTO `power_permission` VALUES (300, 2, '分页：搜索用户信息', 'user:basic:list:get', NULL, NULL, 30, 1, '2020-01-30 23:34:54', '2020-01-30 23:34:54');
INSERT INTO `power_permission` VALUES (301, 2, '删除用户基础信息', 'user:basic:delete', NULL, NULL, 30, 1, '2020-01-30 23:34:54', '2020-01-30 23:34:54');
INSERT INTO `power_permission` VALUES (320, 2, '分页：搜索就诊卡信息', 'user:card:list:get', NULL, NULL, 31, 1, '2020-02-04 18:44:37', '2020-02-04 18:44:37');
INSERT INTO `power_permission` VALUES (400, 2, '删除医院信息', 'hospital:info:delete', NULL, NULL, 40, 1, '2020-02-04 18:44:38', '2020-02-04 18:44:38');
INSERT INTO `power_permission` VALUES (401, 2, '更新医院信息', 'hospital:info:update', NULL, NULL, 40, 1, '2020-02-04 18:44:38', '2020-02-04 18:44:38');
INSERT INTO `power_permission` VALUES (402, 2, '添加医院信息', 'hospital:info:post', NULL, NULL, 40, 1, '2020-02-04 18:44:38', '2020-02-04 18:44:38');

-- ----------------------------
-- Table structure for power_role
-- ----------------------------
DROP TABLE IF EXISTS `power_role`;
CREATE TABLE `power_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '英文名称',
  `chinese_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '中文名称',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '角色状态 1：启用，0：禁用',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `power_role_name_uindex`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of power_role
-- ----------------------------
INSERT INTO `power_role` VALUES (1, 'ROLE_admin', '系统管理员', 1, '2020-01-26 18:19:40', '2020-01-26 18:19:40');
INSERT INTO `power_role` VALUES (2, 'ROLE_user', '普通用户', 1, '2020-01-26 18:20:06', '2020-01-26 18:20:06');

-- ----------------------------
-- Table structure for power_role_permission_relation
-- ----------------------------
DROP TABLE IF EXISTS `power_role_permission_relation`;
CREATE TABLE `power_role_permission_relation`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色权限关系编号',
  `role_id` bigint(20) NOT NULL COMMENT '角色编号',
  `permission_id` bigint(20) NOT NULL COMMENT '权限编号',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `power_role_permission_relation_power_permission_id_fk`(`permission_id`) USING BTREE,
  INDEX `power_role_permission_relation_power_role_id_fk`(`role_id`) USING BTREE,
  CONSTRAINT `power_role_permission_relation_power_permission_id_fk` FOREIGN KEY (`permission_id`) REFERENCES `power_permission` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `power_role_permission_relation_power_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `power_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 87 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of power_role_permission_relation
-- ----------------------------
INSERT INTO `power_role_permission_relation` VALUES (53, 1, 1, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (54, 1, 2, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (55, 1, 10, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (56, 1, 11, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (57, 1, 12, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (58, 1, 20, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (59, 1, 21, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (60, 1, 100, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (61, 1, 101, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (62, 1, 102, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (63, 1, 103, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (64, 1, 120, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (65, 1, 121, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (66, 1, 122, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (67, 1, 123, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (68, 1, 124, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (69, 1, 140, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (70, 1, 141, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (71, 1, 142, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (72, 1, 143, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (73, 1, 144, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (74, 1, 200, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (75, 1, 220, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (76, 1, 3, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (77, 1, 30, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (78, 1, 300, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (79, 1, 301, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (80, 1, 320, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (81, 1, 4, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (82, 1, 400, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (83, 1, 401, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (84, 1, 402, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (85, 1, 31, '2020-02-04 18:45:24', '2020-02-04 18:45:24');
INSERT INTO `power_role_permission_relation` VALUES (86, 1, 40, '2020-02-04 18:45:24', '2020-02-04 18:45:24');

-- ----------------------------
-- Table structure for user_basic_info
-- ----------------------------
DROP TABLE IF EXISTS `user_basic_info`;
CREATE TABLE `user_basic_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `avatar_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户头像',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_basic_info_phone_uindex`(`phone`) USING BTREE,
  CONSTRAINT `user_basic_info_power_account_name_fk` FOREIGN KEY (`phone`) REFERENCES `power_account` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户基础信息表 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_medical_card
-- ----------------------------
DROP TABLE IF EXISTS `user_medical_card`;
CREATE TABLE `user_medical_card`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '就诊卡号',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `gender` int(11) NOT NULL DEFAULT 1 COMMENT '性别 男：1，女：2',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `identification_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '证件号',
  `birth_date` datetime(0) NOT NULL COMMENT '出生日期',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_medical_card_identification_number_uindex`(`identification_number`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7000001 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户就诊卡信息表 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_medical_card
-- ----------------------------
INSERT INTO `user_medical_card` VALUES (7000000, '陈利建', 1, '15812572219', '441323199509292312', '1970-01-19 07:05:36', '2020-02-03 13:19:51', '2020-02-03 13:19:51');

-- ----------------------------
-- Table structure for user_medical_card_relation
-- ----------------------------
DROP TABLE IF EXISTS `user_medical_card_relation`;
CREATE TABLE `user_medical_card_relation`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '关系编号',
  `type` int(11) NOT NULL COMMENT '关系类型 0：本人，1：父母，2：兄弟/姐妹，3：伴侣，4：子女，5：同事/朋友，6：其他',
  `account_id` bigint(20) NOT NULL COMMENT '账号编号',
  `card_id` bigint(20) NOT NULL COMMENT '就诊卡编号',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_medical_card_relation_power_account_id_fk`(`account_id`) USING BTREE,
  INDEX `user_medical_card_relation_user_medical_card_id_fk`(`card_id`) USING BTREE,
  CONSTRAINT `user_medical_card_relation_power_account_id_fk` FOREIGN KEY (`account_id`) REFERENCES `power_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_medical_card_relation_user_medical_card_id_fk` FOREIGN KEY (`card_id`) REFERENCES `user_medical_card` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户就诊卡关系表 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_medical_card_relation
-- ----------------------------
INSERT INTO `user_medical_card_relation` VALUES (2, 0, 1, 7000000, '2020-02-03 13:27:10', '2020-02-03 13:27:10');

SET FOREIGN_KEY_CHECKS = 1;
