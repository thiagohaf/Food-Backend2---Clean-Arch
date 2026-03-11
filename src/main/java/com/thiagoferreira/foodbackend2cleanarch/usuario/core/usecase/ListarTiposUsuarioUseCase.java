package com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.TipoUsuarioGateway;

import java.util.List;

public class ListarTiposUsuarioUseCase {

    private final TipoUsuarioGateway tipoUsuarioGateway;

    public ListarTiposUsuarioUseCase(TipoUsuarioGateway tipoUsuarioGateway) {
        this.tipoUsuarioGateway = tipoUsuarioGateway;
    }

    public List<TipoUsuario> listarTodos() {
        return tipoUsuarioGateway.listarTodos();
    }
}
