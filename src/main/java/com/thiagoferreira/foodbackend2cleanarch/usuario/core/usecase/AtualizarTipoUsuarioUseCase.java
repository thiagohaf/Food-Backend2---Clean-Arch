package com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.dto.AtualizarTipoUsuarioInput;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.TipoUsuarioGateway;
import com.thiagoferreira.foodbackend2cleanarch.util.exception.ValidacaoRegraNegocioException;

import java.util.UUID;

public class AtualizarTipoUsuarioUseCase {

    private final TipoUsuarioGateway tipoUsuarioGateway;

    public AtualizarTipoUsuarioUseCase(TipoUsuarioGateway tipoUsuarioGateway) {
        this.tipoUsuarioGateway = tipoUsuarioGateway;
    }

    public TipoUsuario executar(UUID id, AtualizarTipoUsuarioInput input) {
        TipoUsuario tipo = tipoUsuarioGateway.buscarPorId(id)
                .orElseThrow(() -> new TipoUsuarioNaoEncontradoException());

        if (!tipo.getNome().equalsIgnoreCase(input.nome()) && tipoUsuarioGateway.existePorNome(input.nome())) {
            throw new ValidacaoRegraNegocioException("Já existe outro Tipo de Usuário com este nome.");
        }

        tipo.atualizar(input.nome());
        return tipoUsuarioGateway.salvar(tipo);
    }
}
