DROP TABLE IF EXISTS accounts;

CREATE TABLE accounts (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  type VARCHAR,
  opening_date DATE,
  balance INT,
  owner_id BIGINT
  );

INSERT INTO ACCOUNTS (type, opening_date, balance, owner_id) VALUES ('PREMIUM', NULL, 17, 1),('DEFAULT', NULL, 17, 2),('PREMIUM', NULL, 17, 3),('DEFAULT', NULL, 17, 4),('PREMIUM', NULL, 17, 5),('DEFAULT', NULL, 17, 6),('PREMIUM', NULL, 17, 7)
