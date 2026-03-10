package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.mapper;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.dto.CriarItemCardapioInput;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.ItemCardapioCoreMapperException;

public class ItemCardapioCoreMapper {

    public ItemCardapio toDomain(CriarItemCardapioInput input) {
        if  (input == null) {
            throw new ItemCardapioCoreMapperException();
        }

        return new ItemCardapio(
                input.id(),
                input.nome(),
                input.descricao(),
                input.preco(),
                input.disponibilidadeLocal(),
                input.fotoPath(),
                input.restauranteId()
        );
    }
}
