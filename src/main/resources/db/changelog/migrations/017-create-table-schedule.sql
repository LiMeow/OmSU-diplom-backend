CREATE TABLE IF NOT EXISTS schedule
(
    id         SERIAL PRIMARY KEY,
    course     INTEGER NOT NULL,
    semester   INTEGER NOT NULL,
    study_year TEXT    NOT NULL,
    group_id   INTEGER NOT NULL REFERENCES "group" (id) ON DELETE CASCADE,
    unique (course, semester, study_year, group_id)
);