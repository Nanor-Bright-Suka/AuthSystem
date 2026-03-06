

CREATE TABLE my_users (
                        user_id UUID PRIMARY KEY,
                       firstname VARCHAR(100) NOT NULL,
                       lastname VARCHAR(100) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP NULL
);
