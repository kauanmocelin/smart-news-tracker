CREATE TABLE registration_codes (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    created_at TIMESTAMP(6),
    expires_at TIMESTAMP(6),
    confirmed_at TIMESTAMP(6),
    app_user_id BIGINT NOT NULL,
    CONSTRAINT fk_app_user
        FOREIGN KEY (app_user_id)
        REFERENCES app_users(id) ON DELETE CASCADE
);
