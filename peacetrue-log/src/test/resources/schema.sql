DROP TABLE IF EXISTS log;
CREATE TABLE log (
  id            BIGINT NOT NULL,
  module_code   VARCHAR(255),
  record_id     BIGINT,
  operate_code  VARCHAR(255),
  description VARCHAR(255),
  creator_id   BIGINT,
  created_time  TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE user (
  id       BIGINT NOT NULL,
  name     VARCHAR(255),
  nickname VARCHAR(255),
  PRIMARY KEY (id)
);
