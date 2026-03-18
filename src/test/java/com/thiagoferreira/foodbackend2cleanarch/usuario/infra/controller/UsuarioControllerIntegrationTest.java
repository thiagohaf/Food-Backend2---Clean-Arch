package com.thiagoferreira.foodbackend2cleanarch.usuario.infra.controller;

import com.thiagoferreira.foodbackend2cleanarch.util.config.AbstractIntegrationTest;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.dto.TipoUsuarioRequest;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.dto.UsuarioRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

class UsuarioControllerIntegrationTest extends AbstractIntegrationTest {

    private static final String ENDPOINT_USUARIOS = "/api/v1/usuarios";
    private static final String ENDPOINT_TIPOS_USUARIO = "/api/v1/tipos-usuario";

    @Test
    @DisplayName("POST /api/v1/usuarios deve retornar 201 Created e JSON do usuário (com tipoUsuario)")
    void deveCriarUsuario() {
        String nomeTipoUsuario = "DONO-" + UUID.randomUUID();
        UUID tipoUsuarioId = criarTipoUsuario(nomeTipoUsuario);

        given()
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .body(new UsuarioRequest("Thiago", tipoUsuarioId))
        .when()
                .post(ENDPOINT_USUARIOS)
        .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("nome", equalTo("Thiago"))
                .body("tipoUsuario.id", equalTo(tipoUsuarioId.toString()))
                .body("tipoUsuario.nome", equalTo(nomeTipoUsuario));
    }

    @Test
    @DisplayName("GET /api/v1/usuarios/{id} deve retornar 200 OK e JSON do usuário")
    void deveBuscarUsuarioPorId() {
        String nomeTipoUsuario = "CLIENTE-" + UUID.randomUUID();
        UUID tipoUsuarioId = criarTipoUsuario(nomeTipoUsuario);
        UUID usuarioId = criarUsuario("Maria", tipoUsuarioId);

        given()
                .accept(APPLICATION_JSON_VALUE)
        .when()
                .get(ENDPOINT_USUARIOS + "/{id}", usuarioId)
        .then()
                .statusCode(200)
                .body("id", equalTo(usuarioId.toString()))
                .body("nome", equalTo("Maria"))
                .body("tipoUsuario.id", equalTo(tipoUsuarioId.toString()))
                .body("tipoUsuario.nome", equalTo(nomeTipoUsuario));
    }

    @Test
    @DisplayName("GET /api/v1/usuarios deve retornar 200 OK e lista (com ao menos 1 usuário)")
    void deveListarUsuarios() {
        UUID tipoUsuarioId = criarTipoUsuario("ADMIN-" + UUID.randomUUID());
        criarUsuario("João", tipoUsuarioId);

        given()
                .accept(APPLICATION_JSON_VALUE)
        .when()
                .get(ENDPOINT_USUARIOS)
        .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    @DisplayName("PUT /api/v1/usuarios/{id} deve retornar 200 OK e JSON atualizado")
    void deveAtualizarUsuario() {
        UUID tipoUsuarioId1 = criarTipoUsuario("DONO-" + UUID.randomUUID());
        String nomeTipoUsuario2 = "CLIENTE-" + UUID.randomUUID();
        UUID tipoUsuarioId2 = criarTipoUsuario(nomeTipoUsuario2);
        UUID usuarioId = criarUsuario("Carlos", tipoUsuarioId1);

        given()
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .body(new UsuarioRequest("Carlos Atualizado", tipoUsuarioId2))
        .when()
                .put(ENDPOINT_USUARIOS + "/{id}", usuarioId)
        .then()
                .statusCode(200)
                .body("id", equalTo(usuarioId.toString()))
                .body("nome", equalTo("Carlos Atualizado"))
                .body("tipoUsuario.id", equalTo(tipoUsuarioId2.toString()))
                .body("tipoUsuario.nome", equalTo(nomeTipoUsuario2));
    }

    @Test
    @DisplayName("DELETE /api/v1/usuarios/{id} deve retornar 204 No Content")
    void deveExcluirUsuario() {
        UUID tipoUsuarioId = criarTipoUsuario("EXCLUIR-" + UUID.randomUUID());
        UUID usuarioId = criarUsuario("Apagar", tipoUsuarioId);

        given()
        .when()
                .delete(ENDPOINT_USUARIOS + "/{id}", usuarioId)
        .then()
                .statusCode(204);
    }

    private UUID criarTipoUsuario(String nome) {
        return given()
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .body(new TipoUsuarioRequest(nome))
        .when()
                .post(ENDPOINT_TIPOS_USUARIO)
        .then()
                .statusCode(201)
                .extract()
                .body()
                .jsonPath()
                .getUUID("id");
    }

    private UUID criarUsuario(String nome, UUID tipoUsuarioId) {
        return given()
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .body(new UsuarioRequest(nome, tipoUsuarioId))
        .when()
                .post(ENDPOINT_USUARIOS)
        .then()
                .statusCode(201)
                .extract()
                .body()
                .jsonPath()
                .getUUID("id");
    }
}

