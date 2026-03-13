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
class ExcluirItemCardapioUseCaseTest {

    @Mock
    private ItemCardapioGateway gateway;

    @InjectMocks
    private ExcluirItemCardapioUseCase useCase;

    @Test
    @DisplayName("Deve excluir o item do cardápio com sucesso quando ele existir")
    void deveExcluirComSucesso() {
        UUID id = UUID.randomUUID();
        ItemCardapio itemExistente = new ItemCardapio(id, "Pizza", "Saborosa", new BigDecimal("40.0"), true, "foto.jpg", UUID.randomUUID());

        when(gateway.buscarPorId(id)).thenReturn(Optional.of(itemExistente));

        useCase.executar(id);

        verify(gateway, times(1)).excluir(id);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar excluir item inexistente")
    void deveLancarExcecaoAoExcluirItemInexistente() {
        UUID id = UUID.randomUUID();

        when(gateway.buscarPorId(id)).thenReturn(Optional.empty());

        assertThrows(ItemCardapioNaoEncontradoException.class, () -> useCase.executar(id));
        verify(gateway, never()).excluir(any());
    }
}
