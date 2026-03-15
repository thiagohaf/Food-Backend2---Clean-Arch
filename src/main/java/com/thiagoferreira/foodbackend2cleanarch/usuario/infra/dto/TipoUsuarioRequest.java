package com.thiagoferreira.foodbackend2cleanarch.usuario.infra.dto;

import jakarta.validation.constraints.NotBlank;

public record TipoUsuarioRequest(
        @NotBlank(message = "Nome é obrigatório") String nome
) {
}
