package com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemCardapioResponse(
        UUID id,
        String nome,
        String descricao,
        BigDecimal preco,
        String categoria,
        UUID restauranteId
) {
}

