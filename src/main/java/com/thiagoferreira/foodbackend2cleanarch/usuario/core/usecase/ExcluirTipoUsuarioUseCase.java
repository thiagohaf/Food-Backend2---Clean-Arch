package com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.TipoUsuarioGateway;

import java.util.UUID;

/**
 * Caso de uso que exclui um tipo de usuário após validar existência.
 */
public class ExcluirTipoUsuarioUseCase {

    private final TipoUsuarioGateway tipoUsuarioGateway;

    /**
     * @param tipoUsuarioGateway porta de persistência
     */
    public ExcluirTipoUsuarioUseCase(TipoUsuarioGateway tipoUsuarioGateway) {
        this.tipoUsuarioGateway = tipoUsuarioGateway;
    }

    /**
     * @param id identificador do tipo
     */
    public void excluir(UUID id) {
        if (tipoUsuarioGateway.buscarPorId(id).isEmpty()) {
            throw new TipoUsuarioNaoEncontradoException();
        }

        tipoUsuarioGateway.excluir(id);
    }
}
