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

 Date: 09/09/2021 17:27:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for todo_list
-- ----------------------------
DROP TABLE IF EXISTS `todo_list`;
CREATE TABLE `todo_list`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `CONTENT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '待办事项内容',
  `STATUS` bit(1) NOT NULL COMMENT '状态，0未完成，1已完成',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `COMPLETE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '完成时间',
  `USER_ID` int(11) NULL DEFAULT NULL,
  `DEL_STATE` bit(1) NULL DEFAULT NULL COMMENT '删除状态',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of todo_list
-- ----------------------------
INSERT INTO `todo_list` VALUES (2, '呸呸呸', b'1', '2021-09-09 14:21:42', '2021-09-09 15:19:10', NULL, b'1');
INSERT INTO `todo_list` VALUES (3, '呵呵', b'1', '2021-09-09 14:41:57', '2021-09-09 15:19:08', NULL, b'1');
INSERT INTO `todo_list` VALUES (4, '7788', b'1', '2021-09-09 14:42:29', '2021-09-09 15:19:07', NULL, b'1');
INSERT INTO `todo_list` VALUES (5, '待办事项功能开发', b'0', '2021-09-09 14:42:47', NULL, NULL, b'0');
INSERT INTO `todo_list` VALUES (6, '8', b'1', '2021-09-09 14:42:49', '2021-09-09 16:37:14', NULL, b'1');
INSERT INTO `todo_list` VALUES (7, '9', b'1', '2021-09-09 14:42:51', '2021-09-09 16:00:06', NULL, b'1');
INSERT INTO `todo_list` VALUES (8, '10', b'1', '2021-09-09 14:42:54', '2021-09-09 15:11:19', NULL, b'1');
INSERT INTO `todo_list` VALUES (10, '广泛的', b'1', '2021-09-09 14:43:00', '2021-09-09 14:53:02', NULL, b'1');
INSERT INTO `todo_list` VALUES (12, 'K线图功能开发', b'0', '2021-09-09 14:43:05', NULL, NULL, b'0');
INSERT INTO `todo_list` VALUES (13, '22', b'1', '2021-09-09 15:03:43', '2021-09-09 16:00:34', NULL, b'1');
INSERT INTO `todo_list` VALUES (15, '数字货币行情功能开发', b'0', '2021-09-09 15:21:12', NULL, NULL, b'0');
INSERT INTO `todo_list` VALUES (17, '账本功能', b'1', '2021-09-09 15:22:21', '2021-09-09 16:51:43', NULL, b'0');
INSERT INTO `todo_list` VALUES (18, '2', b'1', '2021-09-09 16:31:00', '2021-09-09 16:37:15', NULL, b'1');
INSERT INTO `todo_list` VALUES (19, 'r', b'0', '2021-09-09 16:33:59', NULL, NULL, b'1');
INSERT INTO `todo_list` VALUES (20, '7777', b'0', '2021-09-09 17:10:08', NULL, NULL, b'1');

SET FOREIGN_KEY_CHECKS = 1;
