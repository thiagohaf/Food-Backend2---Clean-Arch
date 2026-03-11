package com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.UsuarioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.UsuarioGateway;

import java.util.UUID;

public class ExcluirUsuarioUseCase {
    private final UsuarioGateway usuarioGateway;

    public ExcluirUsuarioUseCase(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public void excluir(UUID id) {
        if (usuarioGateway.buscarPorId(id).isEmpty()) {
            throw new UsuarioNaoEncontradoException();
        }

        usuarioGateway.excluir(id);
    }
}
