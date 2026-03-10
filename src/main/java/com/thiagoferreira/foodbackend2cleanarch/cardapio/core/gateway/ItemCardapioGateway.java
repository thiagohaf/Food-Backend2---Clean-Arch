package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.gateway;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;

import java.util.UUID;

public interface ItemCardapioGateway {

    ItemCardapio salvar(ItemCardapio itemCardapio);
    boolean restauranteExiste(UUID restauranteId);
    boolean existeItemPorNomeERestaurante(String nome, UUID restauranteId);
}
