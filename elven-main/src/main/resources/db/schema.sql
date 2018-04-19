-- shedlock表
CREATE TABLE IF NOT EXISTS shedlock(
    name VARCHAR(64),
    lock_until TIMESTAMP(3) NULL,
    locked_at TIMESTAMP(3) NULL,
    locked_by  VARCHAR(255),
    PRIMARY KEY (name)
);

CREATE TABLE IF NOT EXISTS user (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  age double DEFAULT NULL,
  name varchar(255) NOT NULL,
  version bigint(20) NOT NULL,
  PRIMARY KEY (id),
);
