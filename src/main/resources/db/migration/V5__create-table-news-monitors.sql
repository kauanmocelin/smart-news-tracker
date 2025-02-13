CREATE TABLE news_monitors (
    id BIGSERIAL PRIMARY KEY,
    keyword VARCHAR(30) NOT NULL,
    monitoring_period VARCHAR(255) NOT NULL,
    app_users_id BIGSERIAL,
    CONSTRAINT fk_app_user
        FOREIGN KEY (app_users_id)
        REFERENCES app_users(id) ON DELETE CASCADE
);