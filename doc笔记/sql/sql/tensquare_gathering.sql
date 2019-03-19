# Host: 192.168.200.129  (Version 5.7.21)
# Date: 2019-02-21 11:57:48
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "tb_gathering"
#

DROP TABLE IF EXISTS `tb_gathering`;
CREATE TABLE `tb_gathering` (
  `id` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '活动名称',
  `summary` text COMMENT '大会简介',
  `detail` text COMMENT '详细说明',
  `sponsor` varchar(100) DEFAULT NULL COMMENT '主办方',
  `image` varchar(100) DEFAULT NULL COMMENT '活动图片',
  `starttime` datetime DEFAULT NULL COMMENT '开始时间',
  `endtime` datetime DEFAULT NULL COMMENT '截止时间',
  `address` varchar(100) DEFAULT NULL COMMENT '举办地点',
  `enrolltime` datetime DEFAULT NULL COMMENT '报名截止',
  `state` varchar(1) DEFAULT NULL COMMENT '是否可见',
  `city` varchar(20) DEFAULT NULL COMMENT '城市',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动';

#
# Data for table "tb_gathering"
#

INSERT INTO `tb_gathering` VALUES ('1','测试活动','喝茶看电影，不亦乐乎','喝茶看电影，不亦乐乎','黑马程序员',NULL,'2017-12-14 15:39:32','2017-12-21 15:39:36',NULL,NULL,'1','1'),('94377594140','aaaa',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','1'),('943776146707845','aaaa',NULL,NULL,'ssss',NULL,NULL,NULL,'cccc',NULL,'1','1'),('943776663576121344','aaaa',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2'),('943783521749700608','2342423',NULL,NULL,'23454534',NULL,NULL,NULL,'545435435',NULL,'1','2'),('944085821768732672','JAVAEE茶话会',NULL,NULL,'传智',NULL,NULL,NULL,'金燕龙',NULL,'1','2'),('944086086991351808','是',NULL,NULL,'11',NULL,NULL,NULL,'11',NULL,'1','3'),('944090372710207488','大讨论',NULL,NULL,'小马',NULL,NULL,NULL,'消息',NULL,'1','3'),('944105652622594048','测试测试',NULL,NULL,'测试者',NULL,NULL,NULL,'测试地址',NULL,'1','4'),('945227340642914304','111',NULL,NULL,'222',NULL,NULL,NULL,'333',NULL,'1','5'),('945227678527655936','111',NULL,NULL,'222',NULL,NULL,NULL,'333',NULL,'1','5'),('945235087564345344','啊啊',NULL,NULL,'1',NULL,NULL,NULL,'1',NULL,'1','1'),('945235534329024512','1',NULL,NULL,'1',NULL,NULL,NULL,'1',NULL,'1','2'),('945235859786043392','1',NULL,NULL,'1',NULL,NULL,NULL,'1',NULL,'1','3'),('951384896167874560','??',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

#
# Structure for table "tb_usergath"
#

DROP TABLE IF EXISTS `tb_usergath`;
CREATE TABLE `tb_usergath` (
  `userid` varchar(20) NOT NULL COMMENT '用户ID',
  `gathid` varchar(20) NOT NULL COMMENT '活动ID',
  `exetime` datetime DEFAULT NULL COMMENT '点击时间',
  PRIMARY KEY (`userid`,`gathid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户关注活动';

#
# Data for table "tb_usergath"
#

INSERT INTO `tb_usergath` VALUES ('1','200','2018-01-06 15:44:04');
