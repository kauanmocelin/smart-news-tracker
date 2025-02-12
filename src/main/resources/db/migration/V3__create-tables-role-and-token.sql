CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE tokens (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    token_type VARCHAR(255) NOT NULL,
    expired BOOLEAN NOT NULL,
    revoked BOOLEAN NOT NULL,
    app_user_id BIGSERIAL,
    CONSTRAINT fk_app_user
        FOREIGN KEY (app_user_id)
        REFERENCES app_users(id) ON DELETE CASCADE
);

CREATE TABLE app_users_roles (
    app_user_id BIGSERIAL NOT NULL,
    roles_id BIGSERIAL NOT NULL,
    PRIMARY KEY (app_user_id, roles_id),
    FOREIGN KEY (app_user_id)
        REFERENCES app_users(id) ON DELETE CASCADE,
    FOREIGN KEY (roles_id)
        REFERENCES roles(id) ON DELETE CASCADE
);