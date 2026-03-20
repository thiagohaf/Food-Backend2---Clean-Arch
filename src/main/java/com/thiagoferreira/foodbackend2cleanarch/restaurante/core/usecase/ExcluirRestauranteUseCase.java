package com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.RestauranteNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.gateway.RestauranteGateway;

import java.util.UUID;

/**
 * Caso de uso que exclui um restaurante após verificar existência.
 */
public class ExcluirRestauranteUseCase {

    private RestauranteGateway restauranteGateway;

    /**
     * @param restauranteGateway porta de persistência
     */
    public ExcluirRestauranteUseCase(RestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    /**
     * @param id identificador do restaurante
     */
    public void executar(UUID id) {
        if (restauranteGateway.buscarPorId(id).isEmpty()) {
            throw new RestauranteNaoEncontradoException();
        }

        restauranteGateway.excluir(id);
    }
}
