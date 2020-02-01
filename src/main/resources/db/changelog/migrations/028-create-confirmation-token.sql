CREATE TABLE IF NOT EXISTS confirmation_token
(
    id           SERIAL PRIMARY KEY,
    token        TEXT      DEFAULT NULL,
    user_id      INTEGER NOT NULL REFERENCES "user" (id) ON DELETE CASCADE,
    expired_date TIMESTAMP DEFAULT NULL
);