CREATE TABLE IF NOT EXISTS students
(
    id               SERIAL PRIMARY KEY,
    group_id         INTEGER NOT NULL REFERENCES groups (id) ON DELETE CASCADE,
    personal_data_id INTEGER NOT NULL REFERENCES personal_datas (id) ON DELETE CASCADE
);