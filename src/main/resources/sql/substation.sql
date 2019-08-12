/*
 Navicat Premium Data Transfer

 Source Server         : 218.90.135.54
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : 218.90.135.54:3305
 Source Schema         : substation

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 12/08/2019 11:49:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for alarm_info
-- ----------------------------
DROP TABLE IF EXISTS `alarm_info`;
CREATE TABLE `alarm_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `substation_id` int(20) NOT NULL COMMENT '所属变电所id',
  `number` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '报警序号',
  `area_id` int(11) NULL DEFAULT NULL COMMENT '报警区域id',
  `area_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报警区域名称',
  `level` int(2) NULL DEFAULT NULL COMMENT '报警等级（1：低级 2：中级 3：高级）',
  `status` int(2) NULL DEFAULT NULL COMMENT '当前状态（0：未恢复 1：已恢复）',
  `type` int(2) NULL DEFAULT NULL COMMENT '报警类型（1：火警）',
  `detail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报警描述',
  `confirm_status` int(2) NULL DEFAULT NULL COMMENT '确认状态',
  `confirm_time` timestamp(0) NULL DEFAULT NULL COMMENT '确认时间',
  `recovery_time` timestamp(0) NULL DEFAULT NULL COMMENT '恢复时间',
  `delete_flag` int(2) NULL DEFAULT 0 COMMENT '删除标志位 ( 0：未删除 1： 已删除)',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `create_user` int(255) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统报警信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for area_info
-- ----------------------------
DROP TABLE IF EXISTS `area_info`;
CREATE TABLE `area_info`  (
  `id` bigint(20) NOT NULL,
  `area_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区域名称',
  `substation_id` int(20) NOT NULL COMMENT '所属变电所id',
  `delete_flag` int(2) NULL DEFAULT 0 COMMENT '删除标志位（0:未删除 1：已删除）',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `create_user` int(20) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` int(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for camera_info
-- ----------------------------
DROP TABLE IF EXISTS `camera_info`;
CREATE TABLE `camera_info`  (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `channel_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通道名称',
  `substation_id` int(20) NULL DEFAULT NULL COMMENT '所属变电所id',
  `ip_addr` char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备地址',
  `port_num` int(10) NULL DEFAULT 37777 COMMENT '端口号',
  `mac_addr` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'mac',
  `device_type` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备类型',
  `device_brand` char(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '品牌',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `create_user` int(20) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `update_user` int(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for control_condition
-- ----------------------------
DROP TABLE IF EXISTS `control_condition`;
CREATE TABLE `control_condition`  (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `condition_id` int(20) NULL DEFAULT NULL COMMENT '条件id',
  `control_id` int(20) NULL DEFAULT NULL COMMENT '要执行的联控控制操作id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_condition`(`condition_id`) USING BTREE,
  INDEX `index_control`(`control_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '设备控制操作与执行条件中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_condition
-- ----------------------------
DROP TABLE IF EXISTS `device_condition`;
CREATE TABLE `device_condition`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(20) NULL DEFAULT NULL COMMENT '联控类型\r\n1：定时任务触发\r\n2：温度高于max值触发\r\n3：温度低于min值触发\r\n4： 手动执行\r\n5：湿度高于max值触发\r\n6：湿度低于min值触发\r\n7：其他单个条件',
  `description` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联控的描述',
  `timer` timestamp(0) NULL DEFAULT NULL COMMENT '定时任务的执行时间',
  `execution_user` int(20) NULL DEFAULT NULL COMMENT '任务的手动执行人id',
  `execution_time` timestamp(0) NULL DEFAULT NULL COMMENT '任务的手动执行时间',
  `temperature_max` decimal(6, 2) NULL DEFAULT NULL COMMENT '温度的最高阈值\r\n（最多5位，保留最多2位小数）\r\n(低于该值为正常区域)',
  `temperature_min` decimal(6, 2) NULL DEFAULT NULL COMMENT '温度的最低阈值\r\n(高于该值为正常区域)',
  `humidity_max` decimal(5, 2) NULL DEFAULT NULL COMMENT '湿度的最高阈值(低于该值为正常区域)',
  `humidity_min` decimal(5, 2) NULL DEFAULT NULL COMMENT '湿度的最低阈值(高于该值为正常区域)',
  `others` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '其他条件',
  `delete_flag` int(2) NULL DEFAULT 0 COMMENT '删除标志位 0：未删除 1：已删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `create_user` int(11) NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `update_user` int(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '联控条件' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_control
-- ----------------------------
DROP TABLE IF EXISTS `device_control`;
CREATE TABLE `device_control`  (
  `id` int(20) NOT NULL,
  `number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备联控序号',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备联控名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联控操作描述',
  `delete_flag` int(2) NULL DEFAULT NULL COMMENT '删除标志位  0：未删除 1：已删除',
  `condition_flag` int(2) NULL DEFAULT NULL COMMENT '是否需要满足所有条件执行控制，\r\n多个条件时：\r\n（control_condition表有多条同一control_id的记录）\r\n   0：不是（满足多个条件中一个）\r\n   1：是（满足多条件的所有要求）\r\n单个条件时：\r\n    该字段值为1',
  `create_time` timestamp(0) NULL DEFAULT NULL,
  `create_user` int(20) NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT NULL,
  `update_user` int(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '设备联控' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_info
-- ----------------------------
DROP TABLE IF EXISTS `device_info`;
CREATE TABLE `device_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `substation_id` int(20) NOT NULL COMMENT '设备所属变电所id',
  `area_id` int(20) NOT NULL COMMENT '设备所属区域',
  `area_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属区域名称',
  `number` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '序号',
  `type` int(2) NULL DEFAULT NULL COMMENT '设备类型（\r\n设备描述 \r\n1：变电所消防系统主机 \r\n2：高压区烟感器\r\n3：高压区灭火器\r\n4：低压区灭火器\r\n5：高压区手动报警装置\r\n6：低压区手动报警装置\r\n7：高压区温感器\r\n8： 低压区温感器\r\n）',
  `status` int(2) NULL DEFAULT NULL COMMENT '设备状态（ 0：异常 1：正常）',
  `delete_flag` int(2) NULL DEFAULT 0 COMMENT '删除标志位（0：未删除 1：已删除）',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `create_user` int(11) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_log
-- ----------------------------
DROP TABLE IF EXISTS `device_log`;
CREATE TABLE `device_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(0) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志序号',
  `execution_time` timestamp(0) NULL DEFAULT NULL COMMENT '执行时间',
  `detail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志详情',
  `substation_id` int(20) NULL DEFAULT NULL COMMENT '变电所id',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `create_user` int(20) NULL DEFAULT NULL COMMENT '系统自动生成信息时，使用0作为该项值',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `update_user` int(20) NULL DEFAULT NULL COMMENT '系统自动生成信息时，使用0作为该项值',
  `delete_flag` int(2) NULL DEFAULT 0 COMMENT '数据删除标志位 0：未删除 1：已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '设备执行日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inspection
-- ----------------------------
DROP TABLE IF EXISTS `inspection`;
CREATE TABLE `inspection`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `substation_id` int(20) NULL DEFAULT NULL COMMENT '所属变电所',
  `detail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '巡检详情',
  `execution_time` timestamp(0) NULL DEFAULT NULL COMMENT '巡检任务的执行时间',
  `execution_user` int(11) NULL DEFAULT NULL COMMENT '巡检任务的执行人',
  `delte_flag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '删除标志位（0:未删除  1：已删除）',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `create_user` int(20) NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT NULL,
  `update_user` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role_auth
-- ----------------------------
DROP TABLE IF EXISTS `role_auth`;
CREATE TABLE `role_auth`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` int(20) NULL DEFAULT NULL,
  `auth_id` int(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for substation_info
-- ----------------------------
DROP TABLE IF EXISTS `substation_info`;
CREATE TABLE `substation_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '变电所名称',
  `flag` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '变电所标志',
  `descrption` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '变电所介绍',
  `delete_flag` int(2) NULL DEFAULT 0 COMMENT '删除标志位（0:未删除 1：已删除）',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `create_user` int(20) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` int(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_auth
-- ----------------------------
DROP TABLE IF EXISTS `sys_auth`;
CREATE TABLE `sys_auth`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限字段',
  `delete_flag` int(2) NULL DEFAULT 0 COMMENT '删除标志位（0：未删除  1：已删除）',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `create_user` int(20) NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `update_user` int(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `delete_flag` int(2) NULL DEFAULT 0 COMMENT '删除标志位（0:未删除 1：已删除）',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `create_user` int(20) NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `update_user` int(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `substation_id` int(20) NULL DEFAULT NULL COMMENT '所属变电所id',
  `real_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录姓名',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录密码',
  `phone` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系号码',
  `delete_flag` int(2) NULL DEFAULT 0 COMMENT '删除标志位（0:未删除 1：已删除）',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `create_user` int(20) NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT NULL,
  `update_user` int(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(20) NULL DEFAULT NULL,
  `role_id` int(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
