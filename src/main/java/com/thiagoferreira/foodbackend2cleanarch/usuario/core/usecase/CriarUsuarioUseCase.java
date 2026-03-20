package com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Usuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.dto.CriarUsuarioInput;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.TipoUsuarioGateway;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.UsuarioGateway;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.mapper.UsuarioCoreMapper;

/**
 * Caso de uso que cria usuário garantindo que o tipo informado exista no catálogo antes da persistência.
 */
public class CriarUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;
    private final TipoUsuarioGateway tipoUsuarioGateway;
    private final UsuarioCoreMapper mapper;

    /**
     * @param usuarioGateway persistência de usuário
     * @param tipoUsuarioGateway consulta de tipos de usuário
     * @param mapper montagem da entidade a partir do input e do tipo resolvido
     */
    public CriarUsuarioUseCase(UsuarioGateway usuarioGateway, TipoUsuarioGateway tipoUsuarioGateway, UsuarioCoreMapper mapper) {
        this.usuarioGateway = usuarioGateway;
        this.tipoUsuarioGateway = tipoUsuarioGateway;
        this.mapper = mapper;
    }

    /**
     * @param input dados do novo usuário e referência ao tipo
     * @return usuário gravado
     */
    public Usuario executar(CriarUsuarioInput input) {

        TipoUsuario tipoUsuarioEncontrado = tipoUsuarioGateway.buscarPorId(input.tipoUsuarioId())
                .orElseThrow(TipoUsuarioNaoEncontradoException::new);

        Usuario novoUsuario = mapper.toDomain(input, tipoUsuarioEncontrado);

        return usuarioGateway.salvar(novoUsuario);
    }
}
