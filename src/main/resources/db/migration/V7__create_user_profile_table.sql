


CREATE TABLE user_profile (
                               profile_id UUID PRIMARY KEY,
                               user_id UUID NOT NULL UNIQUE,
                               first_name VARCHAR(255) NOT NULL,
                               last_name VARCHAR(255) NOT NULL,
                               bio VARCHAR(500),
                               image_url VARCHAR(512),
                               created_at TIMESTAMP NOT NULL,
                               updated_at TIMESTAMP NOT NULL,

                               CONSTRAINT fk_user
                                   FOREIGN KEY(user_id)
                                       REFERENCES my_users(user_id)
                                       ON DELETE CASCADE
);
