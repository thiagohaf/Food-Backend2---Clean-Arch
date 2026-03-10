package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.ItemCardapioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.gateway.ItemCardapioGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BuscarItemCardapioPorIdUseCaseTest {
    @Mock
    private ItemCardapioGateway gateway;

    @InjectMocks
    private BuscarItemCardapioPorIdUseCase useCase;

    @Test
    @DisplayName("Deve retornar o item do cardápio quando o ID for encontrado")
    void deveRetornarItemQuandoEncontrado() {
        UUID id = UUID.randomUUID();

        ItemCardapio item = new ItemCardapio(id, "Pizza", "Saborosa", new BigDecimal("40.0"), true, "foto.jpg", UUID.randomUUID());

        when(gateway.buscarPorId(id)).thenReturn(Optional.of(item));

        ItemCardapio resultado = useCase.executar(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(gateway, times(1)).buscarPorId(id);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o item do cardápio não for encontrado")
    void deveLancarExcecaoQuandoNaoEncontrado() {
        UUID id = UUID.randomUUID();
        when(gateway.buscarPorId(id)).thenReturn(Optional.empty());

        assertThrows(ItemCardapioNaoEncontradoException.class, () -> useCase.executar(id));
        verify(gateway, times(1)).buscarPorId(id);
    }
}
