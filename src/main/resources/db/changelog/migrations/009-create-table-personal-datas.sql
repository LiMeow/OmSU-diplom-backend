CREATE TABLE IF NOT EXISTS personal_datas
(
    id         SERIAL PRIMARY KEY,
    user_id    INTEGER NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    firstname  TEXT    NOT NULL,
    patronymic TEXT DEFAULT NULL,
    lastname   TEXT    NOT NULL
);