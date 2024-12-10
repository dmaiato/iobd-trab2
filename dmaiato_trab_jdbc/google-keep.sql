DROP DATABASE IF EXISTS google_keep;

CREATE DATABASE google_keep;

\c google_keep;

CREATE TABLE anotacao (
  id SERIAL PRIMARY KEY,
  titulo TEXT NOT NULL,
  data_hora timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  descricao TEXT NOT NULL,
  cor CHAR(7) NOT NULL,
  foto bytea
);

DROP ROLE IF EXISTS adm;
CREATE ROLE adm WITH PASSWORD '123' LOGIN SUPERUSER;