package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.gateway;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Porta de saída do domínio de cardápio: abstrai persistência e consultas de itens por restaurante.
 */
public interface ItemCardapioGateway {

    /**
     * @param itemCardapio entidade a persistir (criação ou atualização)
     * @return instância após gravação, possivelmente com dados gerados pela infraestrutura
     */
    ItemCardapio salvar(ItemCardapio itemCardapio);

    /**
     * @param restauranteId identificador do restaurante
     * @return {@code true} se o restaurante existe para fins de vínculo do item
     */
    boolean restauranteExiste(UUID restauranteId);

    /**
     * @param nome nome do item
     * @param restauranteId restaurante onde a unicidade é verificada
     * @return {@code true} se já existe item com o mesmo nome no restaurante
     */
    boolean existeItemPorNomeERestaurante(String nome, UUID restauranteId);

    /**
     * @param id identificador do item
     * @return item encontrado, se existir
     */
    Optional<ItemCardapio> buscarPorId(UUID id);

    /**
     * @param restauranteId restaurante cujo cardápio será listado
     * @return itens associados ao restaurante
     */
    List<ItemCardapio> listarPorRestaurante(UUID restauranteId);

    /**
     * @param id identificador do item a remover
     */
    void excluir(UUID id);
}
