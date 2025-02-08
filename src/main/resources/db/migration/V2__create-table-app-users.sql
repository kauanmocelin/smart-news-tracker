CREATE TABLE app_users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    app_user_role VARCHAR(255),
    locked BOOLEAN,
    enabled BOOLEAN
);
