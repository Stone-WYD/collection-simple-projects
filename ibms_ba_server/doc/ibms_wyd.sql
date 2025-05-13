/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : localhost:3306
 Source Schema         : ibms_wyd

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 13/05/2025 17:12:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_ba
-- ----------------------------
DROP TABLE IF EXISTS `t_ba`;
CREATE TABLE `t_ba`  (
  `ID` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'id',
  `STOREY_ID` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '楼层ID',
  `EQUIPMENT_TYPE_ID` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '设备类型',
  `NAME` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '名称',
  `PROTOCOL` int NULL DEFAULT NULL COMMENT '通讯协议 ,0 modbus, 1 tcp/ip , 2 http .....',
  `ADDRESS` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '设备通讯地址，通讯中的设备标识',
  `IP` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `SORT_NO` int NULL DEFAULT NULL COMMENT '排序',
  `POSITION_X` int NULL DEFAULT NULL COMMENT '效果图坐标X',
  `POSITION_Y` int NULL DEFAULT NULL COMMENT '效果图坐标Y',
  `ENABLE` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '是否启用0不启用1启用',
  `NOWSTATUS` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '当前状态0未知 1正常 2异常',
  `ADDTIME` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `STAT` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `POSITION_Z` int NULL DEFAULT 0 COMMENT '效果图坐标Z 三维时单位为0.01',
  `ROTATION_X` int NULL DEFAULT 0 COMMENT 'x轴旋转 单位为0.01',
  `ROTATION_Y` int NULL DEFAULT 0 COMMENT 'Y轴旋转 单位为0.01',
  `ROTATION_Z` int NULL DEFAULT 0 COMMENT 'Z轴旋转 单位为0.01',
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `FK_Reference_98`(`EQUIPMENT_TYPE_ID` ASC) USING BTREE,
  INDEX `FK_Reference_99`(`STOREY_ID` ASC) USING BTREE,
  CONSTRAINT `FK_Reference_98` FOREIGN KEY (`EQUIPMENT_TYPE_ID`) REFERENCES `t_ba_equipment_type` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_99` FOREIGN KEY (`STOREY_ID`) REFERENCES `t_base_storey` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = 'BA' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_ba_equipment_type
-- ----------------------------
DROP TABLE IF EXISTS `t_ba_equipment_type`;
CREATE TABLE `t_ba_equipment_type`  (
  `ID` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'id',
  `NAME` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '类型名称',
  `CODE` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '类型码',
  `STAT` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '0无效1有效',
  `ACTIVE_ICON` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '运行状态下图片',
  `DEFAULT_ICON` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '正常状态下图片',
  `FAULT_ICON` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '报警状态下图片',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = 'BA设备类型' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_ba_parameter
-- ----------------------------
DROP TABLE IF EXISTS `t_ba_parameter`;
CREATE TABLE `t_ba_parameter`  (
  `ID` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'id',
  `EQUIPMENT_ID` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '设备ID',
  `NAME` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '名称',
  `OB_NAME` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '对象名称',
  `IP` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `DEVID` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '设备ID',
  `BA_INDEX` int NULL DEFAULT NULL COMMENT '索引',
  `TYPE` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'BV  AV',
  `IO_TYPE` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `OPTIONS` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'BV时的选择项  逗号分割',
  `UNIT` varchar(5) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '单位',
  `WRITE_ABLE` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '是否可写 0 只读 1读写',
  `LASTVALUE` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '当前值',
  `LASTTIME` datetime NULL DEFAULT NULL COMMENT '最后更新时间',
  `DIGIT` int NULL DEFAULT 1 COMMENT '小数位数',
  `DURATION_STATISTICS` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '0' COMMENT '是否时长统计 只适用用BV BI，  0不统计 1 为0触发统计  2为1触发统计 3双向触发',
  `STATISTICS_TYPE` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '0' COMMENT '统计类型 0无统计 1运行 2告警 3故障',
  `ADDTIME` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `STAT` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `MAIN_FLAG` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '主属性标识 1-主属性，0-非主属性',
  `DATA_TYPE` int NULL DEFAULT NULL COMMENT '设备类型',
  `DATA_LEN` int NULL DEFAULT NULL,
  `DATA_COEFFICIENT` int NULL DEFAULT NULL,
  `DATA_MAPING` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `FK_Reference_100`(`EQUIPMENT_ID` ASC) USING BTREE,
  CONSTRAINT `FK_Reference_100` FOREIGN KEY (`EQUIPMENT_ID`) REFERENCES `t_ba` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = 'BA参数' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for t_ba_parameter_duration
-- ----------------------------
DROP TABLE IF EXISTS `t_ba_parameter_duration`;
CREATE TABLE `t_ba_parameter_duration`  (
  `ID` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'id',
  `PARAMETER_ID` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '设备ID',
  `BEGTIME` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `ENDTIME` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `DURATION` int NULL DEFAULT NULL COMMENT '时长秒',
  `VALUE` char(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '记录时的BA值',
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `FK_Reference_102`(`PARAMETER_ID` ASC) USING BTREE,
  CONSTRAINT `FK_Reference_102` FOREIGN KEY (`PARAMETER_ID`) REFERENCES `t_ba_parameter` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '参数某状态持续时长' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_ba_parameter_value
-- ----------------------------
DROP TABLE IF EXISTS `t_ba_parameter_value`;
CREATE TABLE `t_ba_parameter_value`  (
  `ID` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'id',
  `PARAMETER_ID` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '设备ID',
  `VALUE` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '当前值',
  `ADDTIME` datetime NULL DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `FK_Reference_101`(`PARAMETER_ID` ASC) USING BTREE,
  CONSTRAINT `FK_Reference_101` FOREIGN KEY (`PARAMETER_ID`) REFERENCES `t_ba_parameter` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = 'BA数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_base_build
-- ----------------------------
DROP TABLE IF EXISTS `t_base_build`;
CREATE TABLE `t_base_build`  (
  `ID` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'id',
  `NAME` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '名称',
  `SHOW_NAME` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '显示名称',
  `BUILD_DRAW` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图片路径',
  `LOCATION` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '位置',
  `SORT_NO` int NULL DEFAULT NULL COMMENT '排序',
  `ADDTIME` datetime NULL DEFAULT NULL COMMENT '入库时间',
  `IMG_WIDTH` int NULL DEFAULT NULL COMMENT '效果图宽度',
  `IMG_HEIGHT` int NULL DEFAULT NULL COMMENT '效果图高度',
  `ENABLE` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '是否启用0不启用1启用',
  `STAT` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '状态0无效1有效',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '楼栋表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_base_build_role
-- ----------------------------
DROP TABLE IF EXISTS `t_base_build_role`;
CREATE TABLE `t_base_build_role`  (
  `ID` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'id',
  `BUILD_ID` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '楼栋id',
  `ROLE_ID` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '角色ID',
  `ADDTIME` datetime NULL DEFAULT NULL COMMENT '入库时间',
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `t_base_build_role_build`(`BUILD_ID` ASC) USING BTREE,
  INDEX `t_base_build_role_role`(`ROLE_ID` ASC) USING BTREE,
  CONSTRAINT `t_base_build_role_build` FOREIGN KEY (`BUILD_ID`) REFERENCES `t_base_build` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_base_build_role_role` FOREIGN KEY (`ROLE_ID`) REFERENCES `t_system_role` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '楼栋角色对应表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_base_storey
-- ----------------------------
DROP TABLE IF EXISTS `t_base_storey`;
CREATE TABLE `t_base_storey`  (
  `ID` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'id',
  `BUILD_ID` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `NAME` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '名称',
  `SHOW_NAME` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '显示名称',
  `STOREY_DRAW` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图片路径',
  `FLOOR` varchar(6) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '门禁系统对应的楼层号，导数据使用',
  `STOREY_TYPE` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '类型0地下室 1地上',
  `SORT_NO` int NULL DEFAULT NULL COMMENT '排序',
  `ADDTIME` datetime NULL DEFAULT NULL COMMENT '入库时间',
  `IMG_WIDTH` int NULL DEFAULT NULL COMMENT '效果图宽度',
  `IMG_HEIGHT` int NULL DEFAULT NULL COMMENT '效果图高度',
  `ENABLE` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '是否启用0不启用1启用',
  `STAT` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '状态0无效1有效',
  `ISDEFAULT` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '0' COMMENT '是否默认楼层 0否 1是',
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `FK_Reference_12`(`BUILD_ID` ASC) USING BTREE,
  CONSTRAINT `FK_Reference_12` FOREIGN KEY (`BUILD_ID`) REFERENCES `t_base_build` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '楼层表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
