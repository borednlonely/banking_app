-- Banking App schema (PostgreSQL)

CREATE TABLE users (
    username TEXT PRIMARY KEY,
    password TEXT NOT NULL
);

CREATE TABLE accounts (
    account_number TEXT PRIMARY KEY,
    username       TEXT NOT NULL REFERENCES users(username),
    account_type   TEXT NOT NULL,
    name           TEXT NOT NULL,
    ssn            TEXT NOT NULL,
    balance        NUMERIC(12,2) NOT NULL,
    debit_card_num TEXT,
    debit_pin      TEXT
);
