package com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain.Restaurante;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.gateway.RestauranteGateway;

import java.util.List;

public class ListarRestaurantesUseCase {

    private RestauranteGateway restauranteGateway;

    public ListarRestaurantesUseCase(RestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public List<Restaurante> executar() {
        return restauranteGateway.listarTodos();
    }
}
