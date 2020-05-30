CREATE TABLE IF NOT EXISTS event
(
    id          SERIAL PRIMARY KEY,
    lecturer_id INTEGER NOT NULL REFERENCES lecturer (id),
    comment     TEXT    DEFAULT NULL,
    required    BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS event_period
(
    id            SERIAL PRIMARY KEY,
    event_id      INTEGER NOT NULL REFERENCES event (id) ON DELETE CASCADE,
    classroom_id  INTEGER NOT NULL REFERENCES classroom (id) ON DELETE CASCADE,
    time_block_id INTEGER NOT NULL REFERENCES time_block (id) ON DELETE CASCADE,
    day           TEXT    NOT NULL,
    date_from     date    NOT NULL,
    date_to       date    NOT NULL,
    interval      TEXT    NOT NULL
);