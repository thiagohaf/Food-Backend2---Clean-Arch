package com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.gateway;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.persistence.entity.ItemCardapioEntity;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.persistence.mapper.ItemCardapioMapper;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.persistence.repository.ItemCardapioRepository;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.repository.RestauranteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemCardapioGatewayImplTest {

    @Mock
    private ItemCardapioRepository itemCardapioRepository;

    @Mock
    private ItemCardapioMapper itemCardapioMapper;

    @Mock
    private RestauranteRepository restauranteRepository;

    @InjectMocks
    private ItemCardapioGatewayImpl itemCardapioGateway;

    @Test
    @DisplayName("buscarPorId deve utilizar findByIdWithRestaurante do repositório e mapear para domínio")
    void deveBuscarPorIdUtilizandoFindByIdWithRestaurante() {
        // Arrange
        UUID id = UUID.randomUUID();
        ItemCardapioEntity entity = new ItemCardapioEntity(
                id,
                "Pizza Margherita",
                "Pizza clássica",
                new BigDecimal("39.90"),
                true,
                "PIZZA",
                null,
                LocalDateTime.now()
        );
        ItemCardapio domain = new ItemCardapio(
                id,
                "Pizza Margherita",
                "Pizza clássica",
                new BigDecimal("39.90"),
                true,
                null,
                UUID.randomUUID()
        );

        when(itemCardapioRepository.findByIdWithRestaurante(id)).thenReturn(Optional.of(entity));
        when(itemCardapioMapper.toDomain(entity)).thenReturn(domain);

        // Act
        Optional<ItemCardapio> resultado = itemCardapioGateway.buscarPorId(id);

        // Assert
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(id);
        verify(itemCardapioRepository).findByIdWithRestaurante(id);
        verify(itemCardapioRepository, never()).findById(any());
        verify(itemCardapioMapper).toDomain(entity);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando buscar item de cardápio por ID inexistente")
    void deveRetornarOptionalVazioQuandoBuscarPorIdInexistente() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(itemCardapioRepository.findByIdWithRestaurante(id)).thenReturn(Optional.empty());

        // Act
        Optional<ItemCardapio> resultado = itemCardapioGateway.buscarPorId(id);

        // Assert
        assertThat(resultado).isEmpty();
        verify(itemCardapioRepository).findByIdWithRestaurante(id);
        verify(itemCardapioMapper, never()).toDomain(any());
    }

    @Test
    @DisplayName("Deve propagar DataAccessException quando repositório falhar ao salvar item de cardápio")
    void devePropagarExcecaoQuandoRepositorioLancarErroAoSalvarItemCardapio() {
        // Arrange
        UUID itemId = UUID.randomUUID();
        UUID restauranteId = UUID.randomUUID();
        ItemCardapio dominio = new ItemCardapio(
                itemId,
                "Pizza Margherita",
                "Pizza clássica",
                new BigDecimal("39.90"),
                true,
                null,
                restauranteId
        );

        var restauranteRef = new com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.entity.RestauranteEntity(
                restauranteId,
                "Ristorante Italiano",
                "Rua A, 123",
                "Italiana",
                "18:00-23:00",
                UUID.randomUUID()
        );

        ItemCardapioEntity entity = new ItemCardapioEntity(
                itemId,
                "Pizza Margherita",
                "Pizza clássica",
                new BigDecimal("39.90"),
                true,
                "GERAL",
                restauranteRef,
                LocalDateTime.now()
        );

        when(restauranteRepository.getReferenceById(restauranteId)).thenReturn(restauranteRef);
        when(itemCardapioMapper.toEntity(dominio, restauranteRef)).thenReturn(entity);
        when(itemCardapioRepository.save(entity))
                .thenThrow(new org.springframework.dao.DataIntegrityViolationException("erro de banco"));

        // Act & Assert
        assertThatThrownBy(() -> itemCardapioGateway.salvar(dominio))
                .isInstanceOf(org.springframework.dao.DataAccessException.class);

        verify(restauranteRepository).getReferenceById(restauranteId);
        verify(itemCardapioMapper).toEntity(dominio, restauranteRef);
        verify(itemCardapioRepository).save(entity);
    }
}

