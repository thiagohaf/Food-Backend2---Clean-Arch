package com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.repository;

import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.entity.TipoUsuarioEntity;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.repository.TipoUsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@EntityScan(basePackages = "com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.entity")
@EnableJpaRepositories(basePackages = "com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.repository")
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.flyway.enabled=false"
})
class TipoUsuarioRepositoryTest {

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Test
    @DisplayName("Deve persistir TipoUsuario com sucesso")
    void devePersistirTipoUsuarioComSucesso() {
        // Arrange
        UUID id = UUID.randomUUID();
        TipoUsuarioEntity entity = new TipoUsuarioEntity(id, "Cliente");

        // Act
        TipoUsuarioEntity salva = tipoUsuarioRepository.save(entity);
        Optional<TipoUsuarioEntity> buscada = tipoUsuarioRepository.findById(id);

        // Assert
        assertNotNull(salva);
        assertEquals(id, salva.getId());
        assertEquals("Cliente", salva.getNome());
        assertTrue(buscada.isPresent());
        assertEquals(salva.getId(), buscada.get().getId());
        assertEquals(salva.getNome(), buscada.get().getNome());
    }

    @Test
    @DisplayName("Deve validar constraint unique no nome ao salvar duplicata")
    void deveValidarConstraintUniqueNoNomeAoSalvarDuplicata() {
        // Arrange
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        TipoUsuarioEntity entity1 = new TipoUsuarioEntity(id1, "Admin");
        tipoUsuarioRepository.saveAndFlush(entity1);

        TipoUsuarioEntity entity2 = new TipoUsuarioEntity(id2, "Admin");

        // Act & Assert
        assertThrows(DataIntegrityViolationException.class, () ->
                tipoUsuarioRepository.saveAndFlush(entity2)
        );
    }

    @Test
    @DisplayName("Deve retornar true quando existir tipo por nome")
    void deveRetornarTrueQuandoExistirPorNome() {
        // Arrange
        UUID id = UUID.randomUUID();
        tipoUsuarioRepository.save(new TipoUsuarioEntity(id, "Dono de Restaurante"));

        // Act
        boolean existe = tipoUsuarioRepository.existsByNome("Dono de Restaurante");

        // Assert
        assertTrue(existe);
    }

    @Test
    @DisplayName("Deve retornar false quando não existir tipo por nome")
    void deveRetornarFalseQuandoNaoExistirPorNome() {
        // Act
        boolean existe = tipoUsuarioRepository.existsByNome("Tipo Inexistente");

        // Assert
        assertFalse(existe);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio ao buscar por ID inexistente")
    void deveRetornarOptionalVazioAoBuscarPorIdInexistente() {
        // Arrange
        UUID idInexistente = UUID.randomUUID();

        // Act
        Optional<TipoUsuarioEntity> resultado = tipoUsuarioRepository.findById(idInexistente);

        // Assert
        assertTrue(resultado.isEmpty());
    }
}
