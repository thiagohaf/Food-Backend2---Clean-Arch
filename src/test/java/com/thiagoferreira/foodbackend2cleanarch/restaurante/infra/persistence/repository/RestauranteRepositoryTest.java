package com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.repository;

import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.entity.RestauranteEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.flyway.enabled=false"
})
class RestauranteRepositoryTest {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Test
    @DisplayName("Deve persistir e buscar restaurante por ID com sucesso")
    void devePersistirEBuscarRestaurantePorIdComSucesso() {
        // Arrange
        UUID id = UUID.randomUUID();
        UUID donoId = UUID.randomUUID();
        RestauranteEntity entity = new RestauranteEntity(
                id,
                "Pizzaria",
                "Rua das Flores, 100",
                "Italiana",
                "18:00-23:00",
                donoId
        );

        // Act
        RestauranteEntity salva = restauranteRepository.save(entity);
        Optional<RestauranteEntity> buscada = restauranteRepository.findById(id);

        // Assert
        assertNotNull(salva);
        assertEquals(id, salva.getId());
        assertEquals("Pizzaria", salva.getNome());
        assertEquals("Rua das Flores, 100", salva.getEndereco());
        assertEquals("Italiana", salva.getTipoCozinha());
        assertEquals("18:00-23:00", salva.getHorarioFuncionamento());
        assertEquals(donoId, salva.getDonoId());

        assertTrue(buscada.isPresent());
        assertEquals(salva.getId(), buscada.get().getId());
        assertEquals(salva.getNome(), buscada.get().getNome());
    }

    @Test
    @DisplayName("Deve retornar Optional vazio ao buscar por ID inexistente")
    void deveRetornarOptionalVazioAoBuscarPorIdInexistente() {
        // Arrange
        UUID idInexistente = UUID.randomUUID();

        // Act
        Optional<RestauranteEntity> resultado = restauranteRepository.findById(idInexistente);

        // Assert
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Deve validar constraint unique (nome, endereco) ao salvar duplicata")
    void deveValidarConstraintUniqueNomeEnderecoAoSalvarDuplicata() {
        // Arrange
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID donoId = UUID.randomUUID();
        RestauranteEntity entity1 = new RestauranteEntity(
                id1,
                "Pizzaria",
                "Rua A, 1",
                "Italiana",
                "18:00-23:00",
                donoId
        );
        restauranteRepository.saveAndFlush(entity1);

        RestauranteEntity entity2 = new RestauranteEntity(
                id2,
                "Pizzaria",
                "Rua A, 1",
                "Brasileira",
                "12:00-22:00",
                donoId
        );

        // Act & Assert
        assertThrows(DataIntegrityViolationException.class, () -> {
            restauranteRepository.saveAndFlush(entity2);
        });
    }

    @Test
    @DisplayName("Deve retornar true quando existir restaurante por nome e endereço")
    void deveRetornarTrueQuandoExistirPorNomeEEndereco() {
        // Arrange
        UUID id = UUID.randomUUID();
        UUID donoId = UUID.randomUUID();
        restauranteRepository.save(new RestauranteEntity(
                id, "Sushi Bar", "Av. Brasil, 500", "Japonesa", "19:00-00:00", donoId
        ));

        // Act
        boolean existe = restauranteRepository.existsByNomeAndEndereco("Sushi Bar", "Av. Brasil, 500");

        // Assert
        assertTrue(existe);
    }

    @Test
    @DisplayName("Deve retornar false quando não existir restaurante por nome e endereço")
    void deveRetornarFalseQuandoNaoExistirPorNomeEEndereco() {
        // Act
        boolean existe = restauranteRepository.existsByNomeAndEndereco("Restaurante Fantasma", "Rua Inexistente, 0");

        // Assert
        assertFalse(existe);
    }

    @Test
    @DisplayName("Deve falhar ao persistir entity com nome nulo (nullable=false)")
    void deveFalharAoPersistirComNomeNulo() {
        // Arrange: constraint nullable=false na coluna nome
        UUID id = UUID.randomUUID();
        UUID donoId = UUID.randomUUID();
        RestauranteEntity entity = new RestauranteEntity(
                id,
                null, // nome nulo viola nullable=false
                "Rua A",
                "Italiana",
                "18:00-23:00",
                donoId
        );

        // Act & Assert
        assertThrows(DataIntegrityViolationException.class, () ->
                restauranteRepository.saveAndFlush(entity)
        );
    }
}
