CREATE TABLE IF NOT EXISTS auditory_occupations
(
    id            SERIAL PRIMARY KEY,
    auditory_id   INTEGER NOT NULL REFERENCES auditories (id) ON DELETE CASCADE,
    time_block_id INTEGER NOT NULL REFERENCES time_blocks (id) ON DELETE CASCADE,
    date          TEXT    NOT NULL,
    lecturer_id   INTEGER DEFAULT NULL REFERENCES lecturers (id),
    comment       TEXT    DEFAULT NULL
);