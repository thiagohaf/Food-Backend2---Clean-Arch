package com.thiagoferreira.foodbackend2cleanarch.usuario.core.mapper;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.dto.CriarTipoUsuarioInput;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.TipoUsuarioCoreMapperException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TipoUsuarioCoreMapperTest {
    private final TipoUsuarioCoreMapper mapper = new TipoUsuarioCoreMapper();

    @Test
    @DisplayName("Deve mapear CriarTipoUsuarioInput para TipoUsuario com sucesso")
    void deveMapearComSucesso() {
        UUID id = UUID.randomUUID();
        CriarTipoUsuarioInput input = new CriarTipoUsuarioInput(
                id,
                "Dono de Restaurante");

        TipoUsuario tipoUsuario = mapper.toDomain(input);

        assertNotNull(tipoUsuario);
        assertEquals(id, tipoUsuario.getId());
        assertEquals("Dono de Restaurante", tipoUsuario.getNome());
    }

    @Test
    @DisplayName("Deve lançar exceção quando input for nulo")
    void deveLancarExcecaoQuandoInputNulo() {
        assertThrows(TipoUsuarioCoreMapperException.class, () -> mapper.toDomain(null));
    }
}
