CREATE TABLE IF NOT EXISTS refresh_token (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(50) NOT NULL,
    expiry_date TIMESTAMP NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_token ON refresh_token(token);
CREATE INDEX IF NOT EXISTS idx_username ON refresh_token(username);
