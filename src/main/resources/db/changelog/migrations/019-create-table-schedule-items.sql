CREATE TABLE IF NOT EXISTS schedule_items
(
    id                     SERIAL PRIMARY KEY,
    auditory_occupation_id INTEGER NOT NULL REFERENCES auditory_occupations (id) ON DELETE CASCADE,
    discipline_id          INTEGER NOT NULL REFERENCES disciplines (id) ON DELETE CASCADE,
    activity_type_id       INTEGER NOT NULL REFERENCES activity_types (id) ON DELETE CASCADE,
    schedule_id            INTEGER NOT NULL REFERENCES schedules (id) ON DELETE CASCADE
);