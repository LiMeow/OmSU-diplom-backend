CREATE TABLE IF NOT EXISTS time_blocks
(
    id        SERIAL PRIMARY KEY,
    time_from TEXT UNIQUE NOT NULL,
    time_to   TEXT UNIQUE NOT NULL
);