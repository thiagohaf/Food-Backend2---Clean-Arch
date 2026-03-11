package com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Usuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.dto.CriarUsuarioInput;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.TipoUsuarioGateway;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.UsuarioGateway;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.mapper.UsuarioCoreMapper;

public class CriarUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;
    private final TipoUsuarioGateway tipoUsuarioGateway;
    private final UsuarioCoreMapper mapper;

    public CriarUsuarioUseCase(UsuarioGateway usuarioGateway, TipoUsuarioGateway tipoUsuarioGateway, UsuarioCoreMapper mapper) {
        this.usuarioGateway = usuarioGateway;
        this.tipoUsuarioGateway = tipoUsuarioGateway;
        this.mapper = mapper;
    }

    public Usuario executar(CriarUsuarioInput input) {

        TipoUsuario tipoUsuarioEncontrado = tipoUsuarioGateway.buscarPorId(input.tipoUsuarioId())
                .orElseThrow(() -> new TipoUsuarioNaoEncontradoException());

        Usuario novoUsuario = mapper.toDomain(input, tipoUsuarioEncontrado);

        return usuarioGateway.salvar(novoUsuario);
    }
}
