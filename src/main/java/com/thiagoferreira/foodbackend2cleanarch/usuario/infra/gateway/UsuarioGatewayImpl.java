package com.thiagoferreira.foodbackend2cleanarch.usuario.infra.gateway;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Usuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.UsuarioGateway;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.entity.UsuarioEntity;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.mapper.UsuarioMapper;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.repository.TipoUsuarioRepository;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UsuarioGatewayImpl implements UsuarioGateway {

    private final UsuarioRepository usuarioRepository;
    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public Usuario salvar(Usuario usuario) {
        UsuarioEntity entity = usuarioMapper.toEntity(usuario);
        entity.setTipoUsuario(tipoUsuarioRepository.getReferenceById(usuario.getTipoUsuario().getId()));
        UsuarioEntity saved = usuarioRepository.save(entity);
        return usuarioMapper.toDomain(saved);
    }

    @Override
    public Optional<Usuario> buscarPorId(UUID id) {
        return usuarioRepository.findByIdWithTipoUsuario(id)
                .map(usuarioMapper::toDomain);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toDomain)
                .toList();
    }

    @Override
    public void excluir(UUID id) {
        usuarioRepository.deleteById(id);
    }
}
