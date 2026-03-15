package com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.dto;

import java.util.UUID;

public record RestauranteResponse(
        UUID id,
        String nome,
        String endereco,
        String tipoCozinha,
        String horarioFuncionamento,
        UUID donoId
) {
}
