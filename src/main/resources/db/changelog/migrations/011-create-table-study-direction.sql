CREATE TABLE IF NOT EXISTS study_direction
(
    id            SERIAL PRIMARY KEY,
    faculty_id    INTEGER     NOT NULL REFERENCES faculty (id) ON DELETE CASCADE,
    qualification TEXT        NOT NULL,
    name          TEXT UNIQUE NOT NULL
);