package com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Usuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.dto.AtualizarUsuarioInput;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.UsuarioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.TipoUsuarioGateway;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.UsuarioGateway;

import java.util.UUID;

public class AtualizarUsuarioUseCase {

    private final TipoUsuarioGateway tipoUsuarioGateway;
    private final UsuarioGateway usuarioGateway;

    public AtualizarUsuarioUseCase(TipoUsuarioGateway tipoUsuarioGateway, UsuarioGateway usuarioGateway) {
        this.tipoUsuarioGateway = tipoUsuarioGateway;
        this.usuarioGateway = usuarioGateway;
    }

    public Usuario executar(UUID id, AtualizarUsuarioInput input) {
        Usuario usuario = usuarioGateway.buscarPorId(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException());

        TipoUsuario tipoUsuario = tipoUsuarioGateway.buscarPorId(input.tipoUsuarioId())
                .orElseThrow(() -> new TipoUsuarioNaoEncontradoException());

        usuario.atualizar(input.nome(), tipoUsuario);

        return usuarioGateway.salvar(usuario);
    }
}
