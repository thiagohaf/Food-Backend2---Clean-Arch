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
public class ExcluirRestauranteUseCaseTest {
    @Mock
    private RestauranteGateway gateway;

    @InjectMocks
    private ExcluirRestauranteUseCase useCase;

    @Test
    @DisplayName("Deve excluir o restaurante com sucesso quando ele existir")
    void deveExcluirComSucesso() {
        UUID id = UUID.randomUUID();
        Restaurante restauranteExistente = new Restaurante(id, "Pizzaria", "Rua A", "Italiana", "18:00-23:00", UUID.randomUUID());

        when(gateway.buscarPorId(id)).thenReturn(Optional.of(restauranteExistente));

        useCase.executar(id);

        verify(gateway, times(1)).excluir(id);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar excluir restaurante inexistente")
    void deveLancarExcecaoAoExcluirRestauranteInexistente() {
        UUID id = UUID.randomUUID();

        when(gateway.buscarPorId(id)).thenReturn(Optional.empty());

        assertThrows(RestauranteNaoEncontradoException.class, () -> useCase.executar(id));
        verify(gateway, never()).excluir(any());
    }
}
