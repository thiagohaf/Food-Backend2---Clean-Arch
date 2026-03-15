package com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.repository;

import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.entity.TipoUsuarioEntity;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.entity.UsuarioEntity;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.repository.TipoUsuarioRepository;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Test
    @DisplayName("Deve gravar Usuario com TipoUsuario associado")
    void deveGravarUsuarioComTipoUsuarioAssociado() {
        // Arrange
        UUID tipoId = UUID.randomUUID();
        UUID usuarioId = UUID.randomUUID();
        TipoUsuarioEntity tipoEntity = new TipoUsuarioEntity(tipoId, "Cliente");
        tipoUsuarioRepository.saveAndFlush(tipoEntity);

        UsuarioEntity usuarioEntity = new UsuarioEntity(usuarioId, "João Silva", tipoEntity);

        // Act
        UsuarioEntity salva = usuarioRepository.save(usuarioEntity);
        Optional<UsuarioEntity> buscada = usuarioRepository.findById(usuarioId);

        // Assert
        assertNotNull(salva);
        assertEquals(usuarioId, salva.getId());
        assertEquals("João Silva", salva.getNome());
        assertNotNull(salva.getTipoUsuario());
        assertEquals(tipoId, salva.getTipoUsuario().getId());
        assertEquals("Cliente", salva.getTipoUsuario().getNome());

        assertTrue(buscada.isPresent());
        assertEquals(usuarioId, buscada.get().getId());
        assertEquals("João Silva", buscada.get().getNome());
    }

    @Test
    @DisplayName("findByIdWithTipoUsuario deve retornar Optional com entidade e relacionamento preenchido (sem lazy exception)")
    void findByIdWithTipoUsuarioDeveRetornarOptionalComEntidadeERelacionamentoPreenchido() {
        // Arrange
        UUID tipoId = UUID.randomUUID();
        UUID usuarioId = UUID.randomUUID();
        TipoUsuarioEntity tipoEntity = new TipoUsuarioEntity(tipoId, "Admin");
        tipoUsuarioRepository.saveAndFlush(tipoEntity);

        UsuarioEntity usuarioEntity = new UsuarioEntity(usuarioId, "Maria Admin", tipoEntity);
        usuarioRepository.saveAndFlush(usuarioEntity);

        // Act
        Optional<UsuarioEntity> resultado = usuarioRepository.findByIdWithTipoUsuario(usuarioId);

        // Assert
        assertTrue(resultado.isPresent());
        UsuarioEntity usuario = resultado.get();
        assertEquals(usuarioId, usuario.getId());
        assertEquals("Maria Admin", usuario.getNome());
        assertNotNull(usuario.getTipoUsuario(), "tipoUsuario deve estar carregado (JOIN FETCH)");
        assertEquals(tipoId, usuario.getTipoUsuario().getId());
        assertEquals("Admin", usuario.getTipoUsuario().getNome());
        // Acesso ao lazy não deve lançar LazyInitializationException pois foi feito JOIN FETCH
    }

    @Test
    @DisplayName("findByIdWithTipoUsuario deve retornar Optional vazio para ID inexistente")
    void findByIdWithTipoUsuarioDeveRetornarOptionalVazioParaIdInexistente() {
        // Arrange
        UUID idInexistente = UUID.randomUUID();

        // Act
        Optional<UsuarioEntity> resultado = usuarioRepository.findByIdWithTipoUsuario(idInexistente);

        // Assert
        assertTrue(resultado.isEmpty());
    }
}
