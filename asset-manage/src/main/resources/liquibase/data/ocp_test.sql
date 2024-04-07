/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80036
 Source Host           : localhost:3306
 Source Schema         : asset_manage

 Target Server Type    : MySQL
 Target Server Version : 80036
 File Encoding         : 65001

 Date: 07/04/2024 15:30:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ocp_test
-- ----------------------------
DROP TABLE IF EXISTS `ocp_test`;
CREATE TABLE `ocp_test`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `test` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '测试',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
