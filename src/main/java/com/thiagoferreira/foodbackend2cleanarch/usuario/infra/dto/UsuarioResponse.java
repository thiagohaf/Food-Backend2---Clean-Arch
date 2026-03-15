package com.thiagoferreira.foodbackend2cleanarch.usuario.infra.dto;

import java.util.UUID;

public record UsuarioResponse(
        UUID id,
        String nome,
        TipoUsuarioResponse tipoUsuario
) {
}
