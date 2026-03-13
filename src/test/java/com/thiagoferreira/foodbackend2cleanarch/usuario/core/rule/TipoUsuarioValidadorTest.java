package com.thiagoferreira.foodbackend2cleanarch.usuario.core.rule;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.util.exception.ValidacaoRegraNegocioException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TipoUsuarioValidadorTest {
    @Test
    @DisplayName("Deve instanciar TipoUsuario com sucesso quando os dados forem válidos")
    void deveCriarTipoUsuarioValido() {
        TipoUsuario tipoUsuario = new TipoUsuario(
                UUID.randomUUID(),
                "Dono de Restaurante");

        assertNotNull(tipoUsuario);
        assertEquals("Dono de Restaurante", tipoUsuario.getNome());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome do TipoUsuario for nulo")
    void deveLancarExcecaoQuandoNomeNulo() {
        UUID idAleatorio = UUID.randomUUID();
        String nomeNulo = null;

        ValidacaoRegraNegocioException exception = assertThrows(
                ValidacaoRegraNegocioException.class,
                () -> new TipoUsuario(idAleatorio, nomeNulo)
        );

        assertEquals("O campo Nome do Tipo não pode ser nulo ou vazio.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome do TipoUsuario for vazio ou espaços")
    void deveLancarExcecaoQuandoNomeVazio() {
        UUID idAleatorio = UUID.randomUUID();
        String nomeComEspacos = "   ";

        ValidacaoRegraNegocioException exception = assertThrows(
                ValidacaoRegraNegocioException.class,
                () -> new TipoUsuario(idAleatorio, nomeComEspacos)
        );

        assertEquals("O campo Nome do Tipo não pode ser nulo ou vazio.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a entidade TipoUsuario recebida for nula")
    void deveLancarExcecaoQuandoTipoUsuarioForNulo() {
        ValidacaoRegraNegocioException exception = assertThrows(
                ValidacaoRegraNegocioException.class,
                () -> TipoUsuarioValidador.validar(null)
        );
        assertEquals("O tipo de usuário não pode ser nulo.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar os dados do TipoUsuario com sucesso")
    void deveAtualizarDadosComSucesso() {
        TipoUsuario tipo = new TipoUsuario(UUID.randomUUID(), "Antigo");
        tipo.atualizar("Novo");
        assertEquals("Novo", tipo.getNome());
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar TipoUsuario com nome inválido")
    void deveLancarExcecaoAoAtualizarTipoComNomeInvalido() {
        TipoUsuario tipo = new TipoUsuario(UUID.randomUUID(), "Valido");
        assertThrows(ValidacaoRegraNegocioException.class, () -> tipo.atualizar("  "));
    }
}
