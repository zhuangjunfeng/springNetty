/*
Navicat MySQL Data Transfer

Source Server         : wamp
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : sems_air

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2016-11-15 13:21:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for air_device
-- ----------------------------
DROP TABLE IF EXISTS `air_device`;
CREATE TABLE `air_device` (
  `device_id` int(100) NOT NULL AUTO_INCREMENT COMMENT '设备编号',
  `device_name` varchar(1000) DEFAULT NULL,
  `device_uid` varchar(100) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  `update_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of air_device
-- ----------------------------

-- ----------------------------
-- Table structure for air_user
-- ----------------------------
DROP TABLE IF EXISTS `air_user`;
CREATE TABLE `air_user` (
  `user_id` int(32) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `open_id` varchar(100) DEFAULT NULL COMMENT '用户的唯一标识',
  `nickname` varchar(1000) DEFAULT NULL COMMENT '用户昵称',
  `sex` varchar(3) DEFAULT NULL COMMENT '用户的性别，值为1时是男性，值为2时是女性，值为0时是未知',
  `province` varchar(255) DEFAULT NULL COMMENT '用户个人资料填写的省份',
  `city` varchar(255) DEFAULT NULL COMMENT '普通用户个人资料填写的城市',
  `country` varchar(255) DEFAULT NULL COMMENT '国家，如中国为CN',
  `headimgurl` varchar(1000) DEFAULT NULL COMMENT '用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。',
  `privilege` varchar(1000) DEFAULT NULL COMMENT '用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）',
  `unionid` varchar(100) DEFAULT NULL COMMENT '只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。',
  `create_time` varchar(255) DEFAULT '' COMMENT '创建时间',
  `update_time` varchar(255) DEFAULT '' COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of air_user
-- ----------------------------
