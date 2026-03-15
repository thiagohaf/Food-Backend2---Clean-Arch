package com.thiagoferreira.foodbackend2cleanarch.usuario.infra.gateway;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.TipoUsuarioGateway;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.entity.TipoUsuarioEntity;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.mapper.TipoUsuarioMapper;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.repository.TipoUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TipoUsuarioGatewayImpl implements TipoUsuarioGateway {

    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final TipoUsuarioMapper tipoUsuarioMapper;

    @Override
    public TipoUsuario salvar(TipoUsuario tipoUsuario) {
        TipoUsuarioEntity entity = tipoUsuarioMapper.toEntity(tipoUsuario);
        TipoUsuarioEntity saved = tipoUsuarioRepository.save(entity);
        return tipoUsuarioMapper.toDomain(saved);
    }

    @Override
    public boolean existePorNome(String nome) {
        return tipoUsuarioRepository.existsByNome(nome);
    }

    @Override
    public Optional<TipoUsuario> buscarPorId(UUID id) {
        return tipoUsuarioRepository.findById(id)
                .map(tipoUsuarioMapper::toDomain);
    }

    @Override
    public List<TipoUsuario> listarTodos() {
        return tipoUsuarioRepository.findAll().stream()
                .map(tipoUsuarioMapper::toDomain)
                .toList();
    }

    @Override
    public void excluir(UUID id) {
        tipoUsuarioRepository.deleteById(id);
    }
}
