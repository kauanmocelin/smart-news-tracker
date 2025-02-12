INSERT INTO app_users (first_name, last_name, email, password, locked, enabled) VALUES
('Fulano', 'Da Silva', 'fulano@gmail.com', '$2a$10$VtqEayxdWPlfpCKNVi2wHeWhT/97jP2ND4ElnGUzza36atjr/R3M.', false, true);

INSERT INTO app_users_roles (app_user_id, roles_id) VALUES
    (2,2);