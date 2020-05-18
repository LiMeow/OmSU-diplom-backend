create TABLE IF NOT EXISTS discipline
(
    id   SERIAL PRIMARY KEY,
    name TEXT UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS discipline_requirements
(
    discipline_id  INTEGER NOT NULL REFERENCES discipline (id) ON DELETE CASCADE,
    requirement_id INTEGER NOT NULL REFERENCES tag (id) ON DELETE CASCADE,
    primary key (discipline_id, requirement_id)
);