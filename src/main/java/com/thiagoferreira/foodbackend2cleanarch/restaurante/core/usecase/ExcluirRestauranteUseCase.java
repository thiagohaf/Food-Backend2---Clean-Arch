package com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.RestauranteNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.gateway.RestauranteGateway;

import java.util.UUID;

public class ExcluirRestauranteUseCase {

    private RestauranteGateway restauranteGateway;

    public ExcluirRestauranteUseCase(RestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public void executar(UUID id) {
        if (restauranteGateway.buscarPorId(id).isEmpty()) {
            throw new RestauranteNaoEncontradoException();
        }

        restauranteGateway.excluir(id);
    }
}
