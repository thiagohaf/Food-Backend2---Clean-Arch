package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CriarItemCardapioInput(
        UUID id,
        String nome,
        String descricao,
        BigDecimal preco,
        boolean disponibilidadeLocal,
        String fotoPath,
        UUID restauranteId
) {
}
