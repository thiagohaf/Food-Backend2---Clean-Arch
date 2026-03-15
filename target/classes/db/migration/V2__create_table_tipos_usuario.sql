-- Flyway migration V2: create table tipos_usuario (espelho da TipoUsuarioEntity)
CREATE TABLE tipos_usuario (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE
);
