CREATE TABLE IF NOT EXISTS study_directions
(
    id            SERIAL PRIMARY KEY,
    faculty_id    INTEGER     NOT NULL REFERENCES faculties (id) ON DELETE CASCADE,
    qualification TEXT        NOT NULL,
    name          TEXT UNIQUE NOT NULL
);