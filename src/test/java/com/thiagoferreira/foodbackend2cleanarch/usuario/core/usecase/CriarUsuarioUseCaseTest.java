package com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Usuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.dto.CriarUsuarioInput;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.TipoUsuarioGateway;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.UsuarioGateway;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.mapper.UsuarioCoreMapper;
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
class CriarUsuarioUseCaseTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @Mock
    private UsuarioCoreMapper mapper;

    @InjectMocks
    private CriarUsuarioUseCase useCase;

    @Test
    @DisplayName("Deve criar e associar Usuario ao TipoUsuario com sucesso")
    void deveCriarESalvarComSucesso() {
        UUID tipoId = UUID.randomUUID();
        TipoUsuario tipo = new TipoUsuario(tipoId, "Dono");

        CriarUsuarioInput input = new CriarUsuarioInput(null, "Maria", tipoId);
        Usuario usuarioMapeado = new Usuario(null, "Maria", tipo);
        Usuario usuarioSalvo = new Usuario(UUID.randomUUID(), "Maria", tipo);

        when(tipoUsuarioGateway.buscarPorId(tipoId)).thenReturn(Optional.of(tipo));
        when(mapper.toDomain(input, tipo)).thenReturn(usuarioMapeado);
        when(usuarioGateway.salvar(usuarioMapeado)).thenReturn(usuarioSalvo);

        Usuario resultado = useCase.executar(input);

        assertNotNull(resultado.getId());
        assertEquals("Maria", resultado.getNome());
        verify(usuarioGateway, times(1)).salvar(usuarioMapeado);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o TipoUsuario informado não for encontrado")
    void deveLancarExcecaoQuandoTipoNaoEncontrado() {
        UUID tipoId = UUID.randomUUID();
        CriarUsuarioInput input = new CriarUsuarioInput(null, "Maria", tipoId);

        when(tipoUsuarioGateway.buscarPorId(tipoId)).thenReturn(Optional.empty());

        assertThrows(TipoUsuarioNaoEncontradoException.class, () -> useCase.executar(input));

        verify(mapper, never()).toDomain(any(), any());
        verify(usuarioGateway, never()).salvar(any());
    }
}
