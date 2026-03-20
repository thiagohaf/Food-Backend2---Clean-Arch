package com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.RestauranteNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain.Restaurante;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.gateway.RestauranteGateway;

import java.util.UUID;

/**
 * Caso de uso que obtém restaurante por id ou sinaliza ausência via exceção de domínio.
 */
public class BuscarRestaurantePorIdUseCase {

    private final RestauranteGateway restauranteGateway;

    /**
     * @param restauranteGateway porta de consulta
     */
    public BuscarRestaurantePorIdUseCase(RestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    /**
     * @param id identificador do restaurante
     * @return restaurante encontrado
     */
    public Restaurante executar(UUID id) {
        return restauranteGateway.buscarPorId(id)
                .orElseThrow(RestauranteNaoEncontradoException::new);
    }
}
