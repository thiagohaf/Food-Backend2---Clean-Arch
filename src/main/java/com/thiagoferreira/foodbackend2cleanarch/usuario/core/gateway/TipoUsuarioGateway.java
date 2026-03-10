package com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;

import java.util.Optional;
import java.util.UUID;

public interface TipoUsuarioGateway {
    TipoUsuario salvar(TipoUsuario tipoUsuario);
    boolean existePorNome(String nome);
    Optional<TipoUsuario> buscarPorId(UUID id);
}
