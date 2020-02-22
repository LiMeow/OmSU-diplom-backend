CREATE TABLE IF NOT EXISTS classroom
(
    id          SERIAL PRIMARY KEY,
    building_id INTEGER NOT NULL REFERENCES building (id) ON DELETE CASCADE,
    number      TEXT    NOT NULL,
    unique (building_id, number)
);