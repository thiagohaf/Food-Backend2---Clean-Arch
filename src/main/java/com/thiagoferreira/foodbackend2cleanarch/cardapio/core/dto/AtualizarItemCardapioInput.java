package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.dto;

import java.math.BigDecimal;

public record AtualizarItemCardapioInput(
        String nome,
        String descricao,
        BigDecimal preco,
        boolean disponibilidadeLocal,
        String fotoPath
) {
}
