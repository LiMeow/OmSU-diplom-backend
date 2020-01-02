CREATE TABLE IF NOT EXISTS auditory_tags
(
    auditory_id INTEGER NOT NULL REFERENCES auditories (id) ON DELETE CASCADE,
    tag_id      INTEGER NOT NULL REFERENCES tags (id) ON DELETE CASCADE,
    primary key (auditory_id, tag_id)
);