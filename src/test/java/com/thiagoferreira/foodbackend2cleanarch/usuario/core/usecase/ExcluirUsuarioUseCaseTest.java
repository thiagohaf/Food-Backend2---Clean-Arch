package com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Usuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.UsuarioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.UsuarioGateway;
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
public class ExcluirUsuarioUseCaseTest {

    @Mock
    private UsuarioGateway gateway;

    @InjectMocks
    private ExcluirUsuarioUseCase useCase;

    @Test
    @DisplayName("Deve excluir o Usuario com sucesso quando existir")
    void deveExcluirComSucesso() {
        UUID id = UUID.randomUUID();
        TipoUsuario tipo = new TipoUsuario(UUID.randomUUID(), "Cliente");
        Usuario usuarioExistente = new Usuario(id, "Maria", tipo);

        when(gateway.buscarPorId(id)).thenReturn(Optional.of(usuarioExistente));

        useCase.excluir(id);

        verify(gateway, times(1)).excluir(id);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar excluir Usuario inexistente")
    void deveLancarExcecaoAoExcluirUsuarioInexistente() {
        UUID id = UUID.randomUUID();
        when(gateway.buscarPorId(id)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> useCase.excluir(id));
        verify(gateway, never()).excluir(any());
    }
}
