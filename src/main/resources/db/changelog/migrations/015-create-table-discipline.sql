create TABLE IF NOT EXISTS discipline
(
    id   SERIAL PRIMARY KEY,
    name TEXT UNIQUE NOT NULL
);