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
public class ExcluirTipoUsuarioUseCaseTest {

    @Mock
    private TipoUsuarioGateway gateway;

    @InjectMocks
    private ExcluirTipoUsuarioUseCase useCase;

    @Test
    @DisplayName("Deve excluir o TipoUsuario com sucesso quando existir")
    void deveExcluirComSucesso() {
        UUID id = UUID.randomUUID();
        TipoUsuario tipoExistente = new TipoUsuario(id, "Dono");

        when(gateway.buscarPorId(id)).thenReturn(Optional.of(tipoExistente));

        useCase.excluir(id);

        verify(gateway, times(1)).excluir(id);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar excluir TipoUsuario inexistente")
    void deveLancarExcecaoAoExcluirTipoInexistente() {
        UUID id = UUID.randomUUID();
        when(gateway.buscarPorId(id)).thenReturn(Optional.empty());

        assertThrows(TipoUsuarioNaoEncontradoException.class, () -> useCase.excluir(id));
        verify(gateway, never()).excluir(any());
    }
}