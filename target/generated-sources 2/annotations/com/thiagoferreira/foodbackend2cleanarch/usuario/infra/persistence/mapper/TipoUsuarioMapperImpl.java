package com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.mapper;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.entity.TipoUsuarioEntity;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-18T00:51:01-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (JetBrains s.r.o.)"
)
@Component
public class TipoUsuarioMapperImpl implements TipoUsuarioMapper {

    @Override
    public TipoUsuarioEntity toEntity(TipoUsuario tipoUsuario) {
        if ( tipoUsuario == null ) {
            return null;
        }

        UUID id = null;
        String nome = null;

        id = tipoUsuario.getId();
        nome = tipoUsuario.getNome();

        TipoUsuarioEntity tipoUsuarioEntity = new TipoUsuarioEntity( id, nome );

        return tipoUsuarioEntity;
    }

    @Override
    public TipoUsuario toDomain(TipoUsuarioEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UUID id = null;
        String nome = null;

        id = entity.getId();
        nome = entity.getNome();

        TipoUsuario tipoUsuario = new TipoUsuario( id, nome );

        return tipoUsuario;
    }
}
