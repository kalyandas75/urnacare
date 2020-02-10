INSERT INTO nb_authority(name) VALUES
('ROLE_ADMIN');

INSERT INTO nb_user(id, created_by, created_date, last_modified_by, last_modified_date, activated, activation_key, email, first_name, image_url, lang_key, last_name, login, password_hash) VALUES
(1, 'system', now(), 'system', NULL, true, NULL, 'system@localhost', 'System', NULL, 'en', 'System', 'system', '$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG'),
(2, 'system', now(), 'system', NULL, true, NULL, 'anonymous@localhost', 'Anonymous', NULL, 'en', 'User', 'anonymoususer', '$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO'),
(3, 'system', now(), 'system', NULL, true, NULL, 'admin@localhost', 'Administrator', NULL, 'en', 'Administrator', 'admin', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC');

INSERT INTO nb_user_authority(user_id, authority_name) VALUES
(1, 'ROLE_ADMIN');