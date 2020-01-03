CREATE TABLE IF NOT EXISTS lecturer_preference
(
    id          SERIAL PRIMARY KEY,
    lecturer_id INTEGER NOT NULL REFERENCES lecturer (id) ON DELETE CASCADE,
    preference  TEXT    NOT NULL
);