CREATE TABLE IF NOT EXISTS time_block
(
    id        SERIAL PRIMARY KEY,
    time_from TEXT UNIQUE NOT NULL,
    time_to   TEXT UNIQUE NOT NULL
);