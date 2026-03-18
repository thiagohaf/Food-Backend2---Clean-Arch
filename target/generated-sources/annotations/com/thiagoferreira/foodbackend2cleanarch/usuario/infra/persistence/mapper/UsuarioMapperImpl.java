package com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.mapper;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Usuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.entity.TipoUsuarioEntity;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.entity.UsuarioEntity;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-17T23:47:33-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (JetBrains s.r.o.)"
)
@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Autowired
    private TipoUsuarioMapper tipoUsuarioMapper;

    @Override
    public UsuarioEntity toEntity(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        UUID id = null;
        String nome = null;
        TipoUsuarioEntity tipoUsuario = null;

        id = usuario.getId();
        nome = usuario.getNome();
        tipoUsuario = tipoUsuarioMapper.toEntity( usuario.getTipoUsuario() );

        UsuarioEntity usuarioEntity = new UsuarioEntity( id, nome, tipoUsuario );

        return usuarioEntity;
    }

    @Override
    public Usuario toDomain(UsuarioEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UUID id = null;
        String nome = null;
        TipoUsuario tipoUsuario = null;

        id = entity.getId();
        nome = entity.getNome();
        tipoUsuario = tipoUsuarioMapper.toDomain( entity.getTipoUsuario() );

        Usuario usuario = new Usuario( id, nome, tipoUsuario );

        return usuario;
    }
}
