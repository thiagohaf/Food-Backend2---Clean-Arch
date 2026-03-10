package com.thiagoferreira.foodbackend2cleanarch.restaurante.core.dto;

public record AtualizarRestauranteInput(
        String nome,
        String endereco,
        String tipoCozinha,
        String horarioFuncionamento
) {
}
