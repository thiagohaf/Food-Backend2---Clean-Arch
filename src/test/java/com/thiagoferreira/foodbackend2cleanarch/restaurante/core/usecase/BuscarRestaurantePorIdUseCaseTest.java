package com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.RestauranteNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain.Restaurante;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.gateway.RestauranteGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarRestaurantePorIdUseCaseTest {

    @Mock
    private RestauranteGateway gateway;

    @InjectMocks
    private BuscarRestaurantePorIdUseCase useCase;

    @Test
    @DisplayName("Deve retornar o restaurante quando o ID for encontrado")
    void deveRetornarRestauranteQuandoEncontrado() {
        UUID id = UUID.randomUUID();
        Restaurante restaurante = new Restaurante(id, "Pizzaria", "Rua A", "Italiana", "18:00-23:00", UUID.randomUUID());

        when(gateway.buscarPorId(id)).thenReturn(Optional.of(restaurante));

        Restaurante resultado = useCase.executar(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(gateway, times(1)).buscarPorId(id);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o restaurante não for encontrado")
    void deveLancarExcecaoQuandoNaoEncontrado() {
        UUID id = UUID.randomUUID();
        when(gateway.buscarPorId(id)).thenReturn(Optional.empty());

        assertThrows(RestauranteNaoEncontradoException.class, () -> useCase.executar(id));
        verify(gateway, times(1)).buscarPorId(id);
    }
}