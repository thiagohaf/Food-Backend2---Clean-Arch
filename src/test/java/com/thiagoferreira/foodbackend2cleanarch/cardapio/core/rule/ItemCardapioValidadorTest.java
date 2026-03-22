package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.rule;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;
import com.thiagoferreira.foodbackend2cleanarch.util.exception.ValidacaoRegraNegocioException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ItemCardapioValidadorTest {

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
        String nomeNulo = null;
        String descricao = "Pão brioche, blend de 180g e queijo cheddar";
        BigDecimal preco = new BigDecimal("35.90");

        ValidacaoRegraNegocioException exceptionNull = assertThrows(
                ValidacaoRegraNegocioException.class,
                () -> new ItemCardapio(nomeNulo, descricao, preco, true, null, UUID.randomUUID())
        );

        assertEquals("O campo Nome do Item não pode ser nulo ou vazio.", exceptionNull.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome for nulo ou vazio")
    void deveLancarExcecaoQuandoNomeVazio() {
        String nomeVazio = "    ";
        String descricao = "Pão brioche, blend de 180g e queijo cheddar";
        BigDecimal preco = new BigDecimal("35.90");

        ValidacaoRegraNegocioException exceptionVazio = assertThrows(
                ValidacaoRegraNegocioException.class,
                () -> new ItemCardapio(nomeVazio, descricao, preco, true, null, UUID.randomUUID())
        );

        assertEquals("O campo Nome do Item não pode ser nulo ou vazio.", exceptionVazio.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a descrição for nula ou vazia")
    void deveLancarExcecaoQuandoDescricaoNula() {
        String nome = "Hambúrguer Artesanal";
        String descricaoNula = null;
        BigDecimal preco = new BigDecimal("35.90");

        ValidacaoRegraNegocioException exception = assertThrows(
                ValidacaoRegraNegocioException.class,
                () -> new ItemCardapio(nome, descricaoNula, preco, true, null, UUID.randomUUID())
        );

        assertEquals("O campo Descrição não pode ser nulo ou vazio.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a descrição for nula ou vazia")
    void deveLancarExcecaoQuandoDescricaoVazia() {
        String nome = "Hambúrguer Artesanal";
        String descricaoVazia = ""; // O alvo do teste
        BigDecimal preco = new BigDecimal("35.90");

        ValidacaoRegraNegocioException exception = assertThrows(
                ValidacaoRegraNegocioException.class,
                () -> new ItemCardapio(nome, descricaoVazia, preco, true, null, UUID.randomUUID())
        );

        assertEquals("O campo Descrição não pode ser nulo ou vazio.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve alterar preço com sucesso quando o novo valor é válido")
    void deveAlterarPrecoComSucessoQuandoValido() {
        ItemCardapio item = criarItemValido();
        item.alterarPreco(new BigDecimal("40.00"));
        assertEquals(new BigDecimal("40.00"), item.getPreco());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o preço for nulo, zero ou negativo")
    void deveLancarExcecaoQuandoPrecoInvalido() {
        ItemCardapio item = criarItemValido();
        BigDecimal precoZero = BigDecimal.ZERO;
        BigDecimal precoNegativo = new BigDecimal("-10.00");

        assertThrows(ValidacaoRegraNegocioException.class, () -> item.alterarPreco(null));
        assertThrows(ValidacaoRegraNegocioException.class, () -> item.alterarPreco(precoZero));

        ValidacaoRegraNegocioException exception = assertThrows(
                ValidacaoRegraNegocioException.class,
                () -> item.alterarPreco(precoNegativo)
        );

        assertEquals("O preço do item deve ser obrigatório e maior que zero.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando restauranteId for nulo")
    void deveLancarExcecaoQuandoRestauranteIdNulo() {
        String nome = "Hambúrguer Artesanal";
        String descricao = "Pão brioche, blend de 180g e queijo cheddar";
        BigDecimal preco = new BigDecimal("35.90");
        UUID restauranteIdNulo = null;

        ValidacaoRegraNegocioException exception = assertThrows(
                ValidacaoRegraNegocioException.class,
                () -> new ItemCardapio(nome, descricao, preco, true, null, restauranteIdNulo)
        );

        assertEquals("O item deve pertencer a um restaurante válido (ID não pode ser nulo).", exception.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar os dados do item com sucesso quando válidos")
    void deveAtualizarDadosComSucesso() {
        ItemCardapio item = new ItemCardapio(
                UUID.randomUUID(), "Hamburguer", "Pão e carne", new BigDecimal("25.00"),
                true, "/img/hamburguer.png", UUID.randomUUID()
        );

        item.atualizar("X-Salada", "Pão, carne e salada", new BigDecimal("30.00"), false, "/img/xsalada.png");

        assertEquals("X-Salada", item.getNome());
        assertEquals("Pão, carne e salada", item.getDescricao());
        assertEquals(new BigDecimal("30.00"), item.getPreco());
        assertFalse(item.getDisponibilidadeLocal());
        assertEquals("/img/xsalada.png", item.getFotoPath());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar com dados inválidos (ex: preço negativo)")
    void deveLancarExcecaoAoAtualizarComDadosInvalidos() {
        ItemCardapio item = new ItemCardapio(
                UUID.randomUUID(), "Hamburguer", "Pão e carne", new BigDecimal("25.00"),
                true, "/img/hamburguer.png", UUID.randomUUID()
        );

        BigDecimal precoInvalido = new BigDecimal("-5.00");

        assertThrows(ValidacaoRegraNegocioException.class, () ->
                item.atualizar("X-Salada", "Descricao", precoInvalido, false, "/img/foto.png")
        );
    }
}
