CREATE SEQUENCE sequence_generator
  INCREMENT 50
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1050
  CACHE 1;

CREATE TABLE nb_authority (
	name VARCHAR(50) NOT NULL,
	PRIMARY KEY (name)
);

CREATE TABLE nb_user (
	id BIGINT NOT NULL,
	login VARCHAR(50) NOT NULL,
	password_hash VARCHAR(60) NOT NULL,
	first_name VARCHAR(50) NULL DEFAULT NULL,
	last_name VARCHAR(50) NULL DEFAULT NULL,
	title VARCHAR(50),
	email VARCHAR(254) NULL DEFAULT NULL,
	image_url VARCHAR(256) NULL DEFAULT NULL,
	activated BOOLEAN NOT NULL,
	lang_key VARCHAR(6) NULL DEFAULT NULL,
	activation_key VARCHAR(20) NULL DEFAULT NULL,
	reset_key VARCHAR(20) NULL DEFAULT NULL,
	created_by VARCHAR(50) NOT NULL,
	created_date TIMESTAMP without time zone NOT NULL,
	reset_date TIMESTAMP without time zone,
	last_modified_by VARCHAR(50) NULL DEFAULT NULL,
	last_modified_date TIMESTAMP without time zone,
	PRIMARY KEY (id),
	CONSTRAINT ux_user_email UNIQUE (email),
    CONSTRAINT ux_user_login UNIQUE (login)
);
CREATE UNIQUE INDEX idx_user_email
  ON nb_user USING btree
  (email COLLATE pg_catalog."default");

CREATE UNIQUE INDEX idx_user_login
  ON nb_user
  USING btree
  (login COLLATE pg_catalog."default");

CREATE TABLE nb_user_authority (
	user_id BIGINT NOT NULL,
	authority_name VARCHAR(50) NOT NULL,
    CONSTRAINT jhi_user_authority_pkey PRIMARY KEY (user_id, authority_name),
    CONSTRAINT fk_authority_name FOREIGN KEY (authority_name)
      REFERENCES nb_authority (name) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id)
      REFERENCES nb_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);