/*
 Navicat Premium Data Transfer

 Source Server         : yang
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : cdb-5vqvsr3e.bj.tencentcdb.com:10172
 Source Schema         : hospital

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 01/02/2020 17:03:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '账号登录记录表 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log_account_login
-- ----------------------------
INSERT INTO `log_account_login` VALUES (1, 1, 'admin', '0:0:0:0:0:0:0:1', '2020-01-25 13:27:42', '2020-01-25 13:27:42');
INSERT INTO `log_account_login` VALUES (2, 1, 'admin', '0:0:0:0:0:0:0:1', '2020-01-25 13:28:36', '2020-01-25 13:28:36');
INSERT INTO `log_account_login` VALUES (3, 1, 'admin', '0:0:0:0:0:0:0:1', '2020-01-26 10:22:25', '2020-01-26 10:22:25');
INSERT INTO `log_account_login` VALUES (4, 1, 'admin', '0:0:0:0:0:0:0:1', '2020-01-26 16:30:12', '2020-01-26 16:30:12');
INSERT INTO `log_account_login` VALUES (5, 1, 'admin', '0:0:0:0:0:0:0:1', '2020-01-30 15:03:45', '2020-01-30 15:03:45');

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
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户操作记录表 ' ROW_FORMAT = Dynamic;

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
INSERT INTO `power_account` VALUES (1, 'admin', '$2a$10$TULStsgoxc/40LWq97nKLe//kNPusCpbP4jJAbjCVePp3nlblUfJ.', 1, '2020-01-30 15:03:45', '2020-01-25 08:04:14', '2020-01-25 08:04:14');

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
) ENGINE = InnoDB AUTO_INCREMENT = 302 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of power_permission
-- ----------------------------
INSERT INTO `power_permission` VALUES (1, 0, '权限模块', 'power:all', NULL, NULL, 0, 1, '2020-01-26 18:17:26', '2020-01-26 18:17:26');
INSERT INTO `power_permission` VALUES (2, 0, '日志模块', 'log:all', NULL, NULL, 0, 1, '2020-01-26 18:17:26', '2020-01-26 18:17:26');
INSERT INTO `power_permission` VALUES (3, 0, '用户模块', 'user:all', NULL, NULL, 0, 1, '2020-01-30 23:34:51', '2020-01-30 23:34:51');
INSERT INTO `power_permission` VALUES (10, 1, '权限角色', 'power:role:all', NULL, NULL, 1, 1, '2020-01-26 18:17:26', '2020-01-26 18:17:26');
INSERT INTO `power_permission` VALUES (11, 1, '权限账号', 'power:account:all', NULL, NULL, 1, 1, '2020-01-26 18:17:26', '2020-01-26 18:17:26');
INSERT INTO `power_permission` VALUES (12, 1, '权限权值', 'power:permission:all', NULL, NULL, 1, 1, '2020-01-26 18:17:26', '2020-01-26 18:17:26');
INSERT INTO `power_permission` VALUES (20, 1, '操作日志', 'log:operation:all', NULL, NULL, 2, 1, '2020-01-26 18:17:26', '2020-01-26 18:17:26');
INSERT INTO `power_permission` VALUES (21, 1, '登录日志', 'log:account:login:all', NULL, NULL, 2, 1, '2020-01-26 18:17:26', '2020-01-26 18:17:26');
INSERT INTO `power_permission` VALUES (30, 1, '用户信息', 'user:basic:all', NULL, NULL, 3, 1, '2020-01-30 23:34:52', '2020-01-30 23:34:52');
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
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of power_role_permission_relation
-- ----------------------------
INSERT INTO `power_role_permission_relation` VALUES (26, 1, 1, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (27, 1, 2, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (28, 1, 10, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (29, 1, 11, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (30, 1, 12, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (31, 1, 20, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (32, 1, 21, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (33, 1, 100, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (34, 1, 101, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (35, 1, 102, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (36, 1, 103, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (37, 1, 120, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (38, 1, 121, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (39, 1, 122, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (40, 1, 123, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (41, 1, 124, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (42, 1, 140, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (43, 1, 141, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (44, 1, 142, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (45, 1, 143, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (46, 1, 144, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (47, 1, 200, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (48, 1, 220, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (49, 1, 3, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (50, 1, 30, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (51, 1, 300, '2020-01-30 23:36:54', '2020-01-30 23:36:54');
INSERT INTO `power_role_permission_relation` VALUES (52, 1, 301, '2020-01-30 23:36:54', '2020-01-30 23:36:54');

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

SET FOREIGN_KEY_CHECKS = 1;
