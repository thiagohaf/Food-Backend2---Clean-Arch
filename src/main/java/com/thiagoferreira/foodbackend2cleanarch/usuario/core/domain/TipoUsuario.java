package com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.rule.TipoUsuarioValidador;

import java.util.UUID;

public class TipoUsuario {
    private UUID id;
    private String nome;

    public TipoUsuario(UUID id, String nome) {
        this.id = id;
        this.nome = nome;
        validar();
    }

    private void validar() {
        TipoUsuarioValidador.validar(this);
    }

    public UUID getId() { return id; }
    public String getNome() { return nome; }
}
