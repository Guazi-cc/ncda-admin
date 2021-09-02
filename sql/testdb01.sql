/*
 Navicat Premium Data Transfer

 Source Server         : localMysql
 Source Server Type    : MySQL
 Source Server Version : 50733
 Source Host           : localhost:3306
 Source Schema         : testdb01

 Target Server Type    : MySQL
 Target Server Version : 50733
 File Encoding         : 65001

 Date: 02/09/2021 09:15:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account_bill
-- ----------------------------
DROP TABLE IF EXISTS `account_bill`;
CREATE TABLE `account_bill`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `DATE` date NULL DEFAULT NULL COMMENT '日期',
  `MONEY` decimal(10, 3) NULL DEFAULT NULL COMMENT '金额',
  `MONEY_STATE` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '金额状态(收入1，支出0)',
  `TYPE` int(11) NULL DEFAULT NULL COMMENT '类别',
  `COMMENT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `DEL_STATE` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除状态(1表示被删除，0正常)',
  `ITEM_NAME` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `USER_ID` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 359 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for account_bill_type
-- ----------------------------
DROP TABLE IF EXISTS `account_bill_type`;
CREATE TABLE `account_bill_type`  (
  `TYPE_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `TYPE_ONE_NAME` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '一级分类名称',
  `USER_ID` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `TYPE_KEYWORD` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类关键词，词与词用英文逗号分隔',
  PRIMARY KEY (`TYPE_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for account_bill_upload_record
-- ----------------------------
DROP TABLE IF EXISTS `account_bill_upload_record`;
CREATE TABLE `account_bill_upload_record`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `DATE` date NULL DEFAULT NULL COMMENT '年月',
  `FILE_CONTENT` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文件内容',
  `DEL_STATE` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除状态（1删除，0正常）',
  `UPLOAD_TIME` datetime(0) NULL DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ncda_user
-- ----------------------------
DROP TABLE IF EXISTS `ncda_user`;
CREATE TABLE `ncda_user`  (
  `USER_ID` int(11) NOT NULL,
  `USER_NAME` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `GENDER` int(11) NULL DEFAULT NULL,
  `BIRTHDAT` date NULL DEFAULT NULL,
  `REAL_NAME` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`USER_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
