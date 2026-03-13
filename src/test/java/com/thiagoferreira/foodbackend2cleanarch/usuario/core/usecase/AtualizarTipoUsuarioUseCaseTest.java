package com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.dto.AtualizarTipoUsuarioInput;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.TipoUsuarioGateway;
import com.thiagoferreira.foodbackend2cleanarch.util.exception.ValidacaoRegraNegocioException;
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
class AtualizarTipoUsuarioUseCaseTest {

    @Mock
    private TipoUsuarioGateway gateway;

    @InjectMocks
    private AtualizarTipoUsuarioUseCase useCase;

    @Test
    @DisplayName("Deve atualizar e salvar o TipoUsuario com sucesso")
    void deveAtualizarESalvarComSucesso() {
        UUID id = UUID.randomUUID();
        AtualizarTipoUsuarioInput input = new AtualizarTipoUsuarioInput("Dono");
        TipoUsuario tipoExistente = new TipoUsuario(id, "Cliente");

        when(gateway.buscarPorId(id)).thenReturn(Optional.of(tipoExistente));
        when(gateway.existePorNome("Dono")).thenReturn(false);
        when(gateway.salvar(any(TipoUsuario.class))).thenAnswer(i -> i.getArguments()[0]);

        TipoUsuario resultado = useCase.executar(id, input);

        assertEquals("Dono", resultado.getNome());
        verify(gateway, times(1)).salvar(tipoExistente);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o novo nome do TipoUsuario já existir no banco")
    void deveLancarExcecaoQuandoNovoNomeJaExistir() {
        UUID id = UUID.randomUUID();
        AtualizarTipoUsuarioInput input = new AtualizarTipoUsuarioInput("Dono");
        TipoUsuario tipoExistente = new TipoUsuario(id, "Cliente");

        when(gateway.buscarPorId(id)).thenReturn(Optional.of(tipoExistente));
        when(gateway.existePorNome("Dono")).thenReturn(true);

        assertThrows(ValidacaoRegraNegocioException.class, () -> useCase.executar(id, input));
        verify(gateway, never()).salvar(any());
    }
}
