CREATE TABLE IF NOT EXISTS schedules
(
    schedule_header_id INTEGER REFERENCES schedule_headers (id) ON DELETE CASCADE,
    schedule_item_id   INTEGER REFERENCES schedule_items (id) ON DELETE CASCADE
);