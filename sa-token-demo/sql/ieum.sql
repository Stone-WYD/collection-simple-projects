/*
 Navicat Premium Data Transfer

 Source Server         : 本地连接
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : localhost:3306
 Source Schema         : sa_token_test

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 24/06/2023 18:13:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ieum
-- ----------------------------
DROP TABLE IF EXISTS `ieum`;
CREATE TABLE `ieum`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `test_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '物品名称',
  `test_ieum` int NULL DEFAULT NULL COMMENT '枚举字典值，1.王玉东，2.叶絮依',
  `num` int NULL DEFAULT NULL COMMENT '物品拥有数量',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '物品单价',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ieum
-- ----------------------------
INSERT INTO `ieum` VALUES (1, '台式机', 1, 1, 8000.00);
INSERT INTO `ieum` VALUES (2, '联想笔记本', 2, 1, 6001.10);

SET FOREIGN_KEY_CHECKS = 1;
