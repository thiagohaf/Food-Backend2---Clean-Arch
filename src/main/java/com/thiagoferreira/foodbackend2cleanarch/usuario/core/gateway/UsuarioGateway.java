package com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioGateway {
    Usuario salvar(Usuario usuario);
    Optional<Usuario> buscarPorId(UUID id);
    List<Usuario> listarTodos();
    void excluir(UUID id);
}
