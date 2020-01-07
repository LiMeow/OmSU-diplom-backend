CREATE TABLE IF NOT EXISTS study_direction
(
    id            SERIAL PRIMARY KEY,
    faculty_id    INTEGER     NOT NULL REFERENCES faculty (id) ON DELETE CASCADE,
    code          TEXT UNIQUE NOT NULL,
    name          TEXT        NOT NULL,
    qualification TEXT        NOT NULL,
    study_form    TEXT        NOT NULL
);