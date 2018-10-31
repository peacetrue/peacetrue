DROP TABLE IF EXISTS `area`;
CREATE TABLE `area` (
  `id` int(11) NOT NULL PRIMARY KEY COMMENT 'id',
  `city_name` varchar(30) NOT NULL COMMENT '城市名称',
  `parent_id` int(11) NOT NULL COMMENT '父ID',
  `short_name` varchar(30) NOT NULL COMMENT '城市短名称',
  `depth` int(1) NOT NULL COMMENT '城市级别 0 国家 1省份 2市 3区、县',
  `city_code` varchar(4) NOT NULL COMMENT '区号',
  `zip_code` varchar(6) NOT NULL COMMENT '邮政编码',
  `merger_name` varchar(50) NOT NULL COMMENT '全名称',
  `longitude` varchar(16) NOT NULL COMMENT '经度',
  `latitude` varchar(16) NOT NULL COMMENT '维度',
  `spell` varchar(30) NOT NULL COMMENT '拼音',
  `is_use` int(1) unsigned zerofill DEFAULT NULL COMMENT '是否正在使用'
);