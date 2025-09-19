CREATE TABLE bacnend.sys_user
(
  id       BIGINT       NOT NULL,
  username VARCHAR(255) NULL,
  email    VARCHAR(255) NULL,
  password VARCHAR(255) NULL,
  CONSTRAINT pk_sys_user PRIMARY KEY (id)
);
