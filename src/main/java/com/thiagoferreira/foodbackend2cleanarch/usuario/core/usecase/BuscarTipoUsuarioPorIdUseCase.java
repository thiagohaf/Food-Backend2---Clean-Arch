package com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.TipoUsuarioGateway;

import java.util.UUID;

public class BuscarTipoUsuarioPorIdUseCase {

    private final TipoUsuarioGateway tipoUsuarioGateway;

    public BuscarTipoUsuarioPorIdUseCase(TipoUsuarioGateway tipoUsuarioGateway) {
        this.tipoUsuarioGateway = tipoUsuarioGateway;
    }

    public TipoUsuario buscarPorId(UUID id) {
        return tipoUsuarioGateway.buscarPorId(id)
                .orElseThrow(() -> new TipoUsuarioNaoEncontradoException());
    }
}
