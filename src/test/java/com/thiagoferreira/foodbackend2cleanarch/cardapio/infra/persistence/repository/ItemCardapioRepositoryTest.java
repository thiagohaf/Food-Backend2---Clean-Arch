package com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.persistence.repository;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.persistence.entity.ItemCardapioEntity;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.entity.RestauranteEntity;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.repository.RestauranteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.flyway.enabled=false"
})
class ItemCardapioRepositoryTest {

    @Autowired
    private ItemCardapioRepository itemCardapioRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Test
    @DisplayName("Deve persistir ItemCardapioEntity com sucesso")
    void devePersistirItemCardapioComSucesso() {
        // Arrange
        UUID restauranteId = UUID.randomUUID();
        RestauranteEntity restaurante = new RestauranteEntity(
                restauranteId,
                "Restaurante Teste",
                "Rua A, 123",
                "Italiana",
                "18:00-23:00",
                UUID.randomUUID()
        );
        restauranteRepository.saveAndFlush(restaurante);

        UUID itemId = UUID.randomUUID();
        ItemCardapioEntity item = new ItemCardapioEntity(
                itemId,
                "Pizza Margherita",
                "Pizza clássica com tomate e mussarela",
                new BigDecimal("39.90"),
                "PIZZA",
                restaurante,
                LocalDateTime.now()
        );

        // Act
        ItemCardapioEntity salvo = itemCardapioRepository.saveAndFlush(item);
        Optional<ItemCardapioEntity> buscado = itemCardapioRepository.findById(itemId);

        // Assert
        assertNotNull(salvo);
        assertEquals(itemId, salvo.getId());
        assertEquals("Pizza Margherita", salvo.getNome());
        assertEquals("Pizza clássica com tomate e mussarela", salvo.getDescricao());
        assertEquals(new BigDecimal("39.90"), salvo.getPreco());
        assertEquals("PIZZA", salvo.getCategoria());
        assertNotNull(salvo.getRestaurante());
        assertEquals(restauranteId, salvo.getRestaurante().getId());

        assertTrue(buscado.isPresent());
        assertEquals(itemId, buscado.get().getId());
    }

    @Test
    @DisplayName("findByIdWithRestaurante deve retornar item com Restaurante carregado via JOIN FETCH")
    void deveBuscarPorIdComRestauranteUsandoJoinFetch() {
        // Arrange
        UUID restauranteId = UUID.randomUUID();
        RestauranteEntity restaurante = new RestauranteEntity(
                restauranteId,
                "Restaurante Join Fetch",
                "Av. Central, 500",
                "Brasileira",
                "11:00-23:00",
                UUID.randomUUID()
        );
        restauranteRepository.saveAndFlush(restaurante);

        UUID itemId = UUID.randomUUID();
        ItemCardapioEntity item = new ItemCardapioEntity(
                itemId,
                "Prato Executivo",
                "Arroz, feijão, bife e salada",
                new BigDecimal("29.90"),
                "PRATO_PRINCIPAL",
                restaurante,
                LocalDateTime.now()
        );
        itemCardapioRepository.saveAndFlush(item);

        // Act
        Optional<ItemCardapioEntity> resultado = itemCardapioRepository.findByIdWithRestaurante(itemId);

        // Assert
        assertTrue(resultado.isPresent());
        ItemCardapioEntity encontrado = resultado.get();

        assertEquals(itemId, encontrado.getId());
        assertEquals("Prato Executivo", encontrado.getNome());
        assertNotNull(encontrado.getRestaurante(), "Restaurante deve estar carregado pelo JOIN FETCH");
        assertEquals(restauranteId, encontrado.getRestaurante().getId());
        assertEquals("Restaurante Join Fetch", encontrado.getRestaurante().getNome());
    }
}

