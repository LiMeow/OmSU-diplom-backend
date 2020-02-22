CREATE TABLE IF NOT EXISTS classroom_tag
(
    classroom_id INTEGER NOT NULL REFERENCES classroom (id) ON DELETE CASCADE,
    tag_id       INTEGER NOT NULL REFERENCES tag (id) ON DELETE CASCADE,
    primary key (classroom_id, tag_id)
);