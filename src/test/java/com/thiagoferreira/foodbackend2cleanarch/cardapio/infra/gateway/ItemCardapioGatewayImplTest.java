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

import static org.assertj.core.api.Assertions.assertThat;
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
}

