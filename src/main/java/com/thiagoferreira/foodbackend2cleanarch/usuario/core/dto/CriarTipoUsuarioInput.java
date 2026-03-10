package com.thiagoferreira.foodbackend2cleanarch.usuario.core.dto;

import java.util.UUID;

public record CriarTipoUsuarioInput(
        UUID id,
        String nome
) {
}
