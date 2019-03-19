# Host: 192.168.200.129  (Version 5.7.21)
# Date: 2019-02-21 11:58:04
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
# Structure for table "tb_enterprise"
#

DROP TABLE IF EXISTS `tb_enterprise`;
CREATE TABLE `tb_enterprise` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `name` varchar(100) DEFAULT NULL COMMENT '企业名称',
  `summary` varchar(1000) DEFAULT NULL COMMENT '企业简介',
  `address` varchar(100) DEFAULT NULL COMMENT '企业地址',
  `labels` varchar(100) DEFAULT NULL COMMENT '标签列表',
  `coordinate` varchar(100) DEFAULT NULL COMMENT '坐标',
  `ishot` varchar(1) DEFAULT NULL COMMENT '是否热门',
  `logo` varchar(100) DEFAULT NULL COMMENT 'LOGO',
  `jobcount` int(11) DEFAULT NULL COMMENT '职位数',
  `url` varchar(100) DEFAULT NULL COMMENT 'URL',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业';

#
# Data for table "tb_enterprise"
#

INSERT INTO `tb_enterprise` VALUES ('','传智播客','国内著名IT教育机构','金燕龙办公楼','IT 培训','1019,2223','1',NULL,NULL,NULL),('2','小米','手机厂商','中关村软件园','手机','0211,3333','0',NULL,NULL,NULL);

#
# Structure for table "tb_recruit"
#

DROP TABLE IF EXISTS `tb_recruit`;
CREATE TABLE `tb_recruit` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `jobname` varchar(100) DEFAULT NULL COMMENT '职位名称',
  `salary` varchar(100) DEFAULT NULL COMMENT '薪资范围',
  `condition` varchar(100) DEFAULT NULL COMMENT '经验要求',
  `education` varchar(100) DEFAULT NULL COMMENT '学历要求',
  `type` varchar(1) DEFAULT NULL COMMENT '任职方式',
  `address` varchar(100) DEFAULT NULL COMMENT '办公地址',
  `eid` varchar(20) DEFAULT NULL COMMENT '企业ID',
  `createtime` datetime DEFAULT NULL COMMENT '创建日期',
  `state` varchar(1) DEFAULT NULL COMMENT '状态',
  `url` varchar(100) DEFAULT NULL COMMENT '网址',
  `label` varchar(100) DEFAULT NULL COMMENT '标签',
  `content1` varchar(100) DEFAULT NULL COMMENT '职位描述',
  `content2` varchar(100) DEFAULT NULL COMMENT '职位要求',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='职位';

#
# Data for table "tb_recruit"
#

INSERT INTO `tb_recruit` VALUES ('','大数据工程师','20000-30000','八年以上开发经验','本科','1','国贸','1','2018-01-06 16:21:12','1',NULL,NULL,NULL,NULL),('1','java开发工程师','15000-20000','五年以上开发经验','本科','1','中关村软件园','1','2018-01-05 15:38:05','1','http://www.baidu.com',NULL,NULL,NULL),('2','php开发工程师','4000-6000','一年以上开发经验','专科','1','王府街宏福创业园','1','2018-01-07 16:10:20','1','http://www.baidu.com',NULL,NULL,NULL),('3','.net开发工程师','2000-3000','一年以上开发经验','专科','1','大望路','1','2018-01-06 16:20:27','2',NULL,NULL,NULL,NULL),('5','前端开发工程师','8000-12000','三年以上开发经验','本科','1','上地','1','2018-01-18 16:22:11','2',NULL,NULL,NULL,NULL);
