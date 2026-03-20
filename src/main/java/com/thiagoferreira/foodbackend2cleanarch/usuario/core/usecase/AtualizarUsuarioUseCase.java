package com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Usuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.dto.AtualizarUsuarioInput;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.UsuarioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.TipoUsuarioGateway;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.UsuarioGateway;

import java.util.UUID;

/**
 * Caso de uso que atualiza nome e tipo de um usuário existente, validando ambas as entidades envolvidas.
 */
public class AtualizarUsuarioUseCase {

    private final TipoUsuarioGateway tipoUsuarioGateway;
    private final UsuarioGateway usuarioGateway;

    /**
     * @param tipoUsuarioGateway catálogo de tipos
     * @param usuarioGateway persistência de usuários
     */
    public AtualizarUsuarioUseCase(TipoUsuarioGateway tipoUsuarioGateway, UsuarioGateway usuarioGateway) {
        this.tipoUsuarioGateway = tipoUsuarioGateway;
        this.usuarioGateway = usuarioGateway;
    }

    /**
     * @param id identificador do usuário
     * @param input novos nome e tipo
     * @return usuário atualizado persistido
     */
    public Usuario executar(UUID id, AtualizarUsuarioInput input) {
        Usuario usuario = usuarioGateway.buscarPorId(id)
                .orElseThrow(UsuarioNaoEncontradoException::new);

        TipoUsuario tipoUsuario = tipoUsuarioGateway.buscarPorId(input.tipoUsuarioId())
                .orElseThrow(TipoUsuarioNaoEncontradoException::new);

        usuario.atualizar(input.nome(), tipoUsuario);

        return usuarioGateway.salvar(usuario);
    }
}
