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

 Date: 16/08/2021 17:39:15
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
  `TYPE` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类别',
  `COMMENT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `DEL_STATE` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除状态(正常1，删除0)',
  `ITEM_NAME` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 322 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account_bill
-- ----------------------------
INSERT INTO `account_bill` VALUES (1, '2021-07-01', 11.000, '1', '分类', '备注呗', '1', '名字啊');

-- ----------------------------
-- Table structure for account_bill_upload_record
-- ----------------------------
DROP TABLE IF EXISTS `account_bill_upload_record`;
CREATE TABLE `account_bill_upload_record`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `DATE` date NULL DEFAULT NULL COMMENT '年月',
  `FILE_CONTENT` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文件内容',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account_bill_upload_record
-- ----------------------------
INSERT INTO `account_bill_upload_record` VALUES (10, '2021-08-01', '2021年8月\n8.1/问题1.5/改哦99/看看55\n8.2/赤鸡223/咳咳66/低昂自一哦+600/\n8.3/来了23\n8.4/改了98/扣扣954/\n8.5/单丝+6');

SET FOREIGN_KEY_CHECKS = 1;
