CREATE TABLE IF NOT EXISTS event
(
    id            SERIAL PRIMARY KEY,
    classroom_id  INTEGER NOT NULL REFERENCES classroom (id) ON DELETE CASCADE,
    time_block_id INTEGER NOT NULL REFERENCES time_block (id) ON DELETE CASCADE,
    day           TEXT    NOT NULL,
    date_from     date    NOT NULL,
    date_to       date    NOT NULL,
    interval      TEXT    NOT NULL,
    lecturer_id   INTEGER DEFAULT NULL REFERENCES lecturer (id),
    comment       TEXT    DEFAULT NULL,
    UNIQUE (classroom_id, time_block_id, day, date_from, date_to, interval)
);