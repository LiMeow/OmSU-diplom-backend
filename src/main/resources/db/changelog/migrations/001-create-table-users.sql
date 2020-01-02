CREATE TABLE IF NOT EXISTS users
(
    id        SERIAL PRIMARY KEY,
    email     TEXT unique NOT NULL,
    pswrd     TEXT DEFAULT NULL,
    user_type TEXT        NOT NULL
);