package com.thiagoferreira.foodbackend2cleanarch.usuario.infra.dto;

import java.util.UUID;

public record TipoUsuarioResponse(
        UUID id,
        String nome
) {
}
