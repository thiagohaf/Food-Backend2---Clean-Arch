package com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.rule.UsuarioValidador;

import java.util.UUID;

public class Usuario {
    private UUID id;
    private String nome;
    private TipoUsuario tipoUsuario;

    public UUID getId() {
        return id;
    }

    public String getNome() { return nome; }

    public TipoUsuario getTipoUsuario() { return tipoUsuario; }

    public Usuario(UUID id, String nome, TipoUsuario tipoUsuario) {
        this.id = id;
        this.nome = nome;
        this.tipoUsuario = tipoUsuario;
        validar();
    }

    public void validar() {
        UsuarioValidador.validar(this);
    }

    public void atualizar(String nome, TipoUsuario tipoUsuario) {
        this.nome = nome;
        this.tipoUsuario = tipoUsuario;
        validar();
    }
}
