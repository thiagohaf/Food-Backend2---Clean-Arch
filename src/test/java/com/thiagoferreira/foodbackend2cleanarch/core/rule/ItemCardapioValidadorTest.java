package com.thiagoferreira.foodbackend2cleanarch.core.rule;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;
import com.thiagoferreira.foodbackend2cleanarch.util.exception.ValidacaoRegraNegocioException;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.rule.ItemCardapioValidador;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ItemCardapioValidadorTest {

    private ItemCardapio criarItemValido() {
       return new ItemCardapio(
                "Hambúrguer Artesanal",
                "Pão brioche, blend de 180g e queijo cheddar",
                new BigDecimal("35.90"),
                true,
                null,
                UUID.randomUUID()
        );
    }

    @Test
    @DisplayName("Deve validar com sucesso um item com todos os dados corretos")
    void deveValidarComSucessoItemCorreto() {
        assertDoesNotThrow(() -> ItemCardapioValidador.validar(criarItemValido()));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o item for nulo")
    void deveLancarExcecaoQuandoItemNulo() {
        // Act & Assert
        ValidacaoRegraNegocioException exception = assertThrows(
                ValidacaoRegraNegocioException.class,
                () -> ItemCardapioValidador.validar(null)
        );

        assertEquals("O item do cardápio não pode ser nulo.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome for nulo ou vazio")
    void deveLancarExcecaoQuandoNomeNulo() {

        ValidacaoRegraNegocioException exceptionNull = assertThrows(
                ValidacaoRegraNegocioException.class,
                () -> new ItemCardapio(
                        null,
                        "Pão brioche, blend de 180g e queijo cheddar",
                        new BigDecimal("35.90"),
                        true,
                        null,
                        UUID.randomUUID()
                )
        );
        assertEquals("O campo Nome do Item não pode ser nulo ou vazio.", exceptionNull.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome for nulo ou vazio")
    void deveLancarExcecaoQuandoNomeVazio() {
        ValidacaoRegraNegocioException exceptionVazio = assertThrows(
                ValidacaoRegraNegocioException.class,
                () -> new ItemCardapio(
                        "    ",
                        "Pão brioche, blend de 180g e queijo cheddar",
                        new BigDecimal("35.90"),
                        true,
                        null,
                        UUID.randomUUID()
                )
        );
        assertEquals("O campo Nome do Item não pode ser nulo ou vazio.", exceptionVazio.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a descrição for nula ou vazia")
    void deveLancarExcecaoQuandoDescricaoNula() {
        ValidacaoRegraNegocioException exception = assertThrows(
                ValidacaoRegraNegocioException.class,
                () -> new ItemCardapio(
                        "Hambúrguer Artesanal",
                        null,
                        new BigDecimal("35.90"),
                        true,
                        null,
                        UUID.randomUUID()
                )
        );

        assertEquals("O campo Descrição não pode ser nulo ou vazio.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a descrição for nula ou vazia")
    void deveLancarExcecaoQuandoDescricaoVazia() {
        ValidacaoRegraNegocioException exception = assertThrows(
                ValidacaoRegraNegocioException.class,
                () -> new ItemCardapio(
                        "Hambúrguer Artesanal",
                        "",
                        new BigDecimal("35.90"),
                        true,
                        null,
                        UUID.randomUUID()
                )
        );

        assertEquals("O campo Descrição não pode ser nulo ou vazio.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o preço for nulo, zero ou negativo")
    void deveLancarExcecaoQuandoPrecoInvalido() {
        ItemCardapio item = criarItemValido();

        assertThrows(ValidacaoRegraNegocioException.class, () -> item.alterarPreco(null));

        assertThrows(ValidacaoRegraNegocioException.class, () -> item.alterarPreco(BigDecimal.ZERO));

        ValidacaoRegraNegocioException exception = assertThrows(
                ValidacaoRegraNegocioException.class,
                () -> item.alterarPreco(new BigDecimal("-10.00"))
        );

        assertEquals("O preço do item deve ser obrigatório e maior que zero.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando restauranteId for nulo")
    void deveLancarExcecaoQuandoRestauranteIdNulo() {
        ValidacaoRegraNegocioException exception = assertThrows(
                ValidacaoRegraNegocioException.class,
                () -> new ItemCardapio(
                        "Hambúrguer Artesanal",
                        "Pão brioche, blend de 180g e queijo cheddar",
                        new BigDecimal("35.90"),
                        true,
                        null,
                        null
                )
        );

        assertEquals("O item deve pertencer a um restaurante válido (ID não pode ser nulo).", exception.getMessage());
    }
}
