/*
 Navicat Premium Data Transfer

 Source Server         : local-mysql
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : localhost:3306
 Source Schema         : testdb01

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 18/08/2021 16:32:44
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
  `DEL_STATE` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除状态(正常1，删除0)',
  `ITEM_NAME` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 69 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for account_bill_type
-- ----------------------------
DROP TABLE IF EXISTS `account_bill_type`;
CREATE TABLE `account_bill_type`  (
  `TYPE_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `TYPE_ONE_NAME` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '一级分类名称',
  `PARENT_ID` int(11) NULL DEFAULT NULL COMMENT '父分类id，只有二级分类有',
  `TYPE_TWO_NAME` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '二级分类名称',
  PRIMARY KEY (`TYPE_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account_bill_type
-- ----------------------------
INSERT INTO `account_bill_type` VALUES (1, '一', NULL, NULL);
INSERT INTO `account_bill_type` VALUES (2, '四', NULL, NULL);
INSERT INTO `account_bill_type` VALUES (3, '煮', NULL, NULL);
INSERT INTO `account_bill_type` VALUES (4, 'ok', NULL, NULL);

-- ----------------------------
-- Table structure for account_bill_upload_record
-- ----------------------------
DROP TABLE IF EXISTS `account_bill_upload_record`;
CREATE TABLE `account_bill_upload_record`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `DATE` date NULL DEFAULT NULL COMMENT '年月',
  `FILE_CONTENT` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文件内容',
  `DEL_STATE` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除状态（0删除，1正常）',
  `UPLOAD_TIME` datetime(0) NULL DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
