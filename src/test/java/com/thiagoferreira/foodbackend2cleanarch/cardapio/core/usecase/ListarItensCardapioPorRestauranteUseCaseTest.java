package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.gateway.ItemCardapioGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListarItensCardapioPorRestauranteUseCaseTest {

    @Mock
    private ItemCardapioGateway gateway;

    @InjectMocks
    private ListarItensCardapioPorRestauranteUseCase useCase;

    @Test
    @DisplayName("Deve retornar a lista de itens de um restaurante específico")
    void deveRetornarListaDeItens() {
        UUID restauranteId = UUID.randomUUID();

        List<ItemCardapio> lista = List.of(
                new ItemCardapio(UUID.randomUUID(), "Pizza", "Saborosa", new BigDecimal("40.0"), true, "foto.jpg", restauranteId)
        );

        when(gateway.listarPorRestaurante(restauranteId)).thenReturn(lista);

        List<ItemCardapio> resultado = useCase.executar(restauranteId);

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(gateway, times(1)).listarPorRestaurante(restauranteId);
    }
}
