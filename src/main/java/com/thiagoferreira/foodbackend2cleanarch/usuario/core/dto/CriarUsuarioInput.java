package com.thiagoferreira.foodbackend2cleanarch.usuario.core.dto;

import java.util.UUID;

public record CriarUsuarioInput(
        UUID id,
        String nome,
        UUID tipoUsuarioId
) {
}
