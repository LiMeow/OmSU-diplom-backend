CREATE TABLE IF NOT EXISTS auditories
(
    id          SERIAL PRIMARY KEY,
    building_id INTEGER NOT NULL REFERENCES buildings (id) ON DELETE CASCADE,
    number      TEXT    NOT NULL,
    unique (building_id, number)
);