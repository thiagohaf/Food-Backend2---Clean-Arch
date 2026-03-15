-- Flyway migration V1: create table restaurantes (espelho da RestauranteEntity)
CREATE TABLE restaurantes (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    tipo_cozinha VARCHAR(255) NOT NULL,
    horario_funcionamento VARCHAR(255) NOT NULL,
    dono_id VARCHAR(36) NOT NULL,
    CONSTRAINT uk_restaurantes_nome_endereco UNIQUE (nome, endereco)
);
