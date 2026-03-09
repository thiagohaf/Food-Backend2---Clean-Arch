package com.thiagoferreira.foodbackend2cleanarch.restaurante.core.mapper;

import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain.Restaurante;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.dto.CriarRestauranteInput;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.exception.RestauranteCoreMapperException;

public class RestauranteCoreMapper {
    public Restaurante toDomain(CriarRestauranteInput input) {
        if (input == null) {
            throw new RestauranteCoreMapperException();
        }

        return new Restaurante(
                input.id(),
                input.nome(),
                input.endereco(),
                input.tipoCozinha(),
                input.horarioFuncionamento(),
                input.donoId()
        );
    }
}
