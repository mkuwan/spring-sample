-- DROP TABLE people IF EXISTS;

CREATE TABLE IF NOT EXISTS people  (
                         person_id VARCHAR(255) NOT NULL,
                         first_name VARCHAR(20),
                         last_name VARCHAR(20),
                         CONSTRAINT people_key PRIMARY KEY (person_id)
);

CREATE TABLE IF NOT EXISTS cooking (
    cooking_id VARCHAR(255) NOT NULL,
    cooking_name VARCHAR(20),
    result VARCHAR(20),
    CONSTRAINT cooking_key PRIMARY KEY (cooking_id)
);