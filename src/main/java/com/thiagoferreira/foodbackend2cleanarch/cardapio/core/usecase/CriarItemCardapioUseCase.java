package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.dto.CriarItemCardapioInput;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.ItemCardapioExistenteException;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.RestauranteNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.gateway.ItemCardapioGateway;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.mapper.ItemCardapioCoreMapper;

public class CriarItemCardapioUseCase {

    private final ItemCardapioGateway itemCardapioGateway;
    private final ItemCardapioCoreMapper mapper;

    public CriarItemCardapioUseCase(ItemCardapioGateway itemCardapioGateway, ItemCardapioCoreMapper mapper) {
        this.itemCardapioGateway = itemCardapioGateway;
        this.mapper = mapper;
    }

    public ItemCardapio executar(CriarItemCardapioInput input) {

        ItemCardapio novoItem = mapper.toDomain(input);

        if (!itemCardapioGateway.restauranteExiste(novoItem.getRestauranteId())) {
            throw new RestauranteNaoEncontradoException();
        }

        if (itemCardapioGateway.existeItemPorNomeERestaurante(novoItem.getNome(), novoItem.getRestauranteId())) {
            throw new ItemCardapioExistenteException();
        }

        return itemCardapioGateway.salvar(novoItem);
    }
}
