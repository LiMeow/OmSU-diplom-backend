CREATE TABLE IF NOT EXISTS schedule_item
(
    id                     SERIAL PRIMARY KEY,
    auditory_occupation_id INTEGER NOT NULL REFERENCES auditory_occupation (id) ON DELETE CASCADE,
    discipline_id          INTEGER NOT NULL REFERENCES discipline (id) ON DELETE CASCADE,
    activity_type_id       INTEGER NOT NULL REFERENCES activity_type (id) ON DELETE CASCADE,
    schedule_id            INTEGER NOT NULL REFERENCES schedule (id) ON DELETE CASCADE
);