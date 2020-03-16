CREATE TABLE IF NOT EXISTS course
(
    id          SERIAL PRIMARY KEY,
    faculty_id  INTEGER NOT NULL REFERENCES faculty (id) ON DELETE CASCADE,
    start_year  TEXT    NOT NULL,
    finish_year TEXT DEFAULT NULL,
    UNIQUE (faculty_id, start_year)
);