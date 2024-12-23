CREATE SCHEMA IF NOT EXISTS calculator;

CREATE TABLE IF NOT EXISTS calculator.calculation
(
    id                   BIGSERIAL PRIMARY KEY,
    first_number         VARCHAR(255),
    first_base           VARCHAR(50),
    second_number        VARCHAR(255),
    second_base          VARCHAR(50),
    operation_type       VARCHAR(50),
    calculation_datetime TIMESTAMP
);