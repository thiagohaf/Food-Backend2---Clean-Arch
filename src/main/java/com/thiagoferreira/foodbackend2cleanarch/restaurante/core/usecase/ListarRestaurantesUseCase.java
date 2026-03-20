package com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain.Restaurante;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.gateway.RestauranteGateway;

import java.util.List;

/**
 * Caso de uso que lista todos os restaurantes cadastrados.
 */
public class ListarRestaurantesUseCase {

    private RestauranteGateway restauranteGateway;

    /**
     * @param restauranteGateway porta de listagem
     */
    public ListarRestaurantesUseCase(RestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    /**
     * @return coleção de restaurantes
     */
    public List<Restaurante> executar() {
        return restauranteGateway.listarTodos();
    }
}
