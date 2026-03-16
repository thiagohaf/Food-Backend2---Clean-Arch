package com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.gateway;

import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain.Restaurante;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.entity.RestauranteEntity;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.mapper.RestauranteMapper;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.repository.RestauranteRepository;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Usuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.UsuarioGateway;
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
class RestauranteGatewayImplTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private RestauranteMapper restauranteMapper;

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private RestauranteGatewayImpl restauranteGateway;

    @Test
    @DisplayName("Deve salvar restaurante com sucesso: domínio convertido para entity, salvo e retornado como domínio")
    void deveSalvarRestauranteComSucesso() {
        // Arrange
        UUID id = UUID.randomUUID();
        UUID donoId = UUID.randomUUID();
        Restaurante dominio = new Restaurante(id, "Pizzaria", "Rua A", "Italiana", "18:00-23:00", donoId);
        RestauranteEntity entity = new RestauranteEntity(id, "Pizzaria", "Rua A", "Italiana", "18:00-23:00", donoId);
        RestauranteEntity entitySalva = new RestauranteEntity(id, "Pizzaria", "Rua A", "Italiana", "18:00-23:00", donoId);
        Restaurante dominioRetorno = new Restaurante(id, "Pizzaria", "Rua A", "Italiana", "18:00-23:00", donoId);

        when(restauranteMapper.toEntity(dominio)).thenReturn(entity);
        when(restauranteRepository.save(entity)).thenReturn(entitySalva);
        when(restauranteMapper.toDomain(entitySalva)).thenReturn(dominioRetorno);

        // Act
        Restaurante resultado = restauranteGateway.salvar(dominio);

        // Assert
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Pizzaria", resultado.getNome());
        assertEquals("Rua A", resultado.getEndereco());
        assertEquals("Italiana", resultado.getTipoCozinha());
        assertEquals("18:00-23:00", resultado.getHorarioFuncionamento());
        assertEquals(donoId, resultado.getDonoId());
        verify(restauranteMapper).toEntity(dominio);
        verify(restauranteRepository).save(entity);
        verify(restauranteMapper).toDomain(entitySalva);
    }

    @Test
    @DisplayName("Deve buscar restaurante por ID quando existir")
    void deveBuscarRestaurantePorIdQuandoExistir() {
        // Arrange
        UUID id = UUID.randomUUID();
        UUID donoId = UUID.randomUUID();
        RestauranteEntity entity = new RestauranteEntity(id, "Pizzaria", "Rua A", "Italiana", "18:00-23:00", donoId);
        Restaurante dominio = new Restaurante(id, "Pizzaria", "Rua A", "Italiana", "18:00-23:00", donoId);
        when(restauranteRepository.findById(id)).thenReturn(Optional.of(entity));
        when(restauranteMapper.toDomain(entity)).thenReturn(dominio);

        // Act
        Optional<Restaurante> resultado = restauranteGateway.buscarPorId(id);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getId());
        assertEquals("Pizzaria", resultado.get().getNome());
        verify(restauranteRepository).findById(id);
        verify(restauranteMapper).toDomain(entity);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando buscar por ID inexistente")
    void deveRetornarOptionalVazioQuandoBuscarPorIdInexistente() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(restauranteRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Restaurante> resultado = restauranteGateway.buscarPorId(id);

        // Assert
        assertTrue(resultado.isEmpty());
        verify(restauranteRepository).findById(id);
        verify(restauranteMapper, never()).toDomain(any());
    }

    @Test
    @DisplayName("Deve listar todos os restaurantes")
    void deveListarTodosOsRestaurantes() {
        // Arrange
        UUID id = UUID.randomUUID();
        UUID donoId = UUID.randomUUID();
        RestauranteEntity entity = new RestauranteEntity(id, "Pizzaria", "Rua A", "Italiana", "18:00-23:00", donoId);
        Restaurante dominio = new Restaurante(id, "Pizzaria", "Rua A", "Italiana", "18:00-23:00", donoId);
        when(restauranteRepository.findAll()).thenReturn(List.of(entity));
        when(restauranteMapper.toDomain(entity)).thenReturn(dominio);

        // Act
        List<Restaurante> resultado = restauranteGateway.listarTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(id, resultado.get(0).getId());
        assertEquals("Pizzaria", resultado.get(0).getNome());
        verify(restauranteRepository).findAll();
    }

    @Test
    @DisplayName("Deve excluir restaurante por ID")
    void deveExcluirRestaurantePorId() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Act
        restauranteGateway.excluir(id);

        // Assert
        verify(restauranteRepository).deleteById(id);
    }

    @Test
    @DisplayName("Deve retornar true quando existir restaurante por nome e endereço")
    void deveRetornarTrueQuandoExistirPorNomeEEndereco() {
        // Arrange
        when(restauranteRepository.existsByNomeAndEndereco("Pizzaria", "Rua A")).thenReturn(true);

        // Act
        boolean resultado = restauranteGateway.existePorNomeEEndereco("Pizzaria", "Rua A");

        // Assert
        assertTrue(resultado);
        verify(restauranteRepository).existsByNomeAndEndereco("Pizzaria", "Rua A");
    }

    @Test
    @DisplayName("Deve retornar false quando não existir restaurante por nome e endereço")
    void deveRetornarFalseQuandoNaoExistirPorNomeEEndereco() {
        // Arrange
        when(restauranteRepository.existsByNomeAndEndereco("Pizzaria", "Rua B")).thenReturn(false);

        // Act
        boolean resultado = restauranteGateway.existePorNomeEEndereco("Pizzaria", "Rua B");

        // Assert
        assertFalse(resultado);
    }

    @Test
    @DisplayName("Deve retornar true quando dono for válido (tipo Dono de Restaurante)")
    void deveRetornarTrueQuandoDonoForValido() {
        // Arrange
        UUID donoId = UUID.randomUUID();
        TipoUsuario tipoDono = new TipoUsuario(UUID.randomUUID(), "Dono de Restaurante");
        Usuario usuario = new Usuario(donoId, "João", tipoDono);
        when(usuarioGateway.buscarPorId(donoId)).thenReturn(Optional.of(usuario));

        // Act
        boolean resultado = restauranteGateway.donoValidoExiste(donoId);

        // Assert
        assertTrue(resultado);
        verify(usuarioGateway).buscarPorId(donoId);
    }

    @Test
    @DisplayName("Deve retornar false quando dono não for do tipo Dono de Restaurante")
    void deveRetornarFalseQuandoDonoNaoForTipoDonoRestaurante() {
        // Arrange
        UUID donoId = UUID.randomUUID();
        TipoUsuario tipoCliente = new TipoUsuario(UUID.randomUUID(), "Cliente");
        Usuario usuario = new Usuario(donoId, "Maria", tipoCliente);
        when(usuarioGateway.buscarPorId(donoId)).thenReturn(Optional.of(usuario));

        // Act
        boolean resultado = restauranteGateway.donoValidoExiste(donoId);

        // Assert
        assertFalse(resultado);
    }

    @Test
    @DisplayName("Deve retornar false quando usuário (dono) não existir")
    void deveRetornarFalseQuandoUsuarioDonoNaoExistir() {
        // Arrange
        UUID donoId = UUID.randomUUID();
        when(usuarioGateway.buscarPorId(donoId)).thenReturn(Optional.empty());

        // Act
        boolean resultado = restauranteGateway.donoValidoExiste(donoId);

        // Assert
        assertFalse(resultado);
    }

    @Test
    @DisplayName("Deve propagar DataAccessException quando repositório falhar ao salvar restaurante")
    void devePropagarExcecaoQuandoRepositorioLancarErroAoSalvarRestaurante() {
        // Arrange
        UUID id = UUID.randomUUID();
        UUID donoId = UUID.randomUUID();
        Restaurante dominio = new Restaurante(id, "Pizzaria", "Rua A", "Italiana", "18:00-23:00", donoId);
        RestauranteEntity entity = new RestauranteEntity(id, "Pizzaria", "Rua A", "Italiana", "18:00-23:00", donoId);

        when(restauranteMapper.toEntity(dominio)).thenReturn(entity);
        when(restauranteRepository.save(entity))
                .thenThrow(new org.springframework.dao.DataIntegrityViolationException("erro de banco"));

        // Act & Assert
        assertThrows(org.springframework.dao.DataAccessException.class, () -> restauranteGateway.salvar(dominio));

        verify(restauranteMapper).toEntity(dominio);
        verify(restauranteRepository).save(entity);
    }
}
