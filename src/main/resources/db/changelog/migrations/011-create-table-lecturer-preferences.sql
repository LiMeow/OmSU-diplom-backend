CREATE TABLE IF NOT EXISTS lecturer_preferences
(
    id          SERIAL PRIMARY KEY,
    lecturer_id INTEGER NOT NULL REFERENCES lecturers (id) ON DELETE CASCADE,
    preference  TEXT    NOT NULL
);