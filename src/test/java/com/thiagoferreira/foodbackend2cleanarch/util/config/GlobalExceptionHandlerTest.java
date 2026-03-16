package com.thiagoferreira.foodbackend2cleanarch.util.config;

import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.controller.RestauranteController;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase.CriarRestauranteUseCase;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase.BuscarRestaurantePorIdUseCase;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase.ListarRestaurantesUseCase;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase.AtualizarRestauranteUseCase;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase.ExcluirRestauranteUseCase;
import com.thiagoferreira.foodbackend2cleanarch.util.exception.ValidacaoRegraNegocioException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Teste de integração web para o {@link GlobalExceptionHandler}:
 * garante que ValidacaoRegraNegocioException retorna 422 e corpo RFC 7807 (ProblemDetail).
 */
@WebMvcTest(RestauranteController.class)
@Import(GlobalExceptionHandler.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CriarRestauranteUseCase criarRestauranteUseCase;

    @MockBean
    private BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;

    @MockBean
    private ListarRestaurantesUseCase listarRestaurantesUseCase;

    @MockBean
    private AtualizarRestauranteUseCase atualizarRestauranteUseCase;

    @MockBean
    private ExcluirRestauranteUseCase excluirRestauranteUseCase;

    @Test
    @DisplayName("Deve retornar 422 Unprocessable Entity quando UseCase lançar ValidacaoRegraNegocioException")
    void deveRetornar422QuandoValidacaoRegraNegocioException() throws Exception {
        // Arrange
        String mensagemEsperada = "O restaurante deve ter um dono (usuário) associado.";
        when(criarRestauranteUseCase.executar(any()))
                .thenThrow(new ValidacaoRegraNegocioException(mensagemEsperada));

        String body = """
                {
                    "nome": "Pizzaria",
                    "endereco": "Rua A",
                    "tipoCozinha": "Italiana",
                    "horarioFuncionamento": "18:00-23:00",
                    "donoId": null
                }
                """;

        // Act & Assert
        mockMvc.perform(post("/api/v1/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnprocessableEntity()) // 422
                .andExpect(content().contentType(MediaType.parseMediaType("application/problem+json")))
                .andExpect(jsonPath("$.title").value("Validação de regra de negócio"))
                .andExpect(jsonPath("$.detail").value(mensagemEsperada))
                .andExpect(jsonPath("$.status").value(422));
    }

    @Test
    @DisplayName("Deve retornar corpo RFC 7807 (ProblemDetail) com title e detail no GET quando UseCase lançar exceção")
    void deveRetornarProblemDetailRfc7807EmGetQuandoExcecao() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        String mensagemEsperada = "Restaurante não encontrado para o ID informado.";
        when(buscarRestaurantePorIdUseCase.executar(id))
                .thenThrow(new ValidacaoRegraNegocioException(mensagemEsperada));

        // Act & Assert
        mockMvc.perform(get("/api/v1/restaurantes/{id}", id))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.title").value("Validação de regra de negócio"))
                .andExpect(jsonPath("$.detail").value(mensagemEsperada));
    }

    @Test
    @DisplayName("Deve tratar MethodArgumentNotValidException retornando 400 Bad Request com detalhes dos campos inválidos")
    void deveTratarMethodArgumentNotValidExceptionComDetalhes() {
        // Arrange
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        var bindingResult = new org.springframework.validation.BeanPropertyBindingResult(new Object(), "request");
        bindingResult.addError(new org.springframework.validation.FieldError(
                "request", "nome", "não deve estar em branco"));
        bindingResult.addError(new org.springframework.validation.FieldError(
                "request", "endereco", "é obrigatório"));

        var ex = new org.springframework.web.bind.MethodArgumentNotValidException(null, bindingResult);

        // Act
        ResponseEntity<ProblemDetail> response = handler.handleMethodArgumentNotValid(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Erro de validação", response.getBody().getTitle());
        assertTrue(response.getBody().getDetail().contains("nome"));
        assertTrue(response.getBody().getDetail().contains("endereco"));
    }

    @Test
    @DisplayName("Deve tratar exceções de recurso não encontrado retornando 404 Not Found com ProblemDetail")
    void deveTratarExcecaoNaoEncontradoCom404() {
        // Arrange
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Exception ex = new com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.UsuarioNaoEncontradoException();

        // Act
        ResponseEntity<ProblemDetail> response = handler.handleNaoEncontrado(ex);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Recurso não encontrado", response.getBody().getTitle());
        assertEquals("O Usuário informado não foi encontrado.", response.getBody().getDetail());
    }

    @Test
    @DisplayName("Deve tratar exceção genérica retornando 500 Internal Server Error com ProblemDetail padrão")
    void deveTratarExcecaoGenericaCom500() {
        // Arrange
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Exception ex = new RuntimeException("falha inesperada");

        // Act
        ResponseEntity<ProblemDetail> response = handler.handleErroInesperado(ex);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), response.getBody().getTitle());
        assertEquals("Erro inesperado", response.getBody().getDetail());
    }
}
