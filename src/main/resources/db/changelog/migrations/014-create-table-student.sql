CREATE TABLE IF NOT EXISTS student
(
    id               SERIAL PRIMARY KEY,
    group_id         INTEGER NOT NULL REFERENCES "group" (id) ON DELETE CASCADE,
    personal_data_id INTEGER NOT NULL REFERENCES personal_data (id) ON DELETE CASCADE
);