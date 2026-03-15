-- Flyway migration V3: create table usuarios (espelho da UsuarioEntity)
CREATE TABLE usuarios (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    tipo_usuario_id VARCHAR(36) NOT NULL,
    data_cadastro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_usuarios_tipo_usuario FOREIGN KEY (tipo_usuario_id) REFERENCES tipos_usuario(id)
);
