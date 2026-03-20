package com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.mapper;

import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain.Restaurante;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.entity.RestauranteEntity;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-19T23:59:57-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.2 (GraalVM Community)"
)
@Component
public class RestauranteMapperImpl implements RestauranteMapper {

    @Override
    public RestauranteEntity toEntity(Restaurante restaurante) {
        if ( restaurante == null ) {
            return null;
        }

        UUID id = null;
        String nome = null;
        String endereco = null;
        String tipoCozinha = null;
        String horarioFuncionamento = null;
        UUID donoId = null;

        id = restaurante.getId();
        nome = restaurante.getNome();
        endereco = restaurante.getEndereco();
        tipoCozinha = restaurante.getTipoCozinha();
        horarioFuncionamento = restaurante.getHorarioFuncionamento();
        donoId = restaurante.getDonoId();

        RestauranteEntity restauranteEntity = new RestauranteEntity( id, nome, endereco, tipoCozinha, horarioFuncionamento, donoId );

        return restauranteEntity;
    }

    @Override
    public Restaurante toDomain(RestauranteEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UUID id = null;
        String nome = null;
        String endereco = null;
        String tipoCozinha = null;
        String horarioFuncionamento = null;
        UUID donoId = null;

        id = entity.getId();
        nome = entity.getNome();
        endereco = entity.getEndereco();
        tipoCozinha = entity.getTipoCozinha();
        horarioFuncionamento = entity.getHorarioFuncionamento();
        donoId = entity.getDonoId();

        Restaurante restaurante = new Restaurante( id, nome, endereco, tipoCozinha, horarioFuncionamento, donoId );

        return restaurante;
    }
}
