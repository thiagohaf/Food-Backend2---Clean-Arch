package com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.controller;

import com.thiagoferreira.foodbackend2cleanarch.util.config.AbstractIntegrationTest;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.dto.ItemCardapioRequest;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.dto.CriarRestauranteRequest;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.dto.TipoUsuarioResponse;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.dto.TipoUsuarioRequest;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.dto.UsuarioRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

class ItemCardapioControllerIntegrationTest extends AbstractIntegrationTest {

    private static final String ENDPOINT_ITENS = "/api/v1/itens-cardapio";
    private static final String ENDPOINT_RESTAURANTES = "/api/v1/restaurantes";
    private static final String ENDPOINT_USUARIOS = "/api/v1/usuarios";
    private static final String ENDPOINT_TIPOS_USUARIO = "/api/v1/tipos-usuario";
    private static final String TIPO_DONO_RESTAURANTE = "DONO_RESTAURANTE";

    @Test
    @DisplayName("POST /api/v1/itens-cardapio deve retornar 201 Created e JSON do item")
    void deveCriarItemCardapio() {
        UUID restauranteId = criarRestauranteCompleto();

        given()
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .body(new ItemCardapioRequest(
                        "Hambúrguer",
                        "Hambúrguer artesanal",
                        new BigDecimal("25.50"),
                        "/img/hamburguer.png",
                        restauranteId
                ))
        .when()
                .post(ENDPOINT_ITENS)
        .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("nome", equalTo("Hambúrguer"))
                .body("descricao", equalTo("Hambúrguer artesanal"))
                .body("preco", equalTo(25.50f))
                .body("fotoPath", equalTo("/img/hamburguer.png"))
                .body("restauranteId", equalTo(restauranteId.toString()));
    }

    @Test
    @DisplayName("GET /api/v1/itens-cardapio/{id} deve retornar 200 OK e JSON do item")
    void deveBuscarItemPorId() {
        UUID restauranteId = criarRestauranteCompleto();
        UUID itemId = criarItem("Pizza", "Pizza clássica", new BigDecimal("39.90"), "/img/pizza.png", restauranteId);

        given()
                .accept(APPLICATION_JSON_VALUE)
        .when()
                .get(ENDPOINT_ITENS + "/{id}", itemId)
        .then()
                .statusCode(200)
                .body("id", equalTo(itemId.toString()))
                .body("nome", equalTo("Pizza"))
                .body("descricao", equalTo("Pizza clássica"))
                .body("preco", equalTo(39.90f))
                .body("fotoPath", equalTo("/img/pizza.png"))
                .body("restauranteId", equalTo(restauranteId.toString()));
    }

    @Test
    @DisplayName("GET /api/v1/itens-cardapio?restauranteId=... deve retornar 200 OK e lista vazia ao buscar itens de um restaurante sem cardápio")
    void deveListarItensPorRestauranteSemCardapio() {
        given()
                .queryParam("restauranteId", UUID.randomUUID())
        .when()
                .get(ENDPOINT_ITENS)
        .then()
                .statusCode(200)
                .body("$", hasSize(0));
    }

    @Test
    @DisplayName("GET /api/v1/itens-cardapio?restauranteId=... deve retornar 200 OK e lista de itens")
    void deveListarItensPorRestaurante() {
        UUID restauranteId = criarRestauranteCompleto();
        criarItem("Sushi", "Sushi variado", new BigDecimal("55.00"), "/img/sushi.png", restauranteId);

        given()
                .accept(APPLICATION_JSON_VALUE)
                .queryParam("restauranteId", restauranteId)
        .when()
                .get(ENDPOINT_ITENS)
        .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1))
                .body("[0].id", notNullValue())
                .body("[0].restauranteId", equalTo(restauranteId.toString()));
    }

    @Test
    @DisplayName("PUT /api/v1/itens-cardapio/{id} deve retornar 200 OK e JSON atualizado")
    void deveAtualizarItemCardapio() {
        UUID restauranteId = criarRestauranteCompleto();
        UUID itemId = criarItem("Item Antigo", "Desc antiga", new BigDecimal("10.00"), "/img/old.png", restauranteId);

        given()
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .body(new ItemCardapioRequest(
                        "Item Novo",
                        "Desc nova",
                        new BigDecimal("12.50"),
                        "/img/new.png",
                        restauranteId
                ))
        .when()
                .put(ENDPOINT_ITENS + "/{id}", itemId)
        .then()
                .statusCode(200)
                .body("id", equalTo(itemId.toString()))
                .body("nome", equalTo("Item Novo"))
                .body("descricao", equalTo("Desc nova"))
                .body("preco", equalTo(12.50f))
                .body("fotoPath", equalTo("/img/new.png"))
                .body("restauranteId", equalTo(restauranteId.toString()));
    }

    @Test
    @DisplayName("DELETE /api/v1/itens-cardapio/{id} deve retornar 204 No Content")
    void deveExcluirItemCardapio() {
        UUID restauranteId = criarRestauranteCompleto();
        UUID itemId = criarItem("Excluir", "Desc", new BigDecimal("1.00"), null, restauranteId);

        given()
        .when()
                .delete(ENDPOINT_ITENS + "/{id}", itemId)
        .then()
                .statusCode(204);
    }

    private UUID obterOuCriarTipoUsuario(String nome) {
        var response = given()
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .body(new TipoUsuarioRequest(nome))
                .when()
                .post(ENDPOINT_TIPOS_USUARIO);

        if (response.statusCode() == 201) {
            return response.jsonPath().getUUID("id");
        }

        if (response.statusCode() == 422) {
            return buscarTipoUsuarioIdPorNome(nome);
        }

        response.then().statusCode(201);
        throw new IllegalStateException("Unreachable");
    }

    private UUID buscarTipoUsuarioIdPorNome(String nome) {
        TipoUsuarioResponse[] tipos = given()
                .accept(APPLICATION_JSON_VALUE)
        .when()
                .get(ENDPOINT_TIPOS_USUARIO)
        .then()
                .statusCode(200)
                .extract()
                .as(TipoUsuarioResponse[].class);

        return Arrays.stream(tipos)
                .filter(t -> t.nome().equalsIgnoreCase(nome))
                .findFirst()
                .map(TipoUsuarioResponse::id)
                .orElseThrow(() -> new IllegalStateException("TipoUsuario não encontrado: " + nome));
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

    private UUID criarRestauranteCompleto() {
        UUID tipoDonoId = obterOuCriarTipoUsuario(TIPO_DONO_RESTAURANTE);
        UUID donoId = criarUsuario("Dono Cardapio", tipoDonoId);

        String nomeRestaurante = "Restaurante Cardapio-" + UUID.randomUUID();
        String enderecoRestaurante = "Rua Cardapio, " + UUID.randomUUID();

        return given()
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .body(new CriarRestauranteRequest(
                        nomeRestaurante,
                        enderecoRestaurante,
                        "Italiana",
                        "10:00-22:00",
                        donoId
                ))
        .when()
                .post(ENDPOINT_RESTAURANTES)
        .then()
                .statusCode(201)
                .extract()
                .body()
                .jsonPath()
                .getUUID("id");
    }

    private UUID criarItem(String nome, String descricao, BigDecimal preco, String fotoPath, UUID restauranteId) {
        return given()
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .body(new ItemCardapioRequest(nome, descricao, preco, fotoPath, restauranteId))
        .when()
                .post(ENDPOINT_ITENS)
        .then()
                .statusCode(201)
                .extract()
                .body()
                .jsonPath()
                .getUUID("id");
    }
}

