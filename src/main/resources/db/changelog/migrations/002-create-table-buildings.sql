CREATE TABLE IF NOT EXISTS buildings
(
    id      SERIAL PRIMARY KEY,
    number  INTEGER NOT NULL,
    address TEXT    NOT NULL,
    unique (number, address)
);