
CREATE TABLE roles (
                       role_id UUID PRIMARY KEY,
                       role_name VARCHAR(50) NOT NULL UNIQUE,
                       created_at TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP NULL
);
