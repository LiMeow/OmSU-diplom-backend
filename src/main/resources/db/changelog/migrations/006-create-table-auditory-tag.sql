CREATE TABLE IF NOT EXISTS auditory_tag
(
    auditory_id INTEGER NOT NULL REFERENCES auditory (id) ON DELETE CASCADE,
    tag_id      INTEGER NOT NULL REFERENCES tag (id) ON DELETE CASCADE,
    primary key (auditory_id, tag_id)
);