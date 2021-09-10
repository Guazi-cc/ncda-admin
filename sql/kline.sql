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

 Date: 10/09/2021 16:39:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for kline
-- ----------------------------
DROP TABLE IF EXISTS `kline`;
CREATE TABLE `kline`  (
  `ID` int(11) NOT NULL,
  `TRA_NM` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交易对名称',
  `OPEN` decimal(7, 4) NULL DEFAULT NULL COMMENT '开盘价',
  `CLOSE` decimal(7, 4) NULL DEFAULT NULL COMMENT '收盘价',
  `HIGHEST` decimal(7, 4) NULL DEFAULT NULL COMMENT '最高价',
  `LOWEST` decimal(7, 4) NULL DEFAULT NULL COMMENT '最低价',
  `TRA_TIME` datetime(0) NULL DEFAULT NULL COMMENT '交易时间',
  `VOLUME` decimal(7, 4) NULL DEFAULT NULL COMMENT '交易量',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
