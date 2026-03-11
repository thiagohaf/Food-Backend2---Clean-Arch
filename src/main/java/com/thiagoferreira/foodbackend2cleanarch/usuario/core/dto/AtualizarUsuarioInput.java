package com.thiagoferreira.foodbackend2cleanarch.usuario.core.dto;

import java.util.UUID;

public record AtualizarUsuarioInput(
        String nome,
        UUID tipoUsuarioId
) {
}
