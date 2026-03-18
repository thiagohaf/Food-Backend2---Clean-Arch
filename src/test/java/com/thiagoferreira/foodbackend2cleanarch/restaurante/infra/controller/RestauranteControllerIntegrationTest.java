package com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.controller;

import com.thiagoferreira.foodbackend2cleanarch.util.config.AbstractIntegrationTest;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.dto.AtualizarRestauranteInput;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.dto.CriarRestauranteRequest;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.dto.TipoUsuarioRequest;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.dto.UsuarioRequest;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.dto.TipoUsuarioResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

class RestauranteControllerIntegrationTest extends AbstractIntegrationTest {

    private static final String ENDPOINT_RESTAURANTES = "/api/v1/restaurantes";
    private static final String ENDPOINT_USUARIOS = "/api/v1/usuarios";
    private static final String ENDPOINT_TIPOS_USUARIO = "/api/v1/tipos-usuario";
    private static final String TIPO_DONO_RESTAURANTE = "DONO_RESTAURANTE";

    @Test
    @DisplayName("POST /api/v1/restaurantes deve retornar 201 Created e JSON do restaurante")
    void deveCriarRestaurante() {
        UUID tipoDonoId = obterOuCriarTipoUsuario(TIPO_DONO_RESTAURANTE);
        UUID donoId = criarUsuario("Dono Restaurante", tipoDonoId);

        String nomeRestaurante = "Restaurante A-" + UUID.randomUUID();
        String enderecoRestaurante = "Rua 1, 100-" + UUID.randomUUID();
        String tipoCozinha = "Italiana";
        String horario = "10:00-22:00";

        given()
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .body(new CriarRestauranteRequest(
                        nomeRestaurante,
                        enderecoRestaurante,
                        tipoCozinha,
                        horario,
                        donoId
                ))
        .when()
                .post(ENDPOINT_RESTAURANTES)
        .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("nome", equalTo(nomeRestaurante))
                .body("endereco", equalTo(enderecoRestaurante))
                .body("tipoCozinha", equalTo(tipoCozinha))
                .body("horarioFuncionamento", equalTo(horario))
                .body("donoId", equalTo(donoId.toString()));
    }

    @Test
    @DisplayName("GET /api/v1/restaurantes/{id} deve retornar 200 OK e JSON do restaurante")
    void deveBuscarRestaurantePorId() {
        UUID tipoDonoId = obterOuCriarTipoUsuario(TIPO_DONO_RESTAURANTE);
        UUID donoId = criarUsuario("Dono B", tipoDonoId);

        String nomeRestaurante = "Restaurante B-" + UUID.randomUUID();
        String enderecoRestaurante = "Av. Central, 200-" + UUID.randomUUID();
        String tipoCozinha = "Brasileira";
        String horario = "11:00-23:00";
        UUID restauranteId = criarRestaurante(
                nomeRestaurante,
                enderecoRestaurante,
                tipoCozinha,
                horario,
                donoId
        );

        given()
                .accept(APPLICATION_JSON_VALUE)
        .when()
                .get(ENDPOINT_RESTAURANTES + "/{id}", restauranteId)
        .then()
                .statusCode(200)
                .body("id", equalTo(restauranteId.toString()))
                .body("nome", equalTo(nomeRestaurante))
                .body("endereco", equalTo(enderecoRestaurante))
                .body("tipoCozinha", equalTo(tipoCozinha))
                .body("horarioFuncionamento", equalTo(horario))
                .body("donoId", equalTo(donoId.toString()));
    }

    @Test
    @DisplayName("GET /api/v1/restaurantes deve retornar 200 OK e lista (com ao menos 1 restaurante)")
    void deveListarRestaurantes() {
        UUID tipoDonoId = obterOuCriarTipoUsuario(TIPO_DONO_RESTAURANTE);
        UUID donoId = criarUsuario("Dono Lista", tipoDonoId);
        criarRestaurante(
                "Restaurante Lista-" + UUID.randomUUID(),
                "Rua Lista, 10-" + UUID.randomUUID(),
                "Japonesa",
                "09:00-18:00",
                donoId
        );

        given()
                .accept(APPLICATION_JSON_VALUE)
        .when()
                .get(ENDPOINT_RESTAURANTES)
        .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    @DisplayName("PUT /api/v1/restaurantes/{id} deve retornar 200 OK e JSON atualizado")
    void deveAtualizarRestaurante() {
        UUID tipoDonoId = obterOuCriarTipoUsuario(TIPO_DONO_RESTAURANTE);
        UUID donoId = criarUsuario("Dono Atualizar", tipoDonoId);

        String nomeAntigo = "Restaurante Antigo-" + UUID.randomUUID();
        String enderecoAntigo = "Rua Velha, 1-" + UUID.randomUUID();
        String tipoCozinhaAntiga = "Mexicana";
        String horarioAntigo = "12:00-20:00";

        UUID restauranteId = criarRestaurante(
                nomeAntigo,
                enderecoAntigo,
                tipoCozinhaAntiga,
                horarioAntigo,
                donoId
        );

        String nomeNovo = "Restaurante Novo-" + UUID.randomUUID();
        String enderecoNovo = "Rua Nova, 2-" + UUID.randomUUID();
        String tipoCozinhaNovo = "Francesa";
        String horarioNovo = "08:00-16:00";

        given()
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .body(new AtualizarRestauranteInput(
                        nomeNovo,
                        enderecoNovo,
                        tipoCozinhaNovo,
                        horarioNovo
                ))
        .when()
                .put(ENDPOINT_RESTAURANTES + "/{id}", restauranteId)
        .then()
                .statusCode(200)
                .body("id", equalTo(restauranteId.toString()))
                .body("nome", equalTo(nomeNovo))
                .body("endereco", equalTo(enderecoNovo))
                .body("tipoCozinha", equalTo(tipoCozinhaNovo))
                .body("horarioFuncionamento", equalTo(horarioNovo))
                .body("donoId", equalTo(donoId.toString()));
    }

    @Test
    @DisplayName("DELETE /api/v1/restaurantes/{id} deve retornar 204 No Content")
    void deveExcluirRestaurante() {
        UUID tipoDonoId = obterOuCriarTipoUsuario(TIPO_DONO_RESTAURANTE);
        UUID donoId = criarUsuario("Dono Excluir", tipoDonoId);

        String nomeRestaurante = "Restaurante Excluir-" + UUID.randomUUID();
        String enderecoRestaurante = "Rua X, 999-" + UUID.randomUUID();
        String tipoCozinha = "Árabe";
        String horario = "10:00-20:00";

        UUID restauranteId = criarRestaurante(
                nomeRestaurante,
                enderecoRestaurante,
                tipoCozinha,
                horario,
                donoId
        );

        given()
        .when()
                .delete(ENDPOINT_RESTAURANTES + "/{id}", restauranteId)
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

        // Se já existir (nome UNIQUE), reutiliza o registro existente
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

    private UUID criarRestaurante(String nome, String endereco, String tipoCozinha, String horarioFuncionamento, UUID donoId) {
        return given()
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .body(new CriarRestauranteRequest(
                        nome,
                        endereco,
                        tipoCozinha,
                        horarioFuncionamento,
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
}

