package com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Usuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.UsuarioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.UsuarioGateway;

import java.util.UUID;

/**
 * Caso de uso que retorna um usuário pelo id ou falha com exceção de domínio.
 */
public class BuscarUsuarioPorIdUseCase {

    private final UsuarioGateway usuarioGateway;

    /**
     * @param usuarioGateway porta de consulta
     */
    public BuscarUsuarioPorIdUseCase(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    /**
     * @param id identificador do usuário
     * @return usuário encontrado
     */
    public Usuario buscarPorId(UUID id) {
        return usuarioGateway.buscarPorId(id)
                .orElseThrow(UsuarioNaoEncontradoException::new);
    }
}
