CREATE TABLE IF NOT EXISTS schedule_headers
(
    id               SERIAL PRIMARY KEY,
    course           INTEGER NOT NULL,
    semester         INTEGER NOT NULL,
    study_form       TEXT    NOT NULL,
    study_year       TEXT    NOT NULL,
    schedule_item_id INTEGER NOT NULL REFERENCES schedule_items (id) ON DELETE CASCADE
);