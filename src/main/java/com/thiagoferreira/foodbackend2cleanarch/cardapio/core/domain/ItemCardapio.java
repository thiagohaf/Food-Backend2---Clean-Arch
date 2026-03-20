package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.rule.ItemCardapioValidador;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Entidade de domínio que representa um item do cardápio vinculado a um restaurante.
 * <p>
 * A foto é modelada apenas como {@code fotoPath}: referência ao arquivo já tratado fora do domínio,
 * reduzindo acoplamento e custo de persistência em relação a armazenar binários na entidade.
 */
public class ItemCardapio {
    private UUID id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private boolean disponibilidadeLocal;
    private String fotoPath;
    private UUID restauranteId;

    /**
     * @return identificador persistido do item, se já existir
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return nome comercial do item
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return texto descritivo apresentado ao cliente
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @return preço unitário do item
     */
    public BigDecimal getPreco() {
        return preco;
    }

    /**
     * @return indica se o item está disponível para consumo no estabelecimento
     */
    public boolean getDisponibilidadeLocal() {
        return disponibilidadeLocal;
    }

    /**
     * @return caminho ou chave lógica da foto já resolvida na camada de infraestrutura
     */
    public String getFotoPath() {
        return fotoPath;
    }

    /**
     * @return identificador do restaurante dono do item
     */
    public UUID getRestauranteId() {
        return restauranteId;
    }

    /**
     * @param nome nome do item
     * @param descricao descrição do item
     * @param preco preço unitário
     * @param disponibilidadeLocal disponibilidade para consumo local
     * @param fotoPath referência à foto (não armazena bytes)
     * @param restauranteId restaurante proprietário
     */
    public ItemCardapio(String nome, String descricao, BigDecimal preco, boolean disponibilidadeLocal, String fotoPath, UUID restauranteId) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.disponibilidadeLocal = disponibilidadeLocal;
        this.fotoPath = fotoPath;
        this.restauranteId = restauranteId;

        validar();
    }

    /**
     * @param id identificador existente
     * @param nome nome do item
     * @param descricao descrição do item
     * @param preco preço unitário
     * @param disponibilidadeLocal disponibilidade para consumo local
     * @param fotoPath referência à foto (não armazena bytes)
     * @param restauranteId restaurante proprietário
     */
    public ItemCardapio(UUID id, String nome, String descricao, BigDecimal preco,
                        boolean disponibilidadeLocal, String fotoPath, UUID restauranteId) {
        this(nome, descricao, preco, disponibilidadeLocal, fotoPath, restauranteId);
        this.id = id;
    }

    private void validar() {
        ItemCardapioValidador.validar(this);
    }

    /**
     * @param preco novo preço unitário
     */
    public void alterarPreco(BigDecimal preco) {
        this.preco = preco;
        validar();
    }

    /**
     * @param nome nome atualizado
     * @param descricao descrição atualizada
     * @param preco preço atualizado
     * @param disponibilidadeLocal disponibilidade atualizada
     * @param fotoPath nova referência de foto
     */
    public void atualizar(String nome, String descricao, BigDecimal preco, boolean disponibilidadeLocal, String fotoPath) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.disponibilidadeLocal = disponibilidadeLocal;
        this.fotoPath = fotoPath;

        validar();
    }
}
