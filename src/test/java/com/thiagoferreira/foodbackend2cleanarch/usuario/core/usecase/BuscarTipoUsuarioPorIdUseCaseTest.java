package com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.TipoUsuarioGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarTipoUsuarioPorIdUseCaseTest {

    @Mock
    private TipoUsuarioGateway gateway;

    @InjectMocks
    private BuscarTipoUsuarioPorIdUseCase useCase;

    @Test
    @DisplayName("Deve retornar o TipoUsuario quando o ID for encontrado")
    void deveRetornarTipoQuandoEncontrado() {
        UUID id = UUID.randomUUID();
        TipoUsuario tipo = new TipoUsuario(id, "Dono");

        when(gateway.buscarPorId(id)).thenReturn(Optional.of(tipo));

        TipoUsuario resultado = useCase.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(gateway, times(1)).buscarPorId(id);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o TipoUsuario não for encontrado")
    void deveLancarExcecaoQuandoNaoEncontrado() {
        UUID id = UUID.randomUUID();
        when(gateway.buscarPorId(id)).thenReturn(Optional.empty());

        assertThrows(TipoUsuarioNaoEncontradoException.class, () -> useCase.buscarPorId(id));
        verify(gateway, times(1)).buscarPorId(id);
    }
}
