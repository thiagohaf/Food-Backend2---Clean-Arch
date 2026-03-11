package com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Usuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.dto.AtualizarUsuarioInput;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.TipoUsuarioGateway;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AtualizarUsuarioUseCaseTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @InjectMocks
    private AtualizarUsuarioUseCase useCase;

    @Test
    @DisplayName("Deve atualizar o Usuario e sua associação de Tipo com sucesso")
    void deveAtualizarComSucesso() {
        UUID usuarioId = UUID.randomUUID();
        UUID novoTipoId = UUID.randomUUID();

        TipoUsuario tipoAntigo = new TipoUsuario(UUID.randomUUID(), "Cliente");
        TipoUsuario tipoNovo = new TipoUsuario(novoTipoId, "Dono");
        Usuario usuarioExistente = new Usuario(usuarioId, "João", tipoAntigo);

        AtualizarUsuarioInput input = new AtualizarUsuarioInput("Carlos", novoTipoId);

        when(usuarioGateway.buscarPorId(usuarioId)).thenReturn(Optional.of(usuarioExistente));
        when(tipoUsuarioGateway.buscarPorId(novoTipoId)).thenReturn(Optional.of(tipoNovo));
        when(usuarioGateway.salvar(any(Usuario.class))).thenAnswer(i -> i.getArguments()[0]);

        Usuario resultado = useCase.executar(usuarioId, input);

        assertEquals("Carlos", resultado.getNome());
        assertEquals("Dono", resultado.getTipoUsuario().getNome());
        verify(usuarioGateway, times(1)).salvar(usuarioExistente);
    }

    @Test
    @DisplayName("Deve lançar exceção se o novo Tipo de Usuário não for encontrado")
    void deveLancarExcecaoSeTipoNaoEncontrado() {
        UUID usuarioId = UUID.randomUUID();
        UUID novoTipoId = UUID.randomUUID();

        Usuario usuarioExistente = new Usuario(usuarioId, "João", new TipoUsuario(UUID.randomUUID(), "Cliente"));
        AtualizarUsuarioInput input = new AtualizarUsuarioInput("Carlos", novoTipoId);

        when(usuarioGateway.buscarPorId(usuarioId)).thenReturn(Optional.of(usuarioExistente));
        when(tipoUsuarioGateway.buscarPorId(novoTipoId)).thenReturn(Optional.empty()); // Tipo não existe no BD

        assertThrows(TipoUsuarioNaoEncontradoException.class, () -> useCase.executar(usuarioId, input));
        verify(usuarioGateway, never()).salvar(any());
    }
}
