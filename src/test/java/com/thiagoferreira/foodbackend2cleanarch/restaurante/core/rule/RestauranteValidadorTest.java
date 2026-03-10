package com.thiagoferreira.foodbackend2cleanarch.restaurante.core.rule;

import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain.Restaurante;
import com.thiagoferreira.foodbackend2cleanarch.util.exception.ValidacaoRegraNegocioException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RestauranteValidadorTest {

    private final UUID ID_VALIDO = UUID.randomUUID();
    private final UUID DONO_ID_VALIDO = UUID.randomUUID();
    private final String NOME_VALIDO = "Restaurante Top";
    private final String ENDERECO_VALIDO = "Rua das Flores, 123";
    private final String COZINHA_VALIDA = "Italiana";
    private final String HORARIO_VALIDO = "11:00-23:00";

    @Test
    @DisplayName("Deve criar um restaurante com sucesso quando todos os dados forem válidos")
    void deveCriarRestauranteComSucesso() {
        assertDoesNotThrow(() ->
                new Restaurante(ID_VALIDO, NOME_VALIDO, ENDERECO_VALIDO,
                        COZINHA_VALIDA, HORARIO_VALIDO, DONO_ID_VALIDO)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    @DisplayName("Deve lançar exceção quando o nome do restaurante for vazio ou apenas espaços")
    void deveLancarExcecaoQuandoNomeForVazio(String nomeInvalido) {
        var exception = assertThrows(ValidacaoRegraNegocioException.class, () ->
                new Restaurante(ID_VALIDO, nomeInvalido, ENDERECO_VALIDO,
                        COZINHA_VALIDA, HORARIO_VALIDO, DONO_ID_VALIDO)
        );

        assertTrue(exception.getMessage().contains("O campo Nome do Restaurante não pode ser nulo ou vazio."));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o endereço for nulo")
    void deveLancarExcecaoQuandoEnderecoForNulo() {
        var exception = assertThrows(ValidacaoRegraNegocioException.class, () ->
                new Restaurante(ID_VALIDO, NOME_VALIDO, null,
                        COZINHA_VALIDA, HORARIO_VALIDO, DONO_ID_VALIDO)
        );

        assertTrue(exception.getMessage().contains("O campo Endereco não pode ser nulo ou vazio."));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o tipo de cozinha for nulo")
    void deveLancarExcecaoQuandoCozinhaForNulo() {
        var exception = assertThrows(ValidacaoRegraNegocioException.class, () ->
                new Restaurante(ID_VALIDO, NOME_VALIDO, ENDERECO_VALIDO,
                        null, HORARIO_VALIDO, DONO_ID_VALIDO)
        );

        assertTrue(exception.getMessage().contains("O campo Tipo de Cozinha não pode ser nulo ou vazio."));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o dono não for informado")
    void deveLancarExcecaoQuandoDonoForNulo() {
        var exception = assertThrows(ValidacaoRegraNegocioException.class, () ->
                new Restaurante(ID_VALIDO, NOME_VALIDO, ENDERECO_VALIDO,
                        COZINHA_VALIDA, HORARIO_VALIDO, null)
        );

        assertEquals("O restaurante deve ter um dono (usuário) associado.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o horário de funcionamento for nulo")
    void deveLancarExcecaoQuandoHorarioForNulo() {
        var exception = assertThrows(ValidacaoRegraNegocioException.class, () ->
                new Restaurante(ID_VALIDO, NOME_VALIDO, ENDERECO_VALIDO,
                        COZINHA_VALIDA, null, DONO_ID_VALIDO)
        );
        assertTrue(exception.getMessage().contains("O horário de funcionamento deve estar no formato HH:mm-HH:mm."));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "   ", "\t", "\n"})
    @DisplayName("Deve lançar exceção quando o horário de funcionamento for vazio ou em branco")
    void deveLancarExcecaoQuandoHorarioForVazioOuEmBranco(String horarioEmBranco) {
        var exception = assertThrows(ValidacaoRegraNegocioException.class, () ->
                new Restaurante(ID_VALIDO, NOME_VALIDO, ENDERECO_VALIDO,
                        COZINHA_VALIDA, horarioEmBranco, DONO_ID_VALIDO)
        );
        assertTrue(exception.getMessage().contains("O horário de funcionamento deve estar no formato HH:mm-HH:mm."));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "09:00",           // apenas um horário (falta -HH:mm)
            "23:59",           // apenas um horário
            "24:00-23:00",     // hora inicial inválida (24)
            "23:00-24:00",     // hora final inválida (24)
            "25:00-23:00",     // hora inicial inválida (25)
            "23:60-00:00",     // minuto inicial inválido (60)
            "12:00-18:60",     // minuto final inválido (60)
            "12:99-18:00",     // minuto inicial inválido (99)
            "9:00-18:00",      // hora com um dígito (início)
            "08:00-9:00",      // hora com um dígito (fim)
            "0:00-23:00",      // hora com um dígito (0 em vez de 00)
            "08:0-18:00",      // minuto com um dígito
            "08:00 18:00",     // separador errado (espaço em vez de -)
            "08:00a18:00",     // separador errado (letra)
            "08:00-18:00-19:00", // três horários
            "abc",             // texto sem formato
            "ab:cd-ef:gh",     // texto no formato mas não números
            "9-22",            // sem dois pontos
            "00-23:00",        // primeiro horário sem dois pontos
            "12:30-15",        // segundo horário incompleto
            "-12:00-23:00",    // caractere extra no início
            "12:00-23:00-",    // caractere extra no fim
            " 12:00-23:00",    // espaço no início
            "12:00-23:00 "     // espaço no fim
    })
    @DisplayName("Deve lançar exceção quando o formato do horário for inválido")
    void deveLancarExcecaoQuandoHorarioInvalido(String horarioErrado) {
        var exception = assertThrows(ValidacaoRegraNegocioException.class, () ->
                new Restaurante(ID_VALIDO, NOME_VALIDO, ENDERECO_VALIDO,
                        COZINHA_VALIDA, horarioErrado, DONO_ID_VALIDO)
        );
        assertTrue(exception.getMessage().contains("O horário de funcionamento deve estar no formato HH:mm-HH:mm."));
    }

    @Test
    @DisplayName("Deve atualizar os dados do restaurante com sucesso quando válidos")
    void deveAtualizarDadosComSucesso() {
        Restaurante restaurante = new Restaurante(
                UUID.randomUUID(), "Pizzaria Antiga", "Rua Velha", "Italiana", "18:00-23:00", UUID.randomUUID()
        );

        restaurante.atualizar("Pizzaria Nova", "Rua Nova", "Geral", "10:00-22:00");

        assertEquals("Pizzaria Nova", restaurante.getNome());
        assertEquals("Rua Nova", restaurante.getEndereco());
        assertEquals("Geral", restaurante.getTipoCozinha());
        assertEquals("10:00-22:00", restaurante.getHorarioFuncionamento());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar com dados inválidos")
    void deveLancarExcecaoAoAtualizarComDadosInvalidos() {
        Restaurante restaurante = new Restaurante(
                UUID.randomUUID(), "Pizzaria Antiga", "Rua Velha", "Italiana", "18:00-23:00", UUID.randomUUID()
        );

        assertThrows(ValidacaoRegraNegocioException.class, () ->
                restaurante.atualizar("Pizzaria Nova", "Rua Nova", "Geral", "horario-errado")
        );
    }
}
