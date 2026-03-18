package com.thiagoferreira.foodbackend2cleanarch.usuario.infra.controller;

import com.thiagoferreira.foodbackend2cleanarch.util.config.AbstractIntegrationTest;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.dto.TipoUsuarioRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

class TipoUsuarioControllerIntegrationTest extends AbstractIntegrationTest {

    private static final String ENDPOINT = "/api/v1/tipos-usuario";

    @Test
    @DisplayName("POST /api/v1/tipos-usuario deve retornar 201 Created e JSON do tipo de usuário")
    void deveCriarTipoUsuario() {
        given()
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .body(new TipoUsuarioRequest("DONO"))
        .when()
                .post(ENDPOINT)
        .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("nome", equalTo("DONO"));
    }

    @Test
    @DisplayName("GET /api/v1/tipos-usuario/{id} deve retornar 200 OK e JSON do tipo de usuário")
    void deveBuscarTipoUsuarioPorId() {
        UUID id = given()
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .body(new TipoUsuarioRequest("CLIENTE"))
        .when()
                .post(ENDPOINT)
        .then()
                .statusCode(201)
                .extract()
                .body()
                .jsonPath()
                .getUUID("id");

        given()
                .accept(APPLICATION_JSON_VALUE)
        .when()
                .get(ENDPOINT + "/{id}", id)
        .then()
                .statusCode(200)
                .body("id", equalTo(id.toString()))
                .body("nome", equalTo("CLIENTE"));
    }

    @Test
    @DisplayName("GET /api/v1/tipos-usuario deve retornar 200 OK e lista (com ao menos 1 item)")
    void deveListarTiposUsuario() {
        given()
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .body(new TipoUsuarioRequest("ADMIN"))
        .when()
                .post(ENDPOINT)
        .then()
                .statusCode(201);

        given()
                .accept(APPLICATION_JSON_VALUE)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    @DisplayName("PUT /api/v1/tipos-usuario/{id} deve retornar 200 OK e JSON atualizado")
    void deveAtualizarTipoUsuario() {
        UUID id = given()
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .body(new TipoUsuarioRequest("GERENTE"))
        .when()
                .post(ENDPOINT)
        .then()
                .statusCode(201)
                .extract()
                .body()
                .jsonPath()
                .getUUID("id");

        given()
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .body(new TipoUsuarioRequest("GERENTE_ATUALIZADO"))
        .when()
                .put(ENDPOINT + "/{id}", id)
        .then()
                .statusCode(200)
                .body("id", equalTo(id.toString()))
                .body("nome", equalTo("GERENTE_ATUALIZADO"));
    }

    @Test
    @DisplayName("DELETE /api/v1/tipos-usuario/{id} deve retornar 204 No Content")
    void deveExcluirTipoUsuario() {
        UUID id = given()
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .body(new TipoUsuarioRequest("EXCLUIR"))
        .when()
                .post(ENDPOINT)
        .then()
                .statusCode(201)
                .extract()
                .body()
                .jsonPath()
                .getUUID("id");

        given()
        .when()
                .delete(ENDPOINT + "/{id}", id)
        .then()
                .statusCode(204);
    }
}

