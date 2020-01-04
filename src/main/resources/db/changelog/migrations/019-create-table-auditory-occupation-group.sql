CREATE TABLE IF NOT EXISTS auditory_occupation_group
(
    auditory_occupation_id INTEGER REFERENCES auditory_occupation (id) ON DELETE CASCADE,
    group_id               INTEGER REFERENCES "group" (id) ON DELETE CASCADE
);