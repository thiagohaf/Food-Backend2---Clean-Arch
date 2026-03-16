package com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemCardapioRequest(
        @NotBlank String nome,
        String descricao,
        @NotNull @Positive BigDecimal preco,
        @NotBlank String categoria,
        @NotNull UUID restauranteId
) {
}

