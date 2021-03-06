CREATE TABLE IF NOT EXISTS "user"
(
    id         SERIAL PRIMARY KEY,
    firstname  TEXT        NOT NULL,
    patronymic TEXT DEFAULT NULL,
    lastname   TEXT        NOT NULL,
    email      TEXT unique NOT NULL,
    password   TEXT DEFAULT NULL,
    user_type  TEXT        NOT NULL,
    enabled    BOOLEAN     NOT NULL
);