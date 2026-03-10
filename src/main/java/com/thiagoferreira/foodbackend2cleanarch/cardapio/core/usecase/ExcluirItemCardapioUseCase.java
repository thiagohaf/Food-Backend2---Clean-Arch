package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.ItemCardapioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.gateway.ItemCardapioGateway;

import java.util.UUID;

public class ExcluirItemCardapioUseCase {

    private final ItemCardapioGateway itemCardapioGateway;

    public ExcluirItemCardapioUseCase(ItemCardapioGateway itemCardapioGateway) {
        this.itemCardapioGateway = itemCardapioGateway;
    }

    public void executar(UUID id) {
        if (itemCardapioGateway.buscarPorId(id).isEmpty()) {
            throw new ItemCardapioNaoEncontradoException();
        }

        itemCardapioGateway.excluir(id);
    }
}
