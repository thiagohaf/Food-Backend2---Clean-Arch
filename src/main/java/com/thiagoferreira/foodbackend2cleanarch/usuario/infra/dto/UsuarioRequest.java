package com.thiagoferreira.foodbackend2cleanarch.usuario.infra.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UsuarioRequest(
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotNull(message = "tipoUsuarioId é obrigatório") UUID tipoUsuarioId
) {
}
