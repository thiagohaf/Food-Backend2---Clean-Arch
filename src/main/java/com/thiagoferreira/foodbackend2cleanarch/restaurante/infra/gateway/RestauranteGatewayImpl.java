package com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.gateway;

import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain.Restaurante;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.gateway.RestauranteGateway;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.entity.RestauranteEntity;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.mapper.RestauranteMapper;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.repository.RestauranteRepository;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RestauranteGatewayImpl implements RestauranteGateway {

    private static final String TIPO_DONO_NOME = "DONO_RESTAURANTE";

    private final RestauranteRepository restauranteRepository;
    private final RestauranteMapper restauranteMapper;
    private final UsuarioGateway usuarioGateway;

    @Override
    public Restaurante salvar(Restaurante restaurante) {
        RestauranteEntity entity = restauranteMapper.toEntity(restaurante);
        RestauranteEntity saved = restauranteRepository.save(entity);
        return restauranteMapper.toDomain(saved);
    }

    @Override
    public boolean existePorNomeEEndereco(String nome, String endereco) {
        return restauranteRepository.existsByNomeAndEndereco(nome, endereco);
    }

    @Override
    public boolean donoValidoExiste(UUID donoId) {
        return usuarioGateway.buscarPorId(donoId)
                .map(usuario -> TIPO_DONO_NOME.equalsIgnoreCase(usuario.getTipoUsuario().getNome()))
                .orElse(false);
    }

    @Override
    public Optional<Restaurante> buscarPorId(UUID id) {
        return restauranteRepository.findById(id)
                .map(restauranteMapper::toDomain);
    }

    @Override
    public List<Restaurante> listarTodos() {
        return restauranteRepository.findAll().stream()
                .map(restauranteMapper::toDomain)
                .toList();
    }

    @Override
    public void excluir(UUID id) {
        restauranteRepository.deleteById(id);
    }
}
