package com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.dto.AtualizarTipoUsuarioInput;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.TipoUsuarioGateway;
import com.thiagoferreira.foodbackend2cleanarch.util.exception.ValidacaoRegraNegocioException;

import java.util.UUID;

/**
 * Caso de uso que renomeia um tipo de usuário, impedindo colisão de nome com outro registro distinto.
 */
public class AtualizarTipoUsuarioUseCase {

    private final TipoUsuarioGateway tipoUsuarioGateway;

    /**
     * @param tipoUsuarioGateway persistência e consultas de unicidade
     */
    public AtualizarTipoUsuarioUseCase(TipoUsuarioGateway tipoUsuarioGateway) {
        this.tipoUsuarioGateway = tipoUsuarioGateway;
    }

    /**
     * @param id identificador do tipo
     * @param input novo nome desejado
     * @return tipo atualizado persistido
     */
    public TipoUsuario executar(UUID id, AtualizarTipoUsuarioInput input) {
        TipoUsuario tipo = tipoUsuarioGateway.buscarPorId(id)
                .orElseThrow(TipoUsuarioNaoEncontradoException::new);

        if (!tipo.getNome().equalsIgnoreCase(input.nome()) && tipoUsuarioGateway.existePorNome(input.nome())) {
            throw new ValidacaoRegraNegocioException("Já existe outro Tipo de Usuário com este nome.");
        }

        tipo.atualizar(input.nome());
        return tipoUsuarioGateway.salvar(tipo);
    }
}
