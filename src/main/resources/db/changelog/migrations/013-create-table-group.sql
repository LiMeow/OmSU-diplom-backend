CREATE TABLE IF NOT EXISTS "group"
(
    id                 SERIAL PRIMARY KEY,
    study_direction_id INTEGER NOT NULL REFERENCES study_direction (id) ON DELETE CASCADE,
    course_id          INTEGER NOT NULL REFERENCES course (id) ON DELETE CASCADE,
    name               TEXT    NOT NULL,
    formation_year     TEXT    NOT NULL,
    dissolution_year   TEXT DEFAULT NULL,
    UNIQUE (study_direction_id, name, course_id)
);