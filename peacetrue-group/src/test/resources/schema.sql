DROP TABLE IF EXISTS user;
CREATE TABLE user (
  id   BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL
);


DROP TABLE IF EXISTS shop_apply;
CREATE TABLE shop_apply (
  id           BIGINT PRIMARY KEY AUTO_INCREMENT,
  name         VARCHAR(255) NOT NULL,
  creator_id   BIGINT       NOT NULL,
  created_time DATETIME     NOT NULL
);


DROP TABLE IF EXISTS shop;
CREATE TABLE shop (
  id           BIGINT PRIMARY KEY AUTO_INCREMENT,
  name         VARCHAR(255) NOT NULL,
  creator_id   BIGINT       NOT NULL,
  created_time DATETIME     NOT NULL
);


