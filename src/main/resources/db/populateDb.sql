DELETE FROM users;
DELETE FROM GROUPS;

INSERT INTO groups (id, name) VALUES
  (1,'GROUP111'),
  (2,'GROUP222'),
  (3,'GROUP333'),
  (4,'GROUP444');

INSERT INTO users (NAME, LOGIN, BIRTHDAY, STATUS, GROUP_ID) VALUES
  ('User1'  , 'User1' , '1985-05-30 0:00:00', 'Active', 1),
  ('User2'  , 'User2' , '1984-05-30 0:00:00', 'Active', 1),
  ('User3'  , 'User3' , '1983-05-30 0:00:00', 'Active', 1),
  ('User4'  , 'User4' , '1982-05-30 0:00:00', 'Active', 2),
  ('User10' , 'User10', '1981-05-30 0:00:00', 'Active', 3),
  ('User12' , 'User12', '1984-05-30 0:00:00', 'Active', 3),
  ('User13' , 'User13', '1983-05-30 0:00:00', 'Active', 3),
  ('User14' , 'User14', '1982-05-30 0:00:00', 'Active', 3),
  ('User15' , 'User15', '1981-05-30 0:00:00', 'Active', 4),
  ('User18' , 'User18', '1983-05-30 0:00:00', 'Active', 4),
  ('User19' , 'User19', '1982-05-30 0:00:00', 'Active', 4),
  ('User20' , 'User20', '1981-05-30 0:00:00', 'Active', 4);
