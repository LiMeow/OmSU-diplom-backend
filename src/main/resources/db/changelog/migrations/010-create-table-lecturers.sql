CREATE TABLE IF NOT EXISTS lecturers
(
    id               SERIAL PRIMARY KEY,
    chair_id         INTEGER NOT NULL REFERENCES chairs (id) ON DELETE CASCADE,
    personal_data_id INTEGER NOT NULL REFERENCES personal_datas (id) ON DELETE CASCADE,
    enabled          BOOLEAN NOT NULL
);