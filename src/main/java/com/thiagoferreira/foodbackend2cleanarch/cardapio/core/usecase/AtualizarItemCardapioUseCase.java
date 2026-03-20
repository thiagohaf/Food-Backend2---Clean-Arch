package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.dto.AtualizarItemCardapioInput;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.ItemCardapioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.gateway.ItemCardapioGateway;

import java.util.UUID;

/**
 * Caso de uso que aplica alterações em um item existente (dados cadastrais, preço, disponibilidade local e referência de foto).
 */
public class AtualizarItemCardapioUseCase {

    private ItemCardapioGateway itemCardapioGateway;

    /**
     * @param itemCardapioGateway porta de persistência de cardápio
     */
    public AtualizarItemCardapioUseCase(ItemCardapioGateway itemCardapioGateway) {
        this.itemCardapioGateway = itemCardapioGateway;
    }

    /**
     * @param id identificador do item
     * @param input novos valores (foto como {@code fotoPath})
     * @return item atualizado persistido
     */
    public ItemCardapio executar(UUID id, AtualizarItemCardapioInput input) {
        ItemCardapio item = itemCardapioGateway.buscarPorId(id)
                .orElseThrow(ItemCardapioNaoEncontradoException::new);

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
