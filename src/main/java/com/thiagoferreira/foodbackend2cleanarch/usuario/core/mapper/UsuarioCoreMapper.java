package com.thiagoferreira.foodbackend2cleanarch.usuario.core.mapper;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Usuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.dto.CriarUsuarioInput;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.TipoUsuarioCoreMapperException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.UsuarioCoreMapperException;

public class UsuarioCoreMapper {
    /**
     * @param input O DTO vindo da requisição
     * @param tipoUsuario A entidade rica já recuperada do banco de dados pelo UseCase
     */
    public Usuario toDomain(CriarUsuarioInput input, TipoUsuario tipoUsuario) {
        if (input == null) {
            throw new UsuarioCoreMapperException();
        }

        if (tipoUsuario == null) {
            throw new TipoUsuarioCoreMapperException();
        }

        return new Usuario(
                input.id(),
                input.nome(),
                tipoUsuario
        );
    }
}
