package com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.TipoUsuarioGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListarTiposUsuarioUseCaseTest {

    @Mock
    private TipoUsuarioGateway gateway;

    @InjectMocks
    private ListarTiposUsuarioUseCase useCase;

    @Test
    @DisplayName("Deve retornar uma lista de Tipos de Usuário")
    void deveRetornarListaDeTipos() {
        List<TipoUsuario> lista = List.of(
                new TipoUsuario(UUID.randomUUID(), "Dono"),
                new TipoUsuario(UUID.randomUUID(), "Cliente")
        );

        when(gateway.listarTodos()).thenReturn(lista);

        List<TipoUsuario> resultado = useCase.listarTodos();

        assertEquals(2, resultado.size());
        verify(gateway, times(1)).listarTodos();
    }
}