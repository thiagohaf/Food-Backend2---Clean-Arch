package com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.RestauranteNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain.Restaurante;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.dto.AtualizarRestauranteInput;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.gateway.RestauranteGateway;

import java.util.UUID;

/**
 * Caso de uso que atualiza dados cadastrais do restaurante (não altera o dono já vinculado na criação).
 */
public class AtualizarRestauranteUseCase {

    private RestauranteGateway restauranteGateway;

    /**
     * @param restauranteGateway porta de persistência
     */
    public AtualizarRestauranteUseCase(RestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    /**
     * @param id identificador do restaurante
     * @param input novos valores de negócio
     * @return agregado atualizado persistido
     */
    public Restaurante executar(UUID id, AtualizarRestauranteInput input) {

        Restaurante restaurante = restauranteGateway.buscarPorId(id)
                .orElseThrow(RestauranteNaoEncontradoException::new);

        restaurante.atualizar(
                input.nome(),
                input.endereco(),
                input.tipoCozinha(),
                input.horarioFuncionamento()
        );

        return restauranteGateway.salvar(restaurante);
    }
}
