CREATE SEQUENCE sequence_generator
  INCREMENT 50
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1050
  CACHE 1;

CREATE TABLE nd_authority (
	name VARCHAR(50) NOT NULL,
	PRIMARY KEY (name)
);

CREATE TABLE nd_user (
	id BIGINT NOT NULL,
	password_hash VARCHAR(60) NOT NULL,
	first_name VARCHAR(50) NULL DEFAULT NULL,
	last_name VARCHAR(50) NULL DEFAULT NULL,
	title VARCHAR(50),
	email VARCHAR(254) NOT NULL,
	image_url VARCHAR(256) NULL DEFAULT NULL,
	newsletter_subscription BOOLEAN,
	phone_number VARCHAR(15),
	birth_date DATE,
	birth_place VARCHAR(100),
	nationality_code VARCHAR(50),
	permit_number VARCHAR(50),
	permit_country_code VARCHAR(50),
	permit_date DATE,
	iban VARCHAR(50),
	bic VARCHAR(50),
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
	CONSTRAINT ux_nd_user_email UNIQUE (email)
);
CREATE UNIQUE INDEX idx_nd_user_email
  ON nd_user USING btree
  (email COLLATE pg_catalog."default");


CREATE TABLE nd_user_authority (
	user_id BIGINT NOT NULL,
	authority_name VARCHAR(50) NOT NULL,
    CONSTRAINT nd_user_authority_pkey PRIMARY KEY (user_id, authority_name),
    CONSTRAINT fk_authority_name FOREIGN KEY (authority_name)
      REFERENCES nd_authority (name) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id)
      REFERENCES nd_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);