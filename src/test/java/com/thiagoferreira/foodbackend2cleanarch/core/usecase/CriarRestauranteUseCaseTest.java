package com.thiagoferreira.foodbackend2cleanarch.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain.Restaurante;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.dto.CriarRestauranteInput;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.exception.RestauranteDonoNaoExisteException;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.exception.RestauranteExistenteException;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.gateway.RestauranteGateway;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.mapper.RestauranteCoreMapper;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase.CriarRestauranteUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CriarRestauranteUseCaseTest {
    @Mock
    private RestauranteGateway restauranteGateway;

    @Mock
    private RestauranteCoreMapper mapper;

    @InjectMocks
    private CriarRestauranteUseCase useCase;

    @Test
    @DisplayName("Deve criar e salvar restaurante com sucesso")
    void deveCriarESalvarRestauranteComSucesso() {
        UUID donoId = UUID.randomUUID();
        CriarRestauranteInput input = new CriarRestauranteInput(
                null,
                "Pizzaria",
                "Rua A",
                "Italiana",
                "18:00-23:00",
                donoId);
        Restaurante restauranteMapeado = new Restaurante(
                null,
                "Pizzaria",
                "Rua A",
                "Italiana",
                "18:00-23:00",
                donoId);
        Restaurante restauranteSalvo = new Restaurante(
                UUID.randomUUID(),
                "Pizzaria",
                "Rua A",
                "Italiana",
                "18:00-23:00",
                donoId);

        when(mapper.toDomain(input)).thenReturn(restauranteMapeado);
        when(restauranteGateway.existePorNomeEEndereco("Pizzaria", "Rua A")).thenReturn(false);
        when(restauranteGateway.donoValidoExiste(donoId)).thenReturn(true);
        when(restauranteGateway.salvar(restauranteMapeado)).thenReturn(restauranteSalvo);

        Restaurante resultado = useCase.executar(input);

        assertNotNull(resultado.getId());
        assertEquals("Pizzaria", resultado.getNome());

        verify(restauranteGateway, times(1)).salvar(restauranteMapeado);
    }

    @Test
    @DisplayName("Deve lançar RestauranteExistenteException quando nome e endereço já existirem")
    void deveLancarExcecaoQuandoRestauranteJaExistir() {
        CriarRestauranteInput input = new CriarRestauranteInput(
                null,
                "Pizzaria",
                "Rua A",
                "Italiana",
                "18:00-23:00",
                UUID.randomUUID());
        Restaurante restauranteMapeado = new Restaurante(
                null,
                "Pizzaria",
                "Rua A",
                "Italiana",
                "18:00-23:00",
                input.donoId());

        when(mapper.toDomain(input)).thenReturn(restauranteMapeado);
        when(restauranteGateway.existePorNomeEEndereco("Pizzaria", "Rua A")).thenReturn(true);

        assertThrows(RestauranteExistenteException.class, () -> useCase.executar(input));

        verify(restauranteGateway, never()).salvar(any());
        verify(restauranteGateway, never()).donoValidoExiste(any());
    }

    @Test
    @DisplayName("Deve lançar RestauranteDonoNaoExisteException quando dono não existir ou for inválido")
    void deveLancarExcecaoQuandoDonoForInvalido() {
        UUID donoId = UUID.randomUUID();
        CriarRestauranteInput input = new CriarRestauranteInput(
                null,
                "Pizzaria",
                "Rua A",
                "Italiana",
                "18:00-23:00",
                donoId);
        Restaurante restauranteMapeado = new Restaurante(
                null,
                "Pizzaria",
                "Rua A",
                "Italiana",
                "18:00-23:00",
                donoId);

        when(mapper.toDomain(input)).thenReturn(restauranteMapeado);
        when(restauranteGateway.existePorNomeEEndereco("Pizzaria", "Rua A")).thenReturn(false);
        when(restauranteGateway.donoValidoExiste(donoId)).thenReturn(false);

        assertThrows(RestauranteDonoNaoExisteException.class, () -> useCase.executar(input));

        verify(restauranteGateway, never()).salvar(any());
    }
}
