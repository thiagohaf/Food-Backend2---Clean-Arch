package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.dto.CriarItemCardapioInput;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.ItemCardapioExistenteException;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.RestauranteNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.gateway.ItemCardapioGateway;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.mapper.ItemCardapioCoreMapper;

/**
 * Caso de uso que orquestra a criação de um item de cardápio: valida restaurante existente,
 * impede duplicidade de nome no mesmo restaurante e persiste o agregado (foto apenas como referência {@code fotoPath} no domínio).
 */
public class CriarItemCardapioUseCase {

    private final ItemCardapioGateway itemCardapioGateway;
    private final ItemCardapioCoreMapper mapper;

    /**
     * @param itemCardapioGateway porta de persistência e consultas de cardápio
     * @param mapper conversão de DTO de entrada para domínio
     */
    public CriarItemCardapioUseCase(ItemCardapioGateway itemCardapioGateway, ItemCardapioCoreMapper mapper) {
        this.itemCardapioGateway = itemCardapioGateway;
        this.mapper = mapper;
    }

    /**
     * @param input dados do novo item (incluindo referência de foto, não arquivo binário)
     * @return item persistido
     */
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
