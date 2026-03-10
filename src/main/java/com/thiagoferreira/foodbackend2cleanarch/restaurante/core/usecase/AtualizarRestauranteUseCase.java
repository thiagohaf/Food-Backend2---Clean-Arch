package com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.RestauranteNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain.Restaurante;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.dto.AtualizarRestauranteInput;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.gateway.RestauranteGateway;

import java.util.UUID;

public class AtualizarRestauranteUseCase {

    private RestauranteGateway restauranteGateway;

    public AtualizarRestauranteUseCase(RestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public Restaurante executar(UUID id, AtualizarRestauranteInput input) {

        Restaurante restaurante = restauranteGateway.buscarPorId(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException());

        restaurante.atualizar(
                input.nome(),
                input.endereco(),
                input.tipoCozinha(),
                input.horarioFuncionamento()
        );

        return restauranteGateway.salvar(restaurante);
    }
}
