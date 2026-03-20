package com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Porta de saída para o catálogo de tipos de usuário (perfis) utilizados nas regras de cadastro.
 */
public interface TipoUsuarioGateway {

    /**
     * @param tipoUsuario tipo a persistir
     * @return tipo após gravação
     */
    TipoUsuario salvar(TipoUsuario tipoUsuario);

    /**
     * @param nome nome a testar
     * @return {@code true} se já existe tipo com esse nome
     */
    boolean existePorNome(String nome);

    /**
     * @param id identificador do tipo
     * @return tipo, se existir
     */
    Optional<TipoUsuario> buscarPorId(UUID id);

    /**
     * @return todos os tipos cadastrados
     */
    List<TipoUsuario> listarTodos();

    /**
     * @param id identificador do tipo a remover
     */
    void excluir(UUID id);
}
