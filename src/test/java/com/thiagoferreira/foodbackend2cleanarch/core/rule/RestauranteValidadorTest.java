package com.thiagoferreira.foodbackend2cleanarch.core.rule;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Restaurante;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.ValidacaoRegraNegocioException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RestauranteValidadorTest {

    @Test
    @DisplayName("Deve lançar exceção quando o nome do restaurante for nulo")
    void deveLancarExcecaoQuandoNomeForNulo() {
        var exception = assertThrows(ValidacaoRegraNegocioException.class, () ->
                new Restaurante(UUID.randomUUID(),
                        null,
                        "Endereço",
                        "Japonesa",
                        "09:00-22:00",
                        UUID.randomUUID())
        );

        assertTrue(exception.getMessage().contains("O campo Nome do Restaurante não pode ser nulo ou vazio."));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o dono não for informado")
    void deveLancarExcecaoQuandoDonoForNulo() {
        var exception = assertThrows(ValidacaoRegraNegocioException.class, () ->
                new Restaurante(UUID.randomUUID(),
                        "Restaurante Top",
                        "Endereço",
                        "Italiana",
                        "11:00-23:00",
                        null)
        );

        assertEquals("O restaurante deve ter um dono (usuário) associado.", exception.getMessage());
    }
}
