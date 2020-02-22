CREATE TABLE IF NOT EXISTS schedule_item
(
    id            SERIAL PRIMARY KEY,
    event_id      INTEGER NOT NULL REFERENCES event (id) ON DELETE CASCADE,
    discipline_id INTEGER NOT NULL REFERENCES discipline (id) ON DELETE CASCADE,
    schedule_id   INTEGER NOT NULL REFERENCES schedule (id) ON DELETE CASCADE,
    activity_type TEXT DEFAULT NULL
);