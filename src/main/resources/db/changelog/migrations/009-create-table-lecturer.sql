CREATE TABLE IF NOT EXISTS lecturer
(
    id               SERIAL PRIMARY KEY,
    chair_id         INTEGER NOT NULL REFERENCES chair (id) ON DELETE CASCADE,
    personal_data_id INTEGER NOT NULL REFERENCES personal_data (id) ON DELETE CASCADE,
    enabled          BOOLEAN NOT NULL
);