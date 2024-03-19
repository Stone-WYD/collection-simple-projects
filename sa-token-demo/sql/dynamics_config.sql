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

 Date: 19/07/2023 11:53:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dynamics_config
-- ----------------------------
DROP TABLE IF EXISTS `dynamics_config`;
CREATE TABLE `dynamics_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '配置名',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '配置最后一段英文名',
  `complete_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '配置完整code，即在配置文件中的路径',
  `value` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '配置值',
  `commentary` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '配置含义描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
