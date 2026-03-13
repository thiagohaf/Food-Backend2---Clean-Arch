package com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.dto.CriarTipoUsuarioInput;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.TipoUsuarioExistenteException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.TipoUsuarioGateway;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.mapper.TipoUsuarioCoreMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarTipoUsuarioUseCaseTest {
    @Mock
    private TipoUsuarioGateway gateway;

    @Mock
    private TipoUsuarioCoreMapper mapper;

    @InjectMocks
    private CriarTipoUsuarioUseCase useCase;

    @Test
    @DisplayName("Deve criar e salvar Tipo de Usuário com sucesso")
    void deveCriarESalvarComSucesso() {
        CriarTipoUsuarioInput input = new CriarTipoUsuarioInput(null, "Cliente");
        TipoUsuario tipoMapeado = new TipoUsuario(null, "Cliente");
        TipoUsuario tipoSalvo = new TipoUsuario(UUID.randomUUID(), "Cliente");

        when(mapper.toDomain(input)).thenReturn(tipoMapeado);
        when(gateway.existePorNome("Cliente")).thenReturn(false);
        when(gateway.salvar(tipoMapeado)).thenReturn(tipoSalvo);

        TipoUsuario resultado = useCase.executar(input);

        assertNotNull(resultado.getId());
        assertEquals("Cliente", resultado.getNome());
        verify(gateway, times(1)).salvar(tipoMapeado);
    }

    @Test
    @DisplayName("Deve lançar exceção quando Tipo de Usuário já existir")
    void deveLancarExcecaoQuandoTipoJaExistir() {
        CriarTipoUsuarioInput input = new CriarTipoUsuarioInput(null, "Cliente");
        TipoUsuario tipoMapeado = new TipoUsuario(null, "Cliente");

        when(mapper.toDomain(input)).thenReturn(tipoMapeado);
        when(gateway.existePorNome("Cliente")).thenReturn(true);

        assertThrows(TipoUsuarioExistenteException.class, () -> useCase.executar(input));
        verify(gateway, never()).salvar(any());
    }
}
