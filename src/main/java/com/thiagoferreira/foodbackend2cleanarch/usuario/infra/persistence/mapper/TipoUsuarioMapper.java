package com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.mapper;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.entity.TipoUsuarioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TipoUsuarioMapper {

    TipoUsuarioEntity toEntity(TipoUsuario tipoUsuario);

    TipoUsuario toDomain(TipoUsuarioEntity entity);
}
