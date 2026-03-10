package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.gateway.ItemCardapioGateway;

import java.util.List;
import java.util.UUID;

public class ListarItensCardapioPorRestauranteUseCase {

    private final ItemCardapioGateway itemCardapioGateway;

    public ListarItensCardapioPorRestauranteUseCase(ItemCardapioGateway itemCardapioGateway) {
        this.itemCardapioGateway = itemCardapioGateway;
    }

    public List<ItemCardapio> executar(UUID id) {
        return itemCardapioGateway.listarPorRestaurante(id);
    }
}
