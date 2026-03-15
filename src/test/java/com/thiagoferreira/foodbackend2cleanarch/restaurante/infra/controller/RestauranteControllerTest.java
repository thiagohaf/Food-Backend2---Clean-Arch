package com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.controller;

import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain.Restaurante;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.dto.AtualizarRestauranteInput;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase.AtualizarRestauranteUseCase;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase.BuscarRestaurantePorIdUseCase;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase.CriarRestauranteUseCase;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase.ExcluirRestauranteUseCase;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase.ListarRestaurantesUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestauranteController.class)
class RestauranteControllerTest {

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
    @DisplayName("POST /api/v1/restaurantes deve retornar 201 Created e header Location")
    void deveRetornar201CreatedEHeaderLocationAoCriar() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        UUID donoId = UUID.randomUUID();
        Restaurante restaurante = new Restaurante(id, "Pizzaria", "Rua A", "Italiana", "18:00-23:00", donoId);
        when(criarRestauranteUseCase.executar(any())).thenReturn(restaurante);

        String body = """
                {
                    "nome": "Pizzaria",
                    "endereco": "Rua A",
                    "tipoCozinha": "Italiana",
                    "horarioFuncionamento": "18:00-23:00",
                    "donoId": "%s"
                }
                """.formatted(donoId);

        // Act & Assert
        mockMvc.perform(post("/api/v1/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", containsString("/api/v1/restaurantes/")))
                .andExpect(header().string("Location", containsString(id.toString())))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nome").value("Pizzaria"))
                .andExpect(jsonPath("$.endereco").value("Rua A"))
                .andExpect(jsonPath("$.tipoCozinha").value("Italiana"))
                .andExpect(jsonPath("$.horarioFuncionamento").value("18:00-23:00"))
                .andExpect(jsonPath("$.donoId").value(donoId.toString()));

        verify(criarRestauranteUseCase).executar(any());
    }

    @Test
    @DisplayName("GET /api/v1/restaurantes/{id} deve retornar 200 OK e JSON do restaurante")
    void deveRetornar200OKEJsonAoBuscarPorId() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        UUID donoId = UUID.randomUUID();
        Restaurante restaurante = new Restaurante(id, "Sushi Bar", "Av. Brasil, 100", "Japonesa", "19:00-00:00", donoId);
        when(buscarRestaurantePorIdUseCase.executar(id)).thenReturn(restaurante);

        // Act & Assert
        mockMvc.perform(get("/api/v1/restaurantes/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nome").value("Sushi Bar"))
                .andExpect(jsonPath("$.endereco").value("Av. Brasil, 100"))
                .andExpect(jsonPath("$.tipoCozinha").value("Japonesa"))
                .andExpect(jsonPath("$.horarioFuncionamento").value("19:00-00:00"))
                .andExpect(jsonPath("$.donoId").value(donoId.toString()));

        verify(buscarRestaurantePorIdUseCase).executar(id);
    }

    @Test
    @DisplayName("GET /api/v1/restaurantes deve retornar 200 OK e lista de restaurantes")
    void deveRetornar200OKEListaAoListar() throws Exception {
        // Arrange
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID donoId = UUID.randomUUID();
        Restaurante r1 = new Restaurante(id1, "Pizzaria", "Rua A", "Italiana", "18:00-23:00", donoId);
        Restaurante r2 = new Restaurante(id2, "Sushi", "Rua B", "Japonesa", "19:00-23:00", donoId);
        when(listarRestaurantesUseCase.executar()).thenReturn(List.of(r1, r2));

        // Act & Assert
        mockMvc.perform(get("/api/v1/restaurantes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(id1.toString()))
                .andExpect(jsonPath("$[0].nome").value("Pizzaria"))
                .andExpect(jsonPath("$[1].id").value(id2.toString()))
                .andExpect(jsonPath("$[1].nome").value("Sushi"));

        verify(listarRestaurantesUseCase).executar();
    }

    @Test
    @DisplayName("PUT /api/v1/restaurantes/{id} deve retornar 200 OK e JSON atualizado")
    void deveRetornar200OKEJsonAoAtualizar() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        UUID donoId = UUID.randomUUID();
        Restaurante atualizado = new Restaurante(id, "Pizzaria Nova", "Rua B", "Italiana", "17:00-22:00", donoId);
        when(atualizarRestauranteUseCase.executar(eq(id), any(AtualizarRestauranteInput.class))).thenReturn(atualizado);

        String body = """
                {
                    "nome": "Pizzaria Nova",
                    "endereco": "Rua B",
                    "tipoCozinha": "Italiana",
                    "horarioFuncionamento": "17:00-22:00"
                }
                """;

        // Act & Assert
        mockMvc.perform(put("/api/v1/restaurantes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nome").value("Pizzaria Nova"))
                .andExpect(jsonPath("$.endereco").value("Rua B"))
                .andExpect(jsonPath("$.horarioFuncionamento").value("17:00-22:00"));

        verify(atualizarRestauranteUseCase).executar(eq(id), any(AtualizarRestauranteInput.class));
    }

    @Test
    @DisplayName("DELETE /api/v1/restaurantes/{id} deve retornar 204 No Content")
    void deveRetornar204NoContentAoExcluir() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();

        // Act & Assert
        mockMvc.perform(delete("/api/v1/restaurantes/{id}", id))
                .andExpect(status().isNoContent());

        verify(excluirRestauranteUseCase).executar(id);
    }
}
