INSERT INTO roles (type) VALUES
    ('ADMIN'),
    ('USER');

INSERT INTO app_users (first_name, last_name, email, password, date_of_birth, locked, enabled) VALUES
('kauan', 'mocelin', 'kauan@gmail.com', '$2a$12$XZRJdISn/.xHRS5i3akCpOAVuJgfCktTpTNVmlZmS5ot5TYOaskKu', '1990-05-15', false, true);

INSERT INTO app_users_roles (app_user_id, roles_id) VALUES
(1, 2);