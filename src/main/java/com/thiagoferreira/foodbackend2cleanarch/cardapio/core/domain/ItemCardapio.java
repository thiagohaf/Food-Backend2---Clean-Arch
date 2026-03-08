package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.rule.ItemCardapioValidador;

import java.math.BigDecimal;
import java.util.UUID;

public class ItemCardapio {
    private UUID id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private boolean disponibilidadeLocal;
    private String fotoPath;
    private UUID restauranteId;

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public boolean getDisponibilidadeLocal() {
        return disponibilidadeLocal;
    }

    public String getFotoPath() {
        return fotoPath;
    }

    public UUID getRestauranteId() {
        return restauranteId;
    }

    public ItemCardapio(String nome, String descricao, BigDecimal preco, boolean disponibilidadeLocal, String fotoPath, UUID restauranteId) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.disponibilidadeLocal = disponibilidadeLocal;
        this.fotoPath = fotoPath;
        this.restauranteId = restauranteId;

        validar();
    }

    public ItemCardapio(UUID id, String nome, String descricao, BigDecimal preco,
                        boolean disponibilidadeLocal, String fotoPath, UUID restauranteId) {
        this(nome, descricao, preco, disponibilidadeLocal, fotoPath, restauranteId);
        this.id = id;
    }

    private void validar() {
        ItemCardapioValidador.validar(this);
    }

    public void alterarPreco(BigDecimal preco) {
        this.preco = preco;
        validar();
    }
}
