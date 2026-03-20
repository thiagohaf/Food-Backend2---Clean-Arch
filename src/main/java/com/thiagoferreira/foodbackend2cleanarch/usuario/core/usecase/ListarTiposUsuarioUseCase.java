package com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.TipoUsuarioGateway;

import java.util.List;

/**
 * Caso de uso que lista todos os tipos de usuário cadastrados.
 */
public class ListarTiposUsuarioUseCase {

    private final TipoUsuarioGateway tipoUsuarioGateway;

    /**
     * @param tipoUsuarioGateway porta de listagem
     */
    public ListarTiposUsuarioUseCase(TipoUsuarioGateway tipoUsuarioGateway) {
        this.tipoUsuarioGateway = tipoUsuarioGateway;
    }

    /**
     * @return todos os tipos
     */
    public List<TipoUsuario> listarTodos() {
        return tipoUsuarioGateway.listarTodos();
    }
}
