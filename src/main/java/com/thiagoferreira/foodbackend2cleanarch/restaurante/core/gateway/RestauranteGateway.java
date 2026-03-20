package com.thiagoferreira.foodbackend2cleanarch.restaurante.core.gateway;

import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain.Restaurante;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Porta de saída do agregado restaurante: unicidade por nome+endereço, validação do dono e CRUD.
 */
public interface RestauranteGateway {

    /**
     * @param restaurante entidade a persistir
     * @return restaurante após gravação
     */
    Restaurante salvar(Restaurante restaurante);

    /**
     * @param nome nome do estabelecimento
     * @param endereco endereço
     * @return {@code true} se já existe combinação nome+endereço (regra de negócio)
     */
    boolean existePorNomeEEndereco(String nome, String endereco);

    /**
     * @param donoId identificador do usuário dono
     * @return {@code true} se o dono é um usuário válido no sistema
     */
    boolean donoValidoExiste(UUID donoId);

    /**
     * @param id identificador do restaurante
     * @return restaurante, se existir
     */
    Optional<Restaurante> buscarPorId(UUID id);

    /**
     * @return todos os restaurantes
     */
    List<Restaurante> listarTodos();

    /**
     * @param id identificador do restaurante a excluir
     */
    void excluir(UUID id);
}
