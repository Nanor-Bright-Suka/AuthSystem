
CREATE TABLE my_refresh_tokens (
                                   token_id UUID PRIMARY KEY,
                                   user_id UUID NOT NULL,

                                   token_hash VARCHAR(512) NOT NULL,

                                   expires_at TIMESTAMP NOT NULL,
                                   created_at TIMESTAMP NOT NULL,

                                   revoked BOOLEAN NOT NULL,
                                   revoked_at TIMESTAMP,

                                   CONSTRAINT fk_my_refresh_tokens_user
                                       FOREIGN KEY (user_id)
                                           REFERENCES my_users (user_id)
                                           ON DELETE CASCADE
);
