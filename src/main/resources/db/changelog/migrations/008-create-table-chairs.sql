create TABLE IF NOT EXISTS chairs
(
    id         SERIAL PRIMARY KEY,
    name       TEXT    NOT NULL,
    faculty_id INTEGER NOT NULL REFERENCES faculties (id) ON DELETE CASCADE
);