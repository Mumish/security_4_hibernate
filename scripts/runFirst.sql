create DATABASE security_demo;
use security_demo;
/*All User's are stored in USER table*/
create table USER (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_name VARCHAR(30) NOT NULL,
  password VARCHAR(100) NOT NULL,
  first_name VARCHAR(30) NOT NULL,
  last_name  VARCHAR(30) NOT NULL,
  email VARCHAR(30) NOT NULL,
  state VARCHAR(30) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (user_name)
);

/* USER_PROFILE table contains all possible roles */
create table USER_PROFILE(
  id BIGINT NOT NULL AUTO_INCREMENT,
  type VARCHAR(30) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (type)
);

/* JOIN TABLE for MANY-TO-MANY relationship*/
CREATE TABLE USER_USER_PROFILE (
  user_id BIGINT NOT NULL,
  user_profile_id BIGINT NOT NULL,
  PRIMARY KEY (user_id, user_profile_id),
  CONSTRAINT FK_USER FOREIGN KEY (user_id) REFERENCES USER (id),
  CONSTRAINT FK_USER_PROFILE FOREIGN KEY (user_profile_id) REFERENCES USER_PROFILE (id)
);

/* Populate USER_PROFILE Table */
INSERT INTO USER_PROFILE(type)
VALUES ('USER');

INSERT INTO USER_PROFILE(type)
VALUES ('ADMIN');

INSERT INTO USER_PROFILE(type)
VALUES ('DBA');

/* Populate USER Table */
INSERT INTO USER(user_name, password, first_name, last_name, email, state)
VALUES ('yuli','yuli', 'Yuli','Slabko','yuli@tut.by', 'Active');

INSERT INTO USER(user_name, password, first_name, last_name, email, state)
VALUES ('alex','alex', 'Alex','Alekseev','alex@gmail.com', 'Active');

INSERT INTO USER(user_name, password, first_name, last_name, email, state)
VALUES ('natty','natty', 'Natali','Great','natty@gmail.com', 'Active');

INSERT INTO USER(user_name, password, first_name, last_name, email, state)
VALUES ('serge','serge', 'Sergey','Gaft','serge@tut.by', 'Active');

INSERT INTO USER(user_name, password, first_name, last_name, email, state)
VALUES ('max','max', 'Max','Mirni','max@tut.by', 'Active');

/* Populate JOIN Table */
INSERT INTO USER_USER_PROFILE (user_id, user_profile_id)
  SELECT u.id, profile.id FROM user u, user_profile profile
  where u.user_name='natty' and profile.type='USER';

INSERT INTO USER_USER_PROFILE (user_id, user_profile_id)
  SELECT u.id, profile.id FROM user u, user_profile profile
  where u.user_name='max' and profile.type='USER';

INSERT INTO USER_USER_PROFILE (user_id, user_profile_id)
  SELECT u.id, profile.id FROM user u, user_profile profile
  where u.user_name='alex' and profile.type='ADMIN';

INSERT INTO USER_USER_PROFILE (user_id, user_profile_id)
  SELECT u.id, profile.id FROM user u, user_profile profile
  where u.user_name='serge' and profile.type='DBA';

INSERT INTO USER_USER_PROFILE (user_id, user_profile_id)
  SELECT u.id, profile.id FROM user u, user_profile profile
  where u.user_name='yuli' and profile.type='ADMIN';

INSERT INTO USER_USER_PROFILE (user_id, user_profile_id)
  SELECT u.id, profile.id FROM user u, user_profile profile
  where u.user_name='yuli' and profile.type='DBA';
