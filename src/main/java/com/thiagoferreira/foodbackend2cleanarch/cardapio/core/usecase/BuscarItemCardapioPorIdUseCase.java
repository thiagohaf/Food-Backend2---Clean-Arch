package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.ItemCardapioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.gateway.ItemCardapioGateway;

import java.util.UUID;

/**
 * Caso de uso que recupera um item de cardápio por identificador ou falha se não existir.
 */
public class BuscarItemCardapioPorIdUseCase {

    private final ItemCardapioGateway itemCardapioGateway;

    /**
     * @param itemCardapioGateway porta de consulta
     */
    public BuscarItemCardapioPorIdUseCase(ItemCardapioGateway itemCardapioGateway) {
        this.itemCardapioGateway = itemCardapioGateway;
    }

    /**
     * @param id identificador do item
     * @return item encontrado
     */
    public ItemCardapio executar(UUID id) {
        return itemCardapioGateway.buscarPorId(id)
                .orElseThrow(ItemCardapioNaoEncontradoException::new);
    }
}
