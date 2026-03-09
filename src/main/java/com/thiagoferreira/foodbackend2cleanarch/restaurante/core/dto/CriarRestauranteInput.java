package com.thiagoferreira.foodbackend2cleanarch.restaurante.core.dto;

import java.util.UUID;

public record CriarRestauranteInput(
        UUID id,
        String nome,
        String endereco,
        String tipoCozinha,
        String horarioFuncionamento,
        UUID donoId
) { }
