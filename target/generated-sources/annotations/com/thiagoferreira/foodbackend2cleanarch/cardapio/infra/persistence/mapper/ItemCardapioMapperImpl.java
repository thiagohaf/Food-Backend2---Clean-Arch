package com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.persistence.mapper;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.persistence.entity.ItemCardapioEntity;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.entity.RestauranteEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-19T22:33:38-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (JetBrains s.r.o.)"
)
@Component
public class ItemCardapioMapperImpl implements ItemCardapioMapper {

    @Override
    public ItemCardapio toDomain(ItemCardapioEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ItemCardapio itemCardapio = createItemCardapio( entity );

        return itemCardapio;
    }

    @Override
    public ItemCardapioEntity toEntity(ItemCardapio itemCardapio, RestauranteEntity restaurante) {
        if ( itemCardapio == null && restaurante == null ) {
            return null;
        }

        UUID id = null;
        String nome = null;
        String descricao = null;
        BigDecimal preco = null;
        boolean disponibilidadeLocal = false;
        String fotoPath = null;
        if ( itemCardapio != null ) {
            id = itemCardapio.getId();
            nome = itemCardapio.getNome();
            descricao = itemCardapio.getDescricao();
            preco = itemCardapio.getPreco();
            disponibilidadeLocal = itemCardapio.getDisponibilidadeLocal();
            fotoPath = itemCardapio.getFotoPath();
        }
        RestauranteEntity restaurante1 = null;
        restaurante1 = restaurante;

        LocalDateTime dataCadastro = java.time.LocalDateTime.now();

        ItemCardapioEntity itemCardapioEntity = new ItemCardapioEntity( id, nome, descricao, preco, disponibilidadeLocal, fotoPath, restaurante1, dataCadastro );

        return itemCardapioEntity;
    }
}
