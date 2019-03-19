# Host: 192.168.200.129  (Version 5.7.21)
# Date: 2019-02-21 11:57:28
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "tb_city"
#

DROP TABLE IF EXISTS `tb_city`;
CREATE TABLE `tb_city` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `name` varchar(20) DEFAULT NULL COMMENT '城市名称',
  `ishot` varchar(1) DEFAULT NULL COMMENT '是否热门',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='城市';

#
# Data for table "tb_city"
#

INSERT INTO `tb_city` VALUES ('1','北京','1'),('2','上海','1'),('3','广州','1'),('4','深圳','1'),('5','天津','0'),('6','重庆','0'),('7','西安','0');

#
# Structure for table "tb_label"
#

DROP TABLE IF EXISTS `tb_label`;
CREATE TABLE `tb_label` (
  `id` varchar(20) NOT NULL COMMENT '标签ID',
  `labelname` varchar(100) DEFAULT NULL COMMENT '标签名称',
  `state` varchar(1) DEFAULT NULL COMMENT '状态',
  `count` bigint(20) DEFAULT NULL COMMENT '使用数量',
  `recommend` varchar(1) DEFAULT NULL COMMENT '是否推荐',
  `fans` bigint(20) DEFAULT NULL COMMENT '粉丝数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签';

#
# Data for table "tb_label"
#

INSERT INTO `tb_label` VALUES ('1','java','1',NULL,NULL,NULL),('2','PHP','1',NULL,NULL,NULL),('3','C++','1',NULL,NULL,NULL),('4','python','1',NULL,NULL,NULL);

#
# Structure for table "tb_ul"
#

DROP TABLE IF EXISTS `tb_ul`;
CREATE TABLE `tb_ul` (
  `userid` varchar(20) NOT NULL,
  `labelid` varchar(20) NOT NULL,
  PRIMARY KEY (`userid`,`labelid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "tb_ul"
#

INSERT INTO `tb_ul` VALUES ('1','1'),('1','2'),('1','3');
