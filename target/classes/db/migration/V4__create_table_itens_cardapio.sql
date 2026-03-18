-- Flyway migration V4: create table itens_cardapio (espelho da ItemCardapioEntity)
CREATE TABLE itens_cardapio (
    id CHAR(36) NOT NULL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    preco DECIMAL(10,2) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    restaurante_id CHAR(36) NOT NULL,
    data_cadastro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_itens_cardapio_restaurante FOREIGN KEY (restaurante_id) REFERENCES restaurantes(id)
);
