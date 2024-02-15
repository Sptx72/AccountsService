DROP TABLE IF EXISTS accounts;

CREATE TABLE accounts (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  type VARCHAR,
  opening_date DATE,
  balance INT,
  owner_id BIGINT
  );

INSERT INTO ACCOUNTS (id, type, opening_date, balance, owner_id) VALUES (1, 'PREMIUM', NULL, 17, 1),(2, 'DEFAULT', NULL, 17, 2),(3, 'PREMIUM', NULL, 17, 3),(4, 'DEFAULT', NULL, 17, 4),(5, 'PREMIUM', NULL, 17, 5),(6, 'DEFAULT', NULL, 17, 6),(7, 'PREMIUM', NULL, 17, 7)
