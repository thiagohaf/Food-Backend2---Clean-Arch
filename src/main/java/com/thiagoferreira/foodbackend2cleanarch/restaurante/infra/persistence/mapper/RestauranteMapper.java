package com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.mapper;

import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain.Restaurante;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.entity.RestauranteEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestauranteMapper {

    RestauranteEntity toEntity(Restaurante restaurante);

    Restaurante toDomain(RestauranteEntity entity);
}
