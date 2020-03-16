CREATE TABLE IF NOT EXISTS schedule_item_group
(
    schedule_item_id INTEGER REFERENCES schedule_item (id) ON DELETE CASCADE,
    group_id         INTEGER REFERENCES "group" (id) ON DELETE CASCADE
);