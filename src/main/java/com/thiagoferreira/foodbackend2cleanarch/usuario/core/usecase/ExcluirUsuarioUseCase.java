package com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.UsuarioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.UsuarioGateway;

import java.util.UUID;

/**
 * Caso de uso que remove um usuário após validar existência.
 */
public class ExcluirUsuarioUseCase {
    private final UsuarioGateway usuarioGateway;

    /**
     * @param usuarioGateway porta de persistência
     */
    public ExcluirUsuarioUseCase(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    /**
     * @param id identificador do usuário
     */
    public void excluir(UUID id) {
        if (usuarioGateway.buscarPorId(id).isEmpty()) {
            throw new UsuarioNaoEncontradoException();
        }

        usuarioGateway.excluir(id);
    }
}
