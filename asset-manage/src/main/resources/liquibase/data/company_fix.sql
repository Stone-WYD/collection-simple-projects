/*
 Navicat Premium Data Transfer

 Source Server         : 43
 Source Server Type    : MySQL
 Source Server Version : 50651
 Source Host           : 192.168.30.43:3306
 Source Schema         : sa_token_test

 Target Server Type    : MySQL
 Target Server Version : 50651
 File Encoding         : 65001

 Date: 15/04/2024 10:48:32
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
-- Table structure for wb_info
-- ----------------------------
DROP TABLE IF EXISTS `wb_info`;
CREATE TABLE `wb_info`  (
                            `id` bigint NOT NULL,
                            `asset_id` int NOT NULL COMMENT 'asset表的id',
                            `receive_id` bigint NULL DEFAULT NULL COMMENT '接收人id',
                            `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                            `receive_time` datetime NULL DEFAULT NULL COMMENT '接收时间',
                            `back` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
                            `extend` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '预留字段',
                            `info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '维保记录',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

