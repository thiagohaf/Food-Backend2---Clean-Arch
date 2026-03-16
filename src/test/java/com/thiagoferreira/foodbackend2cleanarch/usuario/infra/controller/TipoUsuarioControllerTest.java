package com.thiagoferreira.foodbackend2cleanarch.usuario.infra.controller;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.AtualizarTipoUsuarioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.BuscarTipoUsuarioPorIdUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.CriarTipoUsuarioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.ExcluirTipoUsuarioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.ListarTiposUsuarioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.controller.TipoUsuarioController;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TipoUsuarioController.class)
class TipoUsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CriarTipoUsuarioUseCase criarTipoUsuarioUseCase;

    @MockBean
    private BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase;

    @MockBean
    private ListarTiposUsuarioUseCase listarTiposUsuarioUseCase;

    @MockBean
    private AtualizarTipoUsuarioUseCase atualizarTipoUsuarioUseCase;

    @MockBean
    private ExcluirTipoUsuarioUseCase excluirTipoUsuarioUseCase;

    @Test
    @DisplayName("POST /api/v1/tipos-usuario deve retornar 201 Created e header Location")
    void deveRetornar201CreatedEHeaderLocationAoCriar() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        TipoUsuario tipoUsuario = new TipoUsuario(id, "Cliente");
        when(criarTipoUsuarioUseCase.executar(any())).thenReturn(tipoUsuario);

        String body = """
                {
                    "nome": "Cliente"
                }
                """;

        // Act & Assert
        mockMvc.perform(post("/api/v1/tipos-usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", containsString("/api/v1/tipos-usuario/")))
                .andExpect(header().string("Location", containsString(id.toString())))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nome").value("Cliente"));

        verify(criarTipoUsuarioUseCase).executar(any());
    }

    @Test
    @DisplayName("GET /api/v1/tipos-usuario/{id} deve retornar 200 OK e JSON mapeado para TipoUsuarioResponse")
    void deveRetornar200OKEJsonMapeadoParaTipoUsuarioResponse() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        TipoUsuario tipoUsuario = new TipoUsuario(id, "Admin");
        when(buscarTipoUsuarioPorIdUseCase.buscarPorId(id)).thenReturn(tipoUsuario);

        // Act & Assert
        mockMvc.perform(get("/api/v1/tipos-usuario/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nome").value("Admin"));

        verify(buscarTipoUsuarioPorIdUseCase).buscarPorId(id);
    }

    @Test
    @DisplayName("GET /api/v1/tipos-usuario deve retornar 200 OK e lista mapeada para TipoUsuarioResponse")
    void deveRetornar200OKEListaAoListar() throws Exception {
        // Arrange
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        TipoUsuario t1 = new TipoUsuario(id1, "Cliente");
        TipoUsuario t2 = new TipoUsuario(id2, "Admin");
        when(listarTiposUsuarioUseCase.listarTodos()).thenReturn(List.of(t1, t2));

        // Act & Assert
        mockMvc.perform(get("/api/v1/tipos-usuario"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(id1.toString()))
                .andExpect(jsonPath("$[0].nome").value("Cliente"))
                .andExpect(jsonPath("$[1].id").value(id2.toString()))
                .andExpect(jsonPath("$[1].nome").value("Admin"));

        verify(listarTiposUsuarioUseCase).listarTodos();
    }

    @Test
    @DisplayName("POST /api/v1/tipos-usuario com nome em branco deve retornar 400 Bad Request por validação")
    void deveRetornar400BadRequestQuandoCriarComNomeEmBranco() throws Exception {
        // Arrange
        String body = """
                {
                    "nome": "   "
                }
                """;

        // Act & Assert
        mockMvc.perform(post("/api/v1/tipos-usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());

        verify(criarTipoUsuarioUseCase, org.mockito.Mockito.never()).executar(any());
    }

    @Test
    @DisplayName("PUT /api/v1/tipos-usuario/{id} com nome em branco deve retornar 400 Bad Request por validação")
    void deveRetornar400BadRequestQuandoAtualizarComNomeEmBranco() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        String body = """
                {
                    "nome": "   "
                }
                """;

        // Act & Assert
        mockMvc.perform(put("/api/v1/tipos-usuario/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());

        verify(atualizarTipoUsuarioUseCase, org.mockito.Mockito.never()).executar(any(), any());
    }

    @Test
    @DisplayName("GET /api/v1/tipos-usuario/{id} deve retornar 404 Not Found quando tipo de usuário não existir")
    void deveRetornar404QuandoTipoUsuarioNaoExistir() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        when(buscarTipoUsuarioPorIdUseCase.buscarPorId(id))
                .thenThrow(new com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.TipoUsuarioNaoEncontradoException());

        // Act & Assert
        mockMvc.perform(get("/api/v1/tipos-usuario/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Recurso não encontrado"));

        verify(buscarTipoUsuarioPorIdUseCase).buscarPorId(id);
    }
}
