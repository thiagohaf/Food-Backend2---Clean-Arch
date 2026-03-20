package com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Porta de saída para persistência e leitura de {@link Usuario} no núcleo da aplicação.
 */
public interface UsuarioGateway {

    /**
     * @param usuario entidade a gravar
     * @return usuário após persistência
     */
    Usuario salvar(Usuario usuario);

    /**
     * @param id identificador do usuário
     * @return usuário, se existir
     */
    Optional<Usuario> buscarPorId(UUID id);

    /**
     * @return todos os usuários cadastrados
     */
    List<Usuario> listarTodos();

    /**
     * @param id identificador do usuário a excluir
     */
    void excluir(UUID id);
}
