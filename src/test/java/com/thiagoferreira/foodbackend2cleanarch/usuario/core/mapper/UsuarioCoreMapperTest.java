package com.thiagoferreira.foodbackend2cleanarch.usuario.core.mapper;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Usuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.dto.CriarUsuarioInput;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.TipoUsuarioCoreMapperException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.UsuarioCoreMapperException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioCoreMapperTest {

    private final UsuarioCoreMapper mapper = new UsuarioCoreMapper();

    @Test
    @DisplayName("Deve mapear CriarUsuarioInput para Usuario com sucesso")
    void deveMapearComSucesso() {
        UUID tipoId = UUID.randomUUID();
        UUID usuarioId = UUID.randomUUID();

        TipoUsuario tipo = new TipoUsuario(tipoId, "Cliente");
        CriarUsuarioInput input = new CriarUsuarioInput(usuarioId, "João", tipoId);

        Usuario usuario = mapper.toDomain(input, tipo);

        assertNotNull(usuario);
        assertEquals(usuarioId, usuario.getId());
        assertEquals("João", usuario.getNome());
        assertEquals(tipo, usuario.getTipoUsuario());
    }

    @Test
    @DisplayName("Deve lançar exceção quando input for nulo")
    void deveLancarExcecaoQuandoInputNulo() {
        TipoUsuario tipo = new TipoUsuario(UUID.randomUUID(), "Cliente");
        assertThrows(UsuarioCoreMapperException.class, () -> mapper.toDomain(null, tipo));
    }

    @Test
    @DisplayName("Deve lançar exceção quando TipoUsuario for nulo")
    void deveLancarExcecaoQuandoTipoNulo() {
        CriarUsuarioInput input = new CriarUsuarioInput(UUID.randomUUID(), "João", UUID.randomUUID());
        assertThrows(TipoUsuarioCoreMapperException.class, () -> mapper.toDomain(input, null));
    }
}
