package com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.RestauranteNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain.Restaurante;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.gateway.RestauranteGateway;

import java.util.UUID;

public class BuscarRestaurantePorIdUseCase {

    private final RestauranteGateway restauranteGateway;

    public BuscarRestaurantePorIdUseCase(RestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public Restaurante executar(UUID id) {
        return restauranteGateway.buscarPorId(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException());
    }
}
