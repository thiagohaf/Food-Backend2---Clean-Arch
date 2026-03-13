package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.dto.AtualizarItemCardapioInput;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarItemCardapioUseCaseTest {

    @Mock
    private ItemCardapioGateway gateway;

    @InjectMocks
    private AtualizarItemCardapioUseCase useCase;

    @Test
    @DisplayName("Deve atualizar e salvar o item do cardápio com sucesso")
    void deveAtualizarESalvarComSucesso() {
        UUID id = UUID.randomUUID();
        AtualizarItemCardapioInput input = new AtualizarItemCardapioInput("Pizza Nova", "Muito saborosa", new BigDecimal("45.0"), false, "nova-foto.jpg");
        ItemCardapio itemExistente = new ItemCardapio(id, "Pizza Antiga", "Saborosa", new BigDecimal("40.0"), true, "foto.jpg", UUID.randomUUID());

        when(gateway.buscarPorId(id)).thenReturn(Optional.of(itemExistente));
        when(gateway.salvar(any(ItemCardapio.class))).thenAnswer(i -> i.getArguments()[0]);

        ItemCardapio resultado = useCase.executar(id, input);

        assertEquals("Pizza Nova", resultado.getNome());
        assertEquals(new BigDecimal("45.0"), resultado.getPreco());
        verify(gateway, times(1)).salvar(itemExistente);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar item inexistente")
    void deveLancarExcecaoAoAtualizarItemInexistente() {
        UUID id = UUID.randomUUID();
        AtualizarItemCardapioInput input = new AtualizarItemCardapioInput("Pizza Nova", "Muito saborosa", new BigDecimal("45.0"), false, "nova-foto.jpg");

        when(gateway.buscarPorId(id)).thenReturn(Optional.empty());

        assertThrows(ItemCardapioNaoEncontradoException.class, () -> useCase.executar(id, input));
        verify(gateway, never()).salvar(any());
    }
}
