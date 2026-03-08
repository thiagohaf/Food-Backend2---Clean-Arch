package com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain;

import java.util.UUID;

public abstract class UsuarioBase {
    protected UUID id;
    protected  String nomeTipo;

    public UUID getId() {
        return id;
    }

    public String getNomeTipo() {
        return nomeTipo;
    }

    public UsuarioBase(UUID id, String nomeTipo) {
        this.id = id;
        this.nomeTipo = nomeTipo;
    }
}
