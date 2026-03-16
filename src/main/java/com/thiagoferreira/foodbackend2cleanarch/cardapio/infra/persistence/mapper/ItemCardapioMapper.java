package com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.persistence.mapper;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.persistence.entity.ItemCardapioEntity;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.entity.RestauranteEntity;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.mapper.RestauranteMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

@Mapper(componentModel = "spring", uses = {RestauranteMapper.class})
public interface ItemCardapioMapper {

    ItemCardapio toDomain(ItemCardapioEntity entity);

    @Mapping(target = "id", source = "itemCardapio.id")
    @Mapping(target = "nome", source = "itemCardapio.nome")
    @Mapping(target = "descricao", source = "itemCardapio.descricao")
    @Mapping(target = "preco", source = "itemCardapio.preco")
    @Mapping(target = "restaurante", source = "restaurante")
    @Mapping(target = "categoria", constant = "GERAL")
    @Mapping(target = "dataCadastro", expression = "java(java.time.LocalDateTime.now())")
    ItemCardapioEntity toEntity(ItemCardapio itemCardapio, RestauranteEntity restaurante);

    /**
     * Fábrica usada pelo MapStruct para criar a instância de ItemCardapio
     * a partir da entidade, escolhendo explicitamente o construtor com ID.
     */
    @ObjectFactory
    default ItemCardapio createItemCardapio(ItemCardapioEntity entity) {
        if (entity == null) {
            return null;
        }

        return new ItemCardapio(
                entity.getId(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getPreco(),
                true,
                null,
                entity.getRestaurante() != null ? entity.getRestaurante().getId() : null
        );
    }
}
