package com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.dto.CriarTipoUsuarioInput;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.TipoUsuarioExistenteException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.TipoUsuarioGateway;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.mapper.TipoUsuarioCoreMapper;

public class CriarTipoUsuarioUseCase {

    private final TipoUsuarioGateway tipoUsuarioGateway;
    private final TipoUsuarioCoreMapper mapper;

    public CriarTipoUsuarioUseCase(TipoUsuarioGateway tipoUsuarioGateway, TipoUsuarioCoreMapper mapper) {
        this.tipoUsuarioGateway = tipoUsuarioGateway;
        this.mapper = mapper;
    }

    public TipoUsuario executar(CriarTipoUsuarioInput input) {
        TipoUsuario novoTipo = mapper.toDomain(input);

        if (tipoUsuarioGateway.existePorNome(novoTipo.getNome())) {
            throw new TipoUsuarioExistenteException();
        }

        return tipoUsuarioGateway.salvar(novoTipo);
    }
}
