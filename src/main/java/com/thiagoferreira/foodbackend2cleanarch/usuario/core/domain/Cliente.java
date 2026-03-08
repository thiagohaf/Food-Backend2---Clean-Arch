package com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain;

import java.util.UUID;

public class Cliente extends UsuarioBase {
    public Cliente(UUID id, String nomeTipo) {
        super(id, nomeTipo);
    }
}
