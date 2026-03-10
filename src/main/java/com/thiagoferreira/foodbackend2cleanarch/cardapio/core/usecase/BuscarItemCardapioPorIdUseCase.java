package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.ItemCardapioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.gateway.ItemCardapioGateway;

import java.util.UUID;

public class BuscarItemCardapioPorIdUseCase {

    private final ItemCardapioGateway itemCardapioGateway;

    public BuscarItemCardapioPorIdUseCase(ItemCardapioGateway itemCardapioGateway) {
        this.itemCardapioGateway = itemCardapioGateway;
    }

    public ItemCardapio executar(UUID id) {
        return itemCardapioGateway.buscarPorId(id)
                .orElseThrow(() -> new ItemCardapioNaoEncontradoException());
    }
}
