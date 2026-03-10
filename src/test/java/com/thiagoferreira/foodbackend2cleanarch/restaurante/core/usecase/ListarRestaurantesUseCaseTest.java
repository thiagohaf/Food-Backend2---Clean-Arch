package com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain.Restaurante;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.gateway.RestauranteGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListarRestaurantesUseCaseTest {

    @Mock
    private RestauranteGateway gateway;

    @InjectMocks
    private ListarRestaurantesUseCase useCase;

    @Test
    @DisplayName("Deve retornar uma lista de restaurantes")
    void deveRetornarListaDeRestaurantes() {
        List<Restaurante> lista = List.of(
                new Restaurante(UUID.randomUUID(), "Pizzaria", "Rua A", "Italiana", "18:00-23:00", UUID.randomUUID())
        );

        when(gateway.listarTodos()).thenReturn(lista);

        List<Restaurante> resultado = useCase.executar();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(gateway, times(1)).listarTodos();
    }
}
