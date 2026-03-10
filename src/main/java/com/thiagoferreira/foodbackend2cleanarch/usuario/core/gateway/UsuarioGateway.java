package com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Usuario;

public interface UsuarioGateway {
    Usuario salvar(Usuario usuario);
}
