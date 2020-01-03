CREATE TABLE IF NOT EXISTS personal_data
(
    id         SERIAL PRIMARY KEY,
    user_id    INTEGER NOT NULL REFERENCES "user" (id) ON DELETE CASCADE,
    firstname  TEXT    NOT NULL,
    patronymic TEXT DEFAULT NULL,
    lastname   TEXT    NOT NULL
);