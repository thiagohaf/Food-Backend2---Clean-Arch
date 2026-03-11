package com.thiagoferreira.foodbackend2cleanarch.usuario.core.rule;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Usuario;
import com.thiagoferreira.foodbackend2cleanarch.util.exception.ValidacaoRegraNegocioException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioValidadorTest {

    @Test
    @DisplayName("Deve instanciar Usuario com sucesso quando os dados forem válidos")
    void deveCriarUsuarioValido() {
        TipoUsuario tipo = new TipoUsuario(UUID.randomUUID(), "Cliente");

        Usuario usuario = new Usuario(UUID.randomUUID(), "Ana Silva", tipo);

        assertNotNull(usuario);
        assertEquals("Ana Silva", usuario.getNome());
        assertEquals("Cliente", usuario.getTipoUsuario().getNome());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome do Usuario for nulo")
    void deveLancarExcecaoQuandoNomeNulo() {
        TipoUsuario tipo = new TipoUsuario(UUID.randomUUID(), "Cliente");

        ValidacaoRegraNegocioException exception = assertThrows(
                ValidacaoRegraNegocioException.class,
                () -> new Usuario(UUID.randomUUID(), null, tipo)
        );
        assertEquals("O campo Nome do Usuário não pode ser nulo ou vazio.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o Usuario não tiver um Tipo de Usuário associado")
    void deveLancarExcecaoQuandoTipoUsuarioNulo() {
        ValidacaoRegraNegocioException exception = assertThrows(
                ValidacaoRegraNegocioException.class,
                () -> new Usuario(UUID.randomUUID(), "Ana Silva", null)
        );
        assertEquals("O usuário deve obrigatoriamente estar associado a um Tipo de Usuário.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a entidade Usuario recebida for nula")
    void deveLancarExcecaoQuandoUsuarioForNulo() {
        ValidacaoRegraNegocioException exception = assertThrows(
                ValidacaoRegraNegocioException.class,
                () -> UsuarioValidador.validar(null)
        );
        assertEquals("O usuário não pode ser nulo.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar os dados do Usuario com sucesso")
    void deveAtualizarDadosComSucesso() {
        TipoUsuario tipoAntigo = new TipoUsuario(UUID.randomUUID(), "Cliente");
        TipoUsuario tipoNovo = new TipoUsuario(UUID.randomUUID(), "Dono");
        Usuario usuario = new Usuario(UUID.randomUUID(), "João", tipoAntigo);

        usuario.atualizar("Carlos", tipoNovo);

        assertEquals("Carlos", usuario.getNome());
        assertEquals("Dono", usuario.getTipoUsuario().getNome());
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar Usuario sem TipoUsuario associado")
    void deveLancarExcecaoAoAtualizarSemTipo() {
        TipoUsuario tipo = new TipoUsuario(UUID.randomUUID(), "Cliente");
        Usuario usuario = new Usuario(UUID.randomUUID(), "João", tipo);

        assertThrows(ValidacaoRegraNegocioException.class, () -> usuario.atualizar("Carlos", null));
    }
}
