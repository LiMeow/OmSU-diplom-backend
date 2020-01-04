CREATE TABLE IF NOT EXISTS auditory_occupation
(
    id            SERIAL PRIMARY KEY,
    auditory_id   INTEGER NOT NULL REFERENCES auditory (id) ON DELETE CASCADE,
    time_block_id INTEGER NOT NULL REFERENCES time_block (id) ON DELETE CASCADE,
    date          TEXT    NOT NULL,
    lecturer_id   INTEGER DEFAULT NULL REFERENCES lecturer (id),
    comment       TEXT    DEFAULT NULL
);