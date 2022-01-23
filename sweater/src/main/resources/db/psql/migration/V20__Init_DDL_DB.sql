CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE message (
  id int8 NOT NULL,
  filename varchar(255),
  tag varchar(255),
  text varchar(2048) NOT NULL,
  user_id int8,
  PRIMARY KEY (id)
);

CREATE TABLE user_role (
  user_id int8 NOT NULL,
  roles varchar(255)
);

CREATE TABLE usr (
  id int8 NOT NULL,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  email varchar(255),
  active boolean NOT NULL,
  activation_code varchar(255),
  PRIMARY KEY (id)
);

ALTER TABLE if exists message
    ADD CONSTRAINT message_user_fk
    FOREIGN KEY (user_id) REFERENCES usr;

ALTER TABLE if exists user_role
    ADD CONSTRAINT user_role_user_fk
    FOREIGN KEY (user_id) REFERENCES usr;
