package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.gateway.ItemCardapioGateway;

import java.util.List;
import java.util.UUID;

/**
 * Caso de uso que retorna o cardápio completo (lista de itens) de um restaurante identificado pelo {@code id}.
 */
public class ListarItensCardapioPorRestauranteUseCase {

    private final ItemCardapioGateway itemCardapioGateway;

    /**
     * @param itemCardapioGateway porta de consulta de itens
     */
    public ListarItensCardapioPorRestauranteUseCase(ItemCardapioGateway itemCardapioGateway) {
        this.itemCardapioGateway = itemCardapioGateway;
    }

    /**
     * @param id identificador do restaurante
     * @return itens do cardápio desse restaurante
     */
    public List<ItemCardapio> executar(UUID id) {
        return itemCardapioGateway.listarPorRestaurante(id);
    }
}
