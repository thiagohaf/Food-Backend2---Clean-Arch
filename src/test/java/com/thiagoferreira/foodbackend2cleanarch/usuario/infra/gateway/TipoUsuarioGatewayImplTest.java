package com.thiagoferreira.foodbackend2cleanarch.usuario.infra.gateway;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.entity.TipoUsuarioEntity;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.mapper.TipoUsuarioMapper;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.repository.TipoUsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioGatewayImplTest {

    @Mock
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Mock
    private TipoUsuarioMapper tipoUsuarioMapper;

    @InjectMocks
    private com.thiagoferreira.foodbackend2cleanarch.usuario.infra.gateway.TipoUsuarioGatewayImpl tipoUsuarioGateway;

    @Test
    @DisplayName("Deve salvar TipoUsuario com sucesso")
    void deveSalvarTipoUsuarioComSucesso() {
        // Arrange
        UUID id = UUID.randomUUID();
        TipoUsuario dominio = new TipoUsuario(id, "Cliente");
        TipoUsuarioEntity entity = new TipoUsuarioEntity(id, "Cliente");
        TipoUsuarioEntity entitySalva = new TipoUsuarioEntity(id, "Cliente");
        TipoUsuario dominioRetorno = new TipoUsuario(id, "Cliente");

        when(tipoUsuarioMapper.toEntity(dominio)).thenReturn(entity);
        when(tipoUsuarioRepository.save(entity)).thenReturn(entitySalva);
        when(tipoUsuarioMapper.toDomain(entitySalva)).thenReturn(dominioRetorno);

        // Act
        TipoUsuario resultado = tipoUsuarioGateway.salvar(dominio);

        // Assert
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Cliente", resultado.getNome());
        verify(tipoUsuarioMapper).toEntity(dominio);
        verify(tipoUsuarioRepository).save(entity);
        verify(tipoUsuarioMapper).toDomain(entitySalva);
    }

    @Test
    @DisplayName("Deve retornar true quando existir tipo por nome")
    void deveRetornarTrueQuandoExistirPorNome() {
        // Arrange
        when(tipoUsuarioRepository.existsByNome("Admin")).thenReturn(true);

        // Act
        boolean resultado = tipoUsuarioGateway.existePorNome("Admin");

        // Assert
        assertTrue(resultado);
        verify(tipoUsuarioRepository).existsByNome("Admin");
    }

    @Test
    @DisplayName("Deve retornar false quando não existir tipo por nome")
    void deveRetornarFalseQuandoNaoExistirPorNome() {
        // Arrange
        when(tipoUsuarioRepository.existsByNome("Inexistente")).thenReturn(false);

        // Act
        boolean resultado = tipoUsuarioGateway.existePorNome("Inexistente");

        // Assert
        assertFalse(resultado);
        verify(tipoUsuarioRepository).existsByNome("Inexistente");
    }

    @Test
    @DisplayName("Deve buscar TipoUsuario por ID quando existir")
    void deveBuscarTipoUsuarioPorIdQuandoExistir() {
        // Arrange
        UUID id = UUID.randomUUID();
        TipoUsuarioEntity entity = new TipoUsuarioEntity(id, "Cliente");
        TipoUsuario dominio = new TipoUsuario(id, "Cliente");
        when(tipoUsuarioRepository.findById(id)).thenReturn(Optional.of(entity));
        when(tipoUsuarioMapper.toDomain(entity)).thenReturn(dominio);

        // Act
        Optional<TipoUsuario> resultado = tipoUsuarioGateway.buscarPorId(id);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getId());
        assertEquals("Cliente", resultado.get().getNome());
        verify(tipoUsuarioRepository).findById(id);
        verify(tipoUsuarioMapper).toDomain(entity);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando buscar por ID inexistente")
    void deveRetornarOptionalVazioQuandoBuscarPorIdInexistente() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(tipoUsuarioRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<TipoUsuario> resultado = tipoUsuarioGateway.buscarPorId(id);

        // Assert
        assertTrue(resultado.isEmpty());
        verify(tipoUsuarioRepository).findById(id);
        verify(tipoUsuarioMapper, never()).toDomain(any());
    }

    @Test
    @DisplayName("Deve listar todos os tipos de usuário")
    void deveListarTodosOsTiposDeUsuario() {
        // Arrange
        UUID id = UUID.randomUUID();
        TipoUsuarioEntity entity = new TipoUsuarioEntity(id, "Cliente");
        TipoUsuario dominio = new TipoUsuario(id, "Cliente");
        when(tipoUsuarioRepository.findAll()).thenReturn(List.of(entity));
        when(tipoUsuarioMapper.toDomain(entity)).thenReturn(dominio);

        // Act
        List<TipoUsuario> resultado = tipoUsuarioGateway.listarTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(id, resultado.get(0).getId());
        assertEquals("Cliente", resultado.get(0).getNome());
        verify(tipoUsuarioRepository).findAll();
    }

    @Test
    @DisplayName("Deve excluir TipoUsuario por ID")
    void deveExcluirTipoUsuarioPorId() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Act
        tipoUsuarioGateway.excluir(id);

        // Assert
        verify(tipoUsuarioRepository).deleteById(id);
    }
}
