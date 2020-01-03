CREATE TABLE IF NOT EXISTS "user"
(
    id        SERIAL PRIMARY KEY,
    email     TEXT unique NOT NULL,
    password  TEXT DEFAULT NULL,
    user_type TEXT        NOT NULL
);