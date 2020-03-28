CREATE TABLE IF NOT EXISTS schedule
(
    id         SERIAL PRIMARY KEY,
    course_id  INTEGER NOT NULL REFERENCES course (id) ON DELETE CASCADE,
    semester   INTEGER NOT NULL,
    study_year TEXT    NOT NULL,
    unique (course_id, semester, study_year)
);