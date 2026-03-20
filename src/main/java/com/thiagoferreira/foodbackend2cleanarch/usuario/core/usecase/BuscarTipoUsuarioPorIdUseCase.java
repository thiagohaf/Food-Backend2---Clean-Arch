package com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.TipoUsuarioGateway;

import java.util.UUID;

/**
 * Caso de uso que recupera tipo de usuário por id ou falha se ausente.
 */
public class BuscarTipoUsuarioPorIdUseCase {

    private final TipoUsuarioGateway tipoUsuarioGateway;

    /**
     * @param tipoUsuarioGateway porta de consulta
     */
    public BuscarTipoUsuarioPorIdUseCase(TipoUsuarioGateway tipoUsuarioGateway) {
        this.tipoUsuarioGateway = tipoUsuarioGateway;
    }

    /**
     * @param id identificador do tipo
     * @return tipo encontrado
     */
    public TipoUsuario buscarPorId(UUID id) {
        return tipoUsuarioGateway.buscarPorId(id)
                .orElseThrow(TipoUsuarioNaoEncontradoException::new);
    }
}
