package com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.gateway;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.gateway.ItemCardapioGateway;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.persistence.entity.ItemCardapioEntity;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.persistence.mapper.ItemCardapioMapper;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.persistence.repository.ItemCardapioRepository;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.entity.RestauranteEntity;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ItemCardapioGatewayImpl implements ItemCardapioGateway {

    private final ItemCardapioRepository itemCardapioRepository;
    private final ItemCardapioMapper itemCardapioMapper;
    private final RestauranteRepository restauranteRepository;

    @Override
    public ItemCardapio salvar(ItemCardapio itemCardapio) {
        RestauranteEntity restauranteRef = restauranteRepository.getReferenceById(itemCardapio.getRestauranteId());
        ItemCardapioEntity entity = itemCardapioMapper.toEntity(itemCardapio, restauranteRef);
        ItemCardapioEntity saved = itemCardapioRepository.save(entity);
        return itemCardapioMapper.toDomain(saved);
    }

    @Override
    public boolean restauranteExiste(UUID restauranteId) {
        return restauranteRepository.existsById(restauranteId);
    }

    @Override
    public boolean existeItemPorNomeERestaurante(String nome, UUID restauranteId) {
        return itemCardapioRepository.existsByNomeAndRestaurante_Id(nome, restauranteId);
    }

    @Override
    public Optional<ItemCardapio> buscarPorId(UUID id) {
        return itemCardapioRepository.findByIdWithRestaurante(id)
                .map(itemCardapioMapper::toDomain);
    }

    @Override
    public List<ItemCardapio> listarPorRestaurante(UUID restauranteId) {
        return itemCardapioRepository.findByRestaurante_Id(restauranteId).stream()
                .map(itemCardapioMapper::toDomain)
                .toList();
    }

    @Override
    public void excluir(UUID id) {
        itemCardapioRepository.deleteById(id);
    }
}
