CREATE TABLE IF NOT EXISTS "group"
(
    id                 SERIAL PRIMARY KEY,
    study_direction_id INTEGER NOT NULL REFERENCES study_direction (id) ON DELETE CASCADE,
    name               TEXT    NOT NULL,
    UNIQUE (study_direction_id, name)
);