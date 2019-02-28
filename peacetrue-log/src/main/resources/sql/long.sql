DROP TABLE IF EXISTS log;
CREATE TABLE log (
  id            BIGINT NOT NULL COMMENT '主键',
  module_code   VARCHAR(63) NOT NULL COMMENT '模块编码',
  record_id     BIGINT COMMENT '记录主键',
  operate_code  VARCHAR(63) NOT NULL COMMENT '操作编码',
  description   VARCHAR(255) NOT NULL COMMENT '操作描述',
  creator_id    BIGINT NOT NULL COMMENT '创建者主键',
  created_time  TIMESTAMP NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id)
) COMMENT '日志';

