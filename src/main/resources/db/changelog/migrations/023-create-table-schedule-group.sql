CREATE TABLE IF NOT EXISTS schedule_group
(
    schedule_id INTEGER REFERENCES schedule (id) ON DELETE CASCADE,
    group_id    INTEGER REFERENCES "group" (id) ON DELETE CASCADE
);