package com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Usuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.UsuarioGateway;

import java.util.List;

public class ListarUsuariosUseCase {
    private final UsuarioGateway usuarioGateway;

    public ListarUsuariosUseCase(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public List<Usuario> listarTodos() {
        return usuarioGateway.listarTodos();
    }
}
