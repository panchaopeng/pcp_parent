# Host: 192.168.200.129  (Version 5.7.21)
# Date: 2019-02-21 11:58:11
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "tb_admin"
#

DROP TABLE IF EXISTS `tb_admin`;
CREATE TABLE `tb_admin` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `loginname` varchar(100) DEFAULT NULL COMMENT '登陆名称',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `state` varchar(1) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员';

#
# Data for table "tb_admin"
#


#
# Structure for table "tb_follow"
#

DROP TABLE IF EXISTS `tb_follow`;
CREATE TABLE `tb_follow` (
  `userid` varchar(20) NOT NULL COMMENT '用户ID',
  `targetuser` varchar(20) NOT NULL COMMENT '被关注用户ID',
  PRIMARY KEY (`userid`,`targetuser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "tb_follow"
#

INSERT INTO `tb_follow` VALUES ('1','1'),('1','10');

#
# Structure for table "tb_user"
#

DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号码',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(100) DEFAULT NULL COMMENT '昵称',
  `sex` varchar(2) DEFAULT NULL COMMENT '性别',
  `birthday` datetime DEFAULT NULL COMMENT '出生年月日',
  `avatar` varchar(100) DEFAULT NULL COMMENT '头像',
  `email` varchar(100) DEFAULT NULL COMMENT 'E-Mail',
  `regdate` datetime DEFAULT NULL COMMENT '注册日期',
  `updatedate` datetime DEFAULT NULL COMMENT '修改日期',
  `lastdate` datetime DEFAULT NULL COMMENT '最后登陆日期',
  `online` bigint(20) DEFAULT NULL COMMENT '在线时长（分钟）',
  `interest` varchar(100) DEFAULT NULL COMMENT '兴趣',
  `personality` varchar(100) DEFAULT NULL COMMENT '个性',
  `fanscount` int(20) DEFAULT NULL COMMENT '粉丝数',
  `followcount` int(20) DEFAULT NULL COMMENT '关注数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

#
# Data for table "tb_user"
#

INSERT INTO `tb_user` VALUES ('1',NULL,'111111','小白','男','2018-01-08 15:39:19',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0);
