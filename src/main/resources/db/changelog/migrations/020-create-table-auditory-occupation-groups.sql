CREATE TABLE IF NOT EXISTS auditory_occupation_groups
(
    auditory_occupation_id INTEGER REFERENCES auditory_occupations (id) ON DELETE CASCADE,
    group_id               INTEGER REFERENCES groups (id) ON DELETE CASCADE
);