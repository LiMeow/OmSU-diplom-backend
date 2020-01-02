CREATE TABLE IF NOT EXISTS schedules
(
    id               SERIAL PRIMARY KEY,
    course           INTEGER NOT NULL,
    semester         INTEGER NOT NULL,
    study_form       TEXT    NOT NULL,
    study_year       TEXT    NOT NULL,
    schedule_item_id INTEGER NOT NULL REFERENCES schedule_items (id) ON DELETE CASCADE
);