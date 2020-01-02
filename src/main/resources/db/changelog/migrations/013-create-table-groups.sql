CREATE TABLE IF NOT EXISTS groups
(
    id                 SERIAL PRIMARY KEY,
    study_direction_id INTEGER NOT NULL REFERENCES study_directions (id) ON DELETE CASCADE,
    name               TEXT    NOT NULL,
    UNIQUE (study_direction_id, name)
);