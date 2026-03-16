package com.thiagoferreira.foodbackend2cleanarch.usuario.infra.controller;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Usuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.AtualizarUsuarioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.BuscarUsuarioPorIdUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.CriarUsuarioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.ExcluirUsuarioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.ListarUsuariosUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.controller.UsuarioController;
import com.thiagoferreira.foodbackend2cleanarch.util.config.GlobalExceptionHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
@Import(GlobalExceptionHandler.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CriarUsuarioUseCase criarUsuarioUseCase;

    @MockBean
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @MockBean
    private ListarUsuariosUseCase listarUsuariosUseCase;

    @MockBean
    private AtualizarUsuarioUseCase atualizarUsuarioUseCase;

    @MockBean
    private ExcluirUsuarioUseCase excluirUsuarioUseCase;

    @Test
    @DisplayName("POST /api/v1/usuarios com JSON válido deve retornar 201 Created")
    void deveRetornar201CreatedComPayloadValido() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        UUID tipoUsuarioId = UUID.randomUUID();
        TipoUsuario tipo = new TipoUsuario(tipoUsuarioId, "Cliente");
        Usuario usuario = new Usuario(id, "João Silva", tipo);
        when(criarUsuarioUseCase.executar(any())).thenReturn(usuario);

        String body = """
                {
                    "nome": "João Silva",
                    "tipoUsuarioId": "%s"
                }
                """.formatted(tipoUsuarioId);

        // Act & Assert
        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", containsString("/api/v1/usuarios/")))
                .andExpect(header().string("Location", containsString(id.toString())))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.tipoUsuario.id").value(tipoUsuarioId.toString()))
                .andExpect(jsonPath("$.tipoUsuario.nome").value("Cliente"));

        verify(criarUsuarioUseCase).executar(any());
    }

    @Test
    @DisplayName("POST /api/v1/usuarios com nome em branco deve retornar 400 Bad Request por validação")
    void deveRetornar400BadRequestQuandoNomeEmBranco() throws Exception {
        // Arrange
        UUID tipoUsuarioId = UUID.randomUUID();
        String body = """
                {
                    "nome": "   ",
                    "tipoUsuarioId": "%s"
                }
                """.formatted(tipoUsuarioId);

        // Act & Assert
        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());

        verify(criarUsuarioUseCase, org.mockito.Mockito.never()).executar(any());
    }

    @Test
    @DisplayName("POST /api/v1/usuarios com tipoUsuarioId nulo deve retornar 400 Bad Request por validação")
    void deveRetornar400BadRequestQuandoTipoUsuarioIdNulo() throws Exception {
        // Arrange
        String body = """
                {
                    "nome": "João Silva",
                    "tipoUsuarioId": null
                }
                """;

        // Act & Assert
        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());

        verify(criarUsuarioUseCase, org.mockito.Mockito.never()).executar(any());
    }

    @Test
    @DisplayName("PUT /api/v1/usuarios/{id} com nome em branco deve retornar 400 Bad Request por validação")
    void deveRetornar400BadRequestAoAtualizarQuandoNomeEmBranco() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        UUID tipoUsuarioId = UUID.randomUUID();
        String body = """
                {
                    "nome": "   ",
                    "tipoUsuarioId": "%s"
                }
                """.formatted(tipoUsuarioId);

        // Act & Assert
        mockMvc.perform(put("/api/v1/usuarios/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());

        verify(atualizarUsuarioUseCase, org.mockito.Mockito.never()).executar(any(), any());
    }

    @Test
    @DisplayName("PUT /api/v1/usuarios/{id} com tipoUsuarioId nulo deve retornar 400 Bad Request por validação")
    void deveRetornar400BadRequestAoAtualizarQuandoTipoUsuarioIdNulo() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        String body = """
                {
                    "nome": "João Silva",
                    "tipoUsuarioId": null
                }
                """;

        // Act & Assert
        mockMvc.perform(put("/api/v1/usuarios/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());

        verify(atualizarUsuarioUseCase, org.mockito.Mockito.never()).executar(any(), any());
    }

    @Test
    @DisplayName("GET /api/v1/usuarios/{id} deve retornar 404 Not Found quando usuário não existir")
    void deveRetornar404QuandoUsuarioNaoExistir() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        when(buscarUsuarioPorIdUseCase.buscarPorId(id))
                .thenThrow(new com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.UsuarioNaoEncontradoException());

        // Act & Assert
        mockMvc.perform(get("/api/v1/usuarios/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Recurso não encontrado"))
                .andExpect(jsonPath("$.detail").value("O Usuário informado não foi encontrado."));

        verify(buscarUsuarioPorIdUseCase).buscarPorId(id);
    }
}
