package com.thiagoferreira.foodbackend2cleanarch.usuario.infra.gateway;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Usuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.entity.TipoUsuarioEntity;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.entity.UsuarioEntity;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.mapper.UsuarioMapper;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.repository.TipoUsuarioRepository;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.repository.UsuarioRepository;
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
class UsuarioGatewayImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private com.thiagoferreira.foodbackend2cleanarch.usuario.infra.gateway.UsuarioGatewayImpl usuarioGateway;

    @Test
    @DisplayName("Deve salvar Usuario com sucesso")
    void deveSalvarUsuarioComSucesso() {
        // Arrange
        UUID usuarioId = UUID.randomUUID();
        UUID tipoId = UUID.randomUUID();
        TipoUsuario tipo = new TipoUsuario(tipoId, "Cliente");
        Usuario dominio = new Usuario(usuarioId, "João", tipo);

        TipoUsuarioEntity tipoEntity = new TipoUsuarioEntity(tipoId, "Cliente");
        UsuarioEntity entity = new UsuarioEntity(usuarioId, "João", tipoEntity);
        UsuarioEntity entitySalva = new UsuarioEntity(usuarioId, "João", tipoEntity);

        Usuario dominioRetorno = new Usuario(usuarioId, "João", tipo);

        when(usuarioMapper.toEntity(dominio)).thenReturn(entity);
        when(tipoUsuarioRepository.getReferenceById(tipoId)).thenReturn(tipoEntity);
        when(usuarioRepository.save(entity)).thenReturn(entitySalva);
        when(usuarioMapper.toDomain(entitySalva)).thenReturn(dominioRetorno);

        // Act
        Usuario resultado = usuarioGateway.salvar(dominio);

        // Assert
        assertNotNull(resultado);
        assertEquals(usuarioId, resultado.getId());
        assertEquals("João", resultado.getNome());
        assertNotNull(resultado.getTipoUsuario());
        assertEquals(tipoId, resultado.getTipoUsuario().getId());
        verify(usuarioMapper).toEntity(dominio);
        verify(tipoUsuarioRepository).getReferenceById(tipoId);
        verify(usuarioRepository).save(entity);
        verify(usuarioMapper).toDomain(entitySalva);
    }

    @Test
    @DisplayName("Deve buscar Usuario por ID usando findByIdWithTipoUsuario (não findById)")
    void deveBuscarUsuarioPorIdChamandoFindByIdWithTipoUsuario() {
        // Arrange
        UUID id = UUID.randomUUID();
        UUID tipoId = UUID.randomUUID();
        TipoUsuarioEntity tipoEntity = new TipoUsuarioEntity(tipoId, "Cliente");
        UsuarioEntity entity = new UsuarioEntity(id, "Maria", tipoEntity);

        TipoUsuario tipo = new TipoUsuario(tipoId, "Cliente");
        Usuario dominio = new Usuario(id, "Maria", tipo);

        when(usuarioRepository.findByIdWithTipoUsuario(id)).thenReturn(Optional.of(entity));
        when(usuarioMapper.toDomain(entity)).thenReturn(dominio);

        // Act
        Optional<Usuario> resultado = usuarioGateway.buscarPorId(id);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getId());
        assertEquals("Maria", resultado.get().getNome());
        assertEquals(tipoId, resultado.get().getTipoUsuario().getId());
        verify(usuarioRepository).findByIdWithTipoUsuario(id);
        verify(usuarioRepository, never()).findById(any(UUID.class));
        verify(usuarioMapper).toDomain(entity);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando buscar por ID inexistente")
    void deveRetornarOptionalVazioQuandoBuscarPorIdInexistente() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(usuarioRepository.findByIdWithTipoUsuario(id)).thenReturn(Optional.empty());

        // Act
        Optional<Usuario> resultado = usuarioGateway.buscarPorId(id);

        // Assert
        assertTrue(resultado.isEmpty());
        verify(usuarioRepository).findByIdWithTipoUsuario(id);
        verify(usuarioRepository, never()).findById(any(UUID.class));
        verify(usuarioMapper, never()).toDomain(any());
    }

    @Test
    @DisplayName("Deve listar todos os usuários")
    void deveListarTodosOsUsuarios() {
        // Arrange
        UUID id = UUID.randomUUID();
        UUID tipoId = UUID.randomUUID();
        TipoUsuarioEntity tipoEntity = new TipoUsuarioEntity(tipoId, "Cliente");
        UsuarioEntity entity = new UsuarioEntity(id, "João", tipoEntity);

        TipoUsuario tipo = new TipoUsuario(tipoId, "Cliente");
        Usuario dominio = new Usuario(id, "João", tipo);

        when(usuarioRepository.findAll()).thenReturn(List.of(entity));
        when(usuarioMapper.toDomain(entity)).thenReturn(dominio);

        // Act
        List<Usuario> resultado = usuarioGateway.listarTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(id, resultado.get(0).getId());
        assertEquals("João", resultado.get(0).getNome());
        verify(usuarioRepository).findAll();
    }

    @Test
    @DisplayName("Deve excluir Usuario por ID")
    void deveExcluirUsuarioPorId() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Act
        usuarioGateway.excluir(id);

        // Assert
        verify(usuarioRepository).deleteById(id);
    }

    @Test
    @DisplayName("Deve propagar DataAccessException quando repositório falhar ao salvar usuário")
    void devePropagarExcecaoQuandoRepositorioLancarErroAoSalvar() {
        // Arrange
        UUID usuarioId = UUID.randomUUID();
        UUID tipoId = UUID.randomUUID();
        TipoUsuario tipo = new TipoUsuario(tipoId, "Cliente");
        Usuario dominio = new Usuario(usuarioId, "João", tipo);

        TipoUsuarioEntity tipoEntity = new TipoUsuarioEntity(tipoId, "Cliente");
        UsuarioEntity entity = new UsuarioEntity(usuarioId, "João", tipoEntity);

        when(usuarioMapper.toEntity(dominio)).thenReturn(entity);
        when(tipoUsuarioRepository.getReferenceById(tipoId)).thenReturn(tipoEntity);
        when(usuarioRepository.save(entity))
                .thenThrow(new org.springframework.dao.DataIntegrityViolationException("erro de banco"));

        // Act & Assert
        assertThrows(org.springframework.dao.DataAccessException.class, () -> usuarioGateway.salvar(dominio));

        verify(usuarioMapper).toEntity(dominio);
        verify(tipoUsuarioRepository).getReferenceById(tipoId);
        verify(usuarioRepository).save(entity);
    }
}
