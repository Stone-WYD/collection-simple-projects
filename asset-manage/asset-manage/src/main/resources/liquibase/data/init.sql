/*
 Navicat Premium Data Transfer

 Source Server         : 28
 Source Server Type    : MySQL
 Source Server Version : 100119
 Source Host           : 192.168.20.28:3306
 Source Schema         : asset_manage

 Target Server Type    : MySQL
 Target Server Version : 100119
 File Encoding         : 65001

 Date: 19/03/2024 10:50:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for asset
-- ----------------------------
DROP TABLE IF EXISTS `asset`;
CREATE TABLE `asset`  (
  `id` int NOT NULL COMMENT '主键',
  `project_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `custom_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `project_status` tinyint NULL DEFAULT NULL COMMENT '项目状态：1. 在保，0. 过保',
  `begin_time` datetime NULL DEFAULT NULL COMMENT '项目开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '项目结束时间',
  `accounts_receivable` tinyint NULL DEFAULT NULL COMMENT '是否存在应收账款：1.存在，0.不存在',
  `amount_receivable` decimal(20, 2) NULL DEFAULT NULL COMMENT '应收账款金额 ',
  `amount_contract` decimal(20, 2) NULL DEFAULT NULL COMMENT '合同额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
