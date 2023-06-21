-- DROP TABLE people IF EXISTS;

CREATE TABLE IF NOT EXISTS people  (
                         person_id VARCHAR(255) NOT NULL PRIMARY KEY,
                         first_name VARCHAR(20),
                         last_name VARCHAR(20)
);