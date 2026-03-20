package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.ItemCardapioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.gateway.ItemCardapioGateway;

import java.util.UUID;

/**
 * Caso de uso que remove um item de cardápio após confirmar sua existência.
 */
public class ExcluirItemCardapioUseCase {

    private final ItemCardapioGateway itemCardapioGateway;

    /**
     * @param itemCardapioGateway porta de persistência de cardápio
     */
    public ExcluirItemCardapioUseCase(ItemCardapioGateway itemCardapioGateway) {
        this.itemCardapioGateway = itemCardapioGateway;
    }

    /**
     * @param id identificador do item a excluir
     */
    public void executar(UUID id) {
        if (itemCardapioGateway.buscarPorId(id).isEmpty()) {
            throw new ItemCardapioNaoEncontradoException();
        }

        itemCardapioGateway.excluir(id);
    }
}
