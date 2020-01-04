CREATE TABLE IF NOT EXISTS schedule
(
    id         SERIAL PRIMARY KEY,
    course     INTEGER NOT NULL,
    semester   INTEGER NOT NULL,
    study_form TEXT    NOT NULL,
    study_year TEXT    NOT NULL
);