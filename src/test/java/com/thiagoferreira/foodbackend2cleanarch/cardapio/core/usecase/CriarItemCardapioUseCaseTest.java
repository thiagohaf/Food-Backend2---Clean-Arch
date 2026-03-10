package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.dto.CriarItemCardapioInput;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.ItemCardapioExistenteException;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.RestauranteNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.gateway.ItemCardapioGateway;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.mapper.ItemCardapioCoreMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CriarItemCardapioUseCaseTest {

    @Mock
    private ItemCardapioGateway itemCardapioGateway;

    @Mock
    private ItemCardapioCoreMapper mapper;

    @InjectMocks
    private CriarItemCardapioUseCase useCase;

    @Test
    @DisplayName("Deve criar e salvar item do cardápio com sucesso")
    void deveCriarESalvarItemComSucesso() {
        // Arrange
        UUID restauranteId = UUID.randomUUID();
        CriarItemCardapioInput input = new CriarItemCardapioInput(
                null,
                "Lasanha",
                "Deliciosa",
                new BigDecimal("45.00"),
                true,
                "path",
                restauranteId);
        ItemCardapio itemMapeado = new ItemCardapio(
                null,
                "Lasanha",
                "Deliciosa",
                new BigDecimal("45.00"),
                true,
                "path",
                restauranteId);
        ItemCardapio itemSalvo = new ItemCardapio(
                UUID.randomUUID(),
                "Lasanha",
                "Deliciosa",
                new BigDecimal("45.00"),
                true,
                "path",
                restauranteId);

        when(mapper.toDomain(input)).thenReturn(itemMapeado);
        when(itemCardapioGateway.restauranteExiste(restauranteId)).thenReturn(true);
        when(itemCardapioGateway.existeItemPorNomeERestaurante("Lasanha", restauranteId)).thenReturn(false);
        when(itemCardapioGateway.salvar(itemMapeado)).thenReturn(itemSalvo);

        ItemCardapio resultado = useCase.executar(input);

        assertNotNull(resultado.getId());
        assertEquals("Lasanha", resultado.getNome());
        verify(itemCardapioGateway, times(1)).salvar(itemMapeado);
    }

    @Test
    @DisplayName("Deve lançar RestauranteNaoEncontradoException quando o restaurante não existir")
    void deveLancarExcecaoQuandoRestauranteNaoExistir() {

        UUID restauranteId = UUID.randomUUID();
        CriarItemCardapioInput input = new CriarItemCardapioInput(
                null,
                "Lasanha",
                "Deliciosa",
                new BigDecimal("45.00"),
                true,
                "path",
                restauranteId);
        ItemCardapio itemMapeado = new ItemCardapio(
                null,
                "Lasanha",
                "Deliciosa",
                new BigDecimal("45.00"),
                true,
                "path",
                restauranteId);

        when(mapper.toDomain(input)).thenReturn(itemMapeado);

        when(itemCardapioGateway.restauranteExiste(restauranteId)).thenReturn(false);

        assertThrows(RestauranteNaoEncontradoException.class, () -> useCase.executar(input));

        verify(itemCardapioGateway, never()).existeItemPorNomeERestaurante(any(), any());
        verify(itemCardapioGateway, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve lançar ItemCardapioExistenteException quando o item já existir no restaurante")
    void deveLancarExcecaoQuandoItemJaExistirNoRestaurante() {
        // Arrange
        UUID restauranteId = UUID.randomUUID();
        CriarItemCardapioInput input = new CriarItemCardapioInput(
                null,
                "Lasanha",
                "Deliciosa",
                new BigDecimal("45.00"),
                true,
                "path",
                restauranteId);
        ItemCardapio itemMapeado = new ItemCardapio(
                null,
                "Lasanha",
                "Deliciosa",
                new BigDecimal("45.00"),
                true,
                "path",
                restauranteId);

        when(mapper.toDomain(input)).thenReturn(itemMapeado);
        when(itemCardapioGateway.restauranteExiste(restauranteId)).thenReturn(true);

        when(itemCardapioGateway.existeItemPorNomeERestaurante("Lasanha", restauranteId)).thenReturn(true);

        assertThrows(ItemCardapioExistenteException.class, () -> useCase.executar(input));

        verify(itemCardapioGateway, never()).salvar(any());
    }
}
