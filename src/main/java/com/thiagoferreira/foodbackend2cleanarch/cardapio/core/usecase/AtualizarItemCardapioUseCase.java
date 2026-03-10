package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.dto.AtualizarItemCardapioInput;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.ItemCardapioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.gateway.ItemCardapioGateway;

import java.util.UUID;

public class AtualizarItemCardapioUseCase {

    private ItemCardapioGateway itemCardapioGateway;

    public AtualizarItemCardapioUseCase(ItemCardapioGateway itemCardapioGateway) {
        this.itemCardapioGateway = itemCardapioGateway;
    }

    public ItemCardapio executar(UUID id, AtualizarItemCardapioInput input) {
        ItemCardapio item = itemCardapioGateway.buscarPorId(id)
                .orElseThrow(() -> new ItemCardapioNaoEncontradoException());

        item.atualizar(
                input.nome(),
                input.descricao(),
                input.preco(),
                input.disponibilidadeLocal(),
                input.fotoPath()
        );

        return itemCardapioGateway.salvar(item);
    }
}
