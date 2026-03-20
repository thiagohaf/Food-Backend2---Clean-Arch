package com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain.Restaurante;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.dto.CriarRestauranteInput;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.exception.RestauranteDonoNaoExisteException;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.exception.RestauranteExistenteException;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.gateway.RestauranteGateway;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.mapper.RestauranteCoreMapper;

/**
 * Caso de uso responsável por orquestrar a criação de um novo restaurante: garante unicidade nome+endereço
 * e valida existência do usuário dono antes de persistir.
 */
public class CriarRestauranteUseCase {

    private final RestauranteGateway restauranteGateway;
    private final RestauranteCoreMapper mapper;

    /**
     * @param restauranteGateway porta de persistência e regras de consulta do agregado
     * @param mapper conversão do DTO de criação para domínio
     */
    public CriarRestauranteUseCase(RestauranteGateway restauranteGateway,  RestauranteCoreMapper mapper) {
        this.restauranteGateway = restauranteGateway;
        this.mapper = mapper;
    }

    /**
     * @param input dados do novo restaurante
     * @return restaurante persistido
     */
    public Restaurante executar(CriarRestauranteInput input) {

        Restaurante novoRestaurante = mapper.toDomain(input);

        if (restauranteGateway.existePorNomeEEndereco(novoRestaurante.getNome(), novoRestaurante.getEndereco())) {
            throw new RestauranteExistenteException();
        }

        if (!restauranteGateway.donoValidoExiste(novoRestaurante.getDonoId())) {
            throw new RestauranteDonoNaoExisteException();
        }

        return restauranteGateway.salvar(novoRestaurante);
    }
}
