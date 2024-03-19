/*
 Navicat Premium Data Transfer

 Source Server         : wyd
 Source Server Type    : MySQL
 Source Server Version : 50651
 Source Host           : 192.168.30.43:3306
 Source Schema         : sa_token_test

 Target Server Type    : MySQL
 Target Server Version : 50651
 File Encoding         : 65001

 Date: 02/06/2023 17:44:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for my_user
-- ----------------------------
DROP TABLE IF EXISTS `my_user`;
CREATE TABLE `my_user`  (
  `id` bigint(20) NOT NULL COMMENT '用户id',
  `user_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `user_password` varchar(15) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户密码',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `role_id` bigint(20) NULL DEFAULT 0 COMMENT '角色id',
  `enable` tinyint(4) NULL DEFAULT 1 COMMENT '可用标识符，用于封禁，只有登录接口会用到，1为可用，0为不可用',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of my_user
-- ----------------------------
INSERT INTO `my_user` VALUES (1, 'wyd', '123456', 25, 1, 1);
INSERT INTO `my_user` VALUES (2, 'yxy', '123456', 24, 0, 1);
INSERT INTO `my_user` VALUES (3, 'xxx', '123456', 22, 2, 1);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `role_id` bigint(10) NULL DEFAULT NULL COMMENT '角色id',
  `user_id` bigint(20) NULL DEFAULT -1 COMMENT '用户id',
  `business` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '业务类型',
  `permissions` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '操作权限集合',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `create_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_id` bigint(20) NULL DEFAULT NULL COMMENT '更新人id',
  `update_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '不同角色对不同业务有默认的权限，如果对某个账户设置了权限，则按照账户权限为准' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, 1, 1, '*', '*', '2023-05-29 16:17:28', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (2, 0, -1, 'article', 'crud', '2023-05-29 16:44:06', 1, 'wyd', NULL, NULL, NULL);
INSERT INTO `permission` VALUES (3, 2, -1, 'goods', 'crud', '2023-05-30 09:14:17', 1, 'wyd', NULL, NULL, NULL);
INSERT INTO `permission` VALUES (4, 0, -1, 'goods', 'r', '2023-05-30 09:14:41', 1, 'wyd', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色名',
  `role_creator` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '角色创建者',
  `role_creator_id` bigint(20) NULL DEFAULT NULL COMMENT '角色创建者id',
  `role_create_time` datetime(0) NULL DEFAULT NULL COMMENT '角色创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (0, 'common_user', 'wyd', 1, '2023-05-29 16:46:23');
INSERT INTO `role` VALUES (1, 'admin', NULL, NULL, '2023-05-29 11:57:53');
INSERT INTO `role` VALUES (2, 'under_admin', 'wyd', 1, '2023-05-30 09:13:11');

SET FOREIGN_KEY_CHECKS = 1;
