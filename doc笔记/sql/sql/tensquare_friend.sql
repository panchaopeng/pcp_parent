# Host: 192.168.200.129  (Version 5.7.21)
# Date: 2019-02-21 11:57:40
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "tb_friend"
#

DROP TABLE IF EXISTS `tb_friend`;
CREATE TABLE `tb_friend` (
  `userid` varchar(20) NOT NULL COMMENT '用户ID',
  `friendid` varchar(20) NOT NULL COMMENT '好友ID',
  `islike` varchar(1) DEFAULT NULL COMMENT '是否互相喜欢',
  PRIMARY KEY (`userid`,`friendid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "tb_friend"
#

INSERT INTO `tb_friend` VALUES ('1','100','1');
