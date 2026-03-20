package com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Usuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.UsuarioGateway;

import java.util.List;

/**
 * Caso de uso que lista todos os usuários.
 */
public class ListarUsuariosUseCase {
    private final UsuarioGateway usuarioGateway;

    /**
     * @param usuarioGateway porta de listagem
     */
    public ListarUsuariosUseCase(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    /**
     * @return todos os usuários cadastrados
     */
    public List<Usuario> listarTodos() {
        return usuarioGateway.listarTodos();
    }
}
