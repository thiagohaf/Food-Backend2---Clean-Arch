package com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.RestauranteNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain.Restaurante;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.dto.AtualizarRestauranteInput;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AtualizarRestauranteUseCaseTest {

    @Mock
    private RestauranteGateway gateway;

    @InjectMocks
    private AtualizarRestauranteUseCase useCase;

    @Test
    @DisplayName("Deve atualizar e salvar o restaurante com sucesso")
    void deveAtualizarESalvarComSucesso() {
        UUID id = UUID.randomUUID();
        AtualizarRestauranteInput input = new AtualizarRestauranteInput("Hamburgueria", "Rua B", "Lanches", "18:00-23:00");
        Restaurante restauranteExistente = new Restaurante(id, "Pizzaria", "Rua A", "Italiana", "18:00-23:00", UUID.randomUUID());

        when(gateway.buscarPorId(id)).thenReturn(Optional.of(restauranteExistente));
        when(gateway.salvar(any(Restaurante.class))).thenAnswer(i -> i.getArguments()[0]); // Retorna o mesmo objeto salvo

        Restaurante resultado = useCase.executar(id, input);

        assertEquals("Hamburgueria", resultado.getNome());
        assertEquals("Rua B", resultado.getEndereco());
        verify(gateway, times(1)).salvar(restauranteExistente);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar restaurante inexistente")
    void deveLancarExcecaoAoAtualizarRestauranteInexistente() {
        UUID id = UUID.randomUUID();
        AtualizarRestauranteInput input = new AtualizarRestauranteInput("Hamburgueria", "Rua B", "Lanches", "18:00-23:00");

        when(gateway.buscarPorId(id)).thenReturn(Optional.empty());

        assertThrows(RestauranteNaoEncontradoException.class, () -> useCase.executar(id, input));
        verify(gateway, never()).salvar(any());
    }
}
