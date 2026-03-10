package com.thiagoferreira.foodbackend2cleanarch.usuario.core.mapper;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.dto.CriarTipoUsuarioInput;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.TipoUsuarioCoreMapperException;

public class TipoUsuarioCoreMapper {

    public TipoUsuario toDomain(CriarTipoUsuarioInput input) {
        if (input == null) {
            throw new TipoUsuarioCoreMapperException();
        }

        return new TipoUsuario(
                input.id(),
                input.nome()
        );
    }
}
