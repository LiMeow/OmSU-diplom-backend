CREATE TABLE IF NOT EXISTS faculties
(
    id          SERIAL PRIMARY KEY,
    building_id INTEGER     NOT NULL REFERENCES buildings (id) ON DELETE CASCADE,
    name        TEXT UNIQUE NOT NULL
);