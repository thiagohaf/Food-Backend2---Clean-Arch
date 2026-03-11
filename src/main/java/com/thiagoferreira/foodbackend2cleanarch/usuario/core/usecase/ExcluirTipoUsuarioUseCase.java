package com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.TipoUsuarioGateway;

import java.util.UUID;

public class ExcluirTipoUsuarioUseCase {

    private final TipoUsuarioGateway tipoUsuarioGateway;

    public ExcluirTipoUsuarioUseCase(TipoUsuarioGateway tipoUsuarioGateway) {
        this.tipoUsuarioGateway = tipoUsuarioGateway;
    }

    public void excluir(UUID id) {
        if (tipoUsuarioGateway.buscarPorId(id).isEmpty()) {
            throw new TipoUsuarioNaoEncontradoException();
        }

        tipoUsuarioGateway.excluir(id);
    }
}
