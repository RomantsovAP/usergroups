DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS groups;

CREATE TABLE groups (
  id          INTEGER PRIMARY KEY AUTO_INCREMENT,
  name              VARCHAR                 NOT NULL,

);

CREATE TABLE users
(
  id                INTEGER PRIMARY KEY AUTO_INCREMENT,
  name              VARCHAR                 NOT NULL,
  login             VARCHAR                 NOT NULL,
  birthday          TIMESTAMP               NOT NULL,
  status            VARCHAR                 NOT NULL,
  group_id          INTEGER  REFERENCES groups (id)

);




