package com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.controller;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase.AtualizarItemCardapioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase.BuscarItemCardapioPorIdUseCase;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase.CriarItemCardapioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase.ExcluirItemCardapioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase.ListarItensCardapioPorRestauranteUseCase;
import com.thiagoferreira.foodbackend2cleanarch.util.config.GlobalExceptionHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(ItemCardapioController.class)
@Import(GlobalExceptionHandler.class)
class ItemCardapioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CriarItemCardapioUseCase criarItemCardapioUseCase;

    @MockBean
    private BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase;

    @MockBean
    private AtualizarItemCardapioUseCase atualizarItemCardapioUseCase;

    @MockBean
    private ExcluirItemCardapioUseCase excluirItemCardapioUseCase;

    @MockBean
    private ListarItensCardapioPorRestauranteUseCase listarItensCardapioPorRestauranteUseCase;

    @Test
    @DisplayName("POST /api/v1/itens-cardapio com payload válido deve retornar 201 Created e header Location")
    void deveRetornar201CreatedEHeaderLocationComPayloadValido() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        UUID restauranteId = UUID.randomUUID();
        ItemCardapio item = new ItemCardapio(
                id,
                "Hambúrguer",
                "Hambúrguer artesanal",
                new BigDecimal("25.50"),
                true,
                "/img/hamburguer.png",
                restauranteId
        );

        when(criarItemCardapioUseCase.executar(any())).thenReturn(item);

        String body = """
                {
                    "nome": "Hambúrguer",
                    "descricao": "Hambúrguer artesanal",
                    "preco": 25.50,
                    "fotoPath": "/img/hamburguer.png",
                    "restauranteId": "%s"
                }
                """.formatted(restauranteId);

        // Act & Assert
        mockMvc.perform(post("/api/v1/itens-cardapio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", containsString("/api/v1/itens-cardapio/")))
                .andExpect(header().string("Location", containsString(id.toString())))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nome").value("Hambúrguer"))
                .andExpect(jsonPath("$.descricao").value("Hambúrguer artesanal"))
                .andExpect(jsonPath("$.preco").value(25.50))
                .andExpect(jsonPath("$.fotoPath").value("/img/hamburguer.png"))
                .andExpect(jsonPath("$.restauranteId").value(restauranteId.toString()));

        verify(criarItemCardapioUseCase).executar(any());
    }

    @Test
    @DisplayName("POST /api/v1/itens-cardapio com nome em branco deve retornar 400 Bad Request por validação")
    void deveRetornar400BadRequestQuandoNomeEmBranco() throws Exception {
        // Arrange
        UUID restauranteId = UUID.randomUUID();
        String body = """
                {
                    "nome": "   ",
                    "descricao": "Hambúrguer artesanal",
                    "preco": 25.50,
                    "fotoPath": "/img/hamburguer.png",
                    "restauranteId": "%s"
                }
                """.formatted(restauranteId);

        // Act & Assert
        mockMvc.perform(post("/api/v1/itens-cardapio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());

        verify(criarItemCardapioUseCase, never()).executar(any());
    }

    @Test
    @DisplayName("POST /api/v1/itens-cardapio com preco negativo deve retornar 400 Bad Request por validação")
    void deveRetornar400BadRequestQuandoPrecoNegativo() throws Exception {
        // Arrange
        UUID restauranteId = UUID.randomUUID();
        String body = """
                {
                    "nome": "Hambúrguer",
                    "descricao": "Hambúrguer artesanal",
                    "preco": -10.00,
                    "fotoPath": "/img/hamburguer.png",
                    "restauranteId": "%s"
                }
                """.formatted(restauranteId);

        // Act & Assert
        mockMvc.perform(post("/api/v1/itens-cardapio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());

        verify(criarItemCardapioUseCase, never()).executar(any());
    }

    @Test
    @DisplayName("GET /api/v1/itens-cardapio/{id} deve retornar 200 OK e JSON do item")
    void deveRetornar200OKEJsonAoBuscarPorId() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        UUID restauranteId = UUID.randomUUID();
        ItemCardapio item = new ItemCardapio(
                id,
                "Pizza Margherita",
                "Pizza clássica",
                new BigDecimal("39.90"),
                true,
                "/img/pizza.png",
                restauranteId
        );

        when(buscarItemCardapioPorIdUseCase.executar(id)).thenReturn(item);

        // Act & Assert
        mockMvc.perform(get("/api/v1/itens-cardapio/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nome").value("Pizza Margherita"))
                .andExpect(jsonPath("$.descricao").value("Pizza clássica"))
                .andExpect(jsonPath("$.preco").value(39.90))
                .andExpect(jsonPath("$.fotoPath").value("/img/pizza.png"))
                .andExpect(jsonPath("$.restauranteId").value(restauranteId.toString()));

        verify(buscarItemCardapioPorIdUseCase).executar(id);
    }

    @Test
    @DisplayName("GET /api/v1/itens-cardapio?restauranteId=... deve retornar 200 OK e lista de itens")
    void deveRetornar200OKEListaAoListarPorRestaurante() throws Exception {
        UUID restauranteId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();

        when(listarItensCardapioPorRestauranteUseCase.executar(restauranteId)).thenReturn(List.of(
                new ItemCardapio(
                        itemId,
                        "Pizza",
                        "Saborosa",
                        new BigDecimal("40.00"),
                        true,
                        "/img/pizza.png",
                        restauranteId
                )
        ));

        mockMvc.perform(get("/api/v1/itens-cardapio")
                        .param("restauranteId", restauranteId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(itemId.toString()))
                .andExpect(jsonPath("$[0].nome").value("Pizza"))
                .andExpect(jsonPath("$[0].descricao").value("Saborosa"))
                .andExpect(jsonPath("$[0].preco").value(40.00))
                .andExpect(jsonPath("$[0].fotoPath").value("/img/pizza.png"))
                .andExpect(jsonPath("$[0].restauranteId").value(restauranteId.toString()));

        verify(listarItensCardapioPorRestauranteUseCase).executar(restauranteId);
    }

    @Test
    @DisplayName("PUT /api/v1/itens-cardapio/{id} com nome em branco deve retornar 400 Bad Request por validação")
    void deveRetornar400BadRequestQuandoAtualizarComNomeEmBranco() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        String body = """
                {
                    "nome": "   ",
                    "descricao": "Hambúrguer artesanal",
                    "preco": 25.50,
                    "fotoPath": "/img/hamburguer.png",
                    "restauranteId": "%s"
                }
                """.formatted(UUID.randomUUID());

        // Act & Assert
        mockMvc.perform(put("/api/v1/itens-cardapio/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());

        verify(atualizarItemCardapioUseCase, never()).executar(any(), any());
    }

    @Test
    @DisplayName("PUT /api/v1/itens-cardapio/{id} com preço negativo deve retornar 400 Bad Request por validação")
    void deveRetornar400BadRequestQuandoAtualizarComPrecoNegativo() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        String body = """
                {
                    "nome": "Hambúrguer",
                    "descricao": "Hambúrguer artesanal",
                    "preco": -10.00,
                    "fotoPath": "/img/hamburguer.png",
                    "restauranteId": "%s"
                }
                """.formatted(UUID.randomUUID());

        // Act & Assert
        mockMvc.perform(put("/api/v1/itens-cardapio/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());

        verify(atualizarItemCardapioUseCase, never()).executar(any(), any());
    }

    @Test
    @DisplayName("GET /api/v1/itens-cardapio/{id} deve retornar 404 Not Found quando item não existir")
    void deveRetornar404QuandoItemCardapioNaoExistir() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        when(buscarItemCardapioPorIdUseCase.executar(id))
                .thenThrow(new com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.ItemCardapioNaoEncontradoException());

        // Act & Assert
        mockMvc.perform(get("/api/v1/itens-cardapio/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Recurso não encontrado"))
                .andExpect(jsonPath("$.detail").value("Item do cardápio não encontrado."));

        verify(buscarItemCardapioPorIdUseCase).executar(id);
    }
}

