DELETE FROM users;
DELETE FROM GROUPS;

INSERT INTO groups (name) VALUES
  ('GROUP111'),
  ('GROUP222'),
  ('GROUP333');

INSERT INTO users (NAME, LOGIN, BIRTHDAY, STATUS, GROUP_ID) VALUES
  ('User1', 'User1', '1985-05-30 0:00:00', 'Active', 1),
  ('User2', 'User2', '1984-05-30 0:00:00', 'Active', 1),
  ('User3', 'User3', '1983-05-30 0:00:00', 'Active', 1),
  ('User4', 'User4', '1982-05-30 0:00:00', 'Active', 1),
  ('User5', 'User5', '1981-05-30 0:00:00', 'Active', 1);
