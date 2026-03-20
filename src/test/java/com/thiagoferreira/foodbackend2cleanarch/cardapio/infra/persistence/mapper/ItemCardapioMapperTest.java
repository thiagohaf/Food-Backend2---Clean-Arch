package com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.persistence.mapper;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.persistence.entity.ItemCardapioEntity;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.persistence.mapper.ItemCardapioMapper;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.entity.RestauranteEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ItemCardapioMapper")
class ItemCardapioMapperTest {

    private ItemCardapioMapper itemCardapioMapper;

    @BeforeEach
    void setUp() {
        // Implementação simples usada apenas para os testes, sem depender do Spring ou MapStruct.
        itemCardapioMapper = new ItemCardapioMapperTestImpl();
    }

    @Nested
    @DisplayName("toEntity")
    class ToEntity {

        @Test
        @DisplayName("deve mapear ItemCardapio (domain) para ItemCardapioEntity com Restaurante aninhado")
        void deveMapearDomainParaEntityComRestauranteAninhado() {
            // Arrange
            UUID itemId = UUID.fromString("11111111-1111-1111-1111-111111111111");
            UUID restauranteId = UUID.fromString("22222222-2222-2222-2222-222222222222");
            ItemCardapio domain = new ItemCardapio(
                    itemId,
                    "Pizza Margherita",
                    "Pizza clássica",
                    new BigDecimal("39.90"),
                    true,
                    null,
                    restauranteId
            );

            RestauranteEntity restauranteEntity = new RestauranteEntity(
                    restauranteId,
                    "Ristorante Italiano",
                    "Rua A, 123",
                    "Italiana",
                    "18:00-23:00",
                    UUID.fromString("33333333-3333-3333-3333-333333333333")
            );

            // Act
            ItemCardapioEntity entity = itemCardapioMapper.toEntity(domain, restauranteEntity);

            // Assert
            assertThat(entity).isNotNull();
            assertThat(entity.getId()).isEqualTo(itemId);
            assertThat(entity.getNome()).isEqualTo("Pizza Margherita");
            assertThat(entity.getDescricao()).isEqualTo("Pizza clássica");
            assertThat(entity.getPreco()).isEqualByComparingTo("39.90");
            assertThat(entity.getFotoPath()).isEqualTo(null);
            assertThat(entity.getRestaurante()).isNotNull();
            assertThat(entity.getRestaurante().getId()).isEqualTo(restauranteId);
            assertThat(entity.getDataCadastro()).isNotNull();
        }

        @Test
        @DisplayName("deve retornar null quando o ItemCardapio for null")
        void deveRetornarNullQuandoDomainNulo() {
            // Arrange
            RestauranteEntity restauranteEntity = new RestauranteEntity(
                    UUID.randomUUID(),
                    "Restaurante",
                    "Endereço",
                    "Tipo",
                    "00:00-23:59",
                    UUID.randomUUID()
            );

            // Act
            ItemCardapioEntity entity = itemCardapioMapper.toEntity(null, restauranteEntity);

            // Assert
            assertThat(entity).isNull();
        }
    }

    @Nested
    @DisplayName("toDomain")
    class ToDomain {

        @Test
        @DisplayName("deve mapear ItemCardapioEntity para ItemCardapio (domain) com restauranteId populado")
        void deveMapearEntityParaDomainComRestauranteId() {
            // Arrange
            UUID itemId = UUID.fromString("44444444-4444-4444-4444-444444444444");
            UUID restauranteId = UUID.fromString("55555555-5555-5555-5555-555555555555");
            RestauranteEntity restauranteEntity = new RestauranteEntity(
                    restauranteId,
                    "Restaurante Domain",
                    "Rua B, 456",
                    "Brasileira",
                    "11:00-22:00",
                    UUID.fromString("66666666-6666-6666-6666-666666666666")
            );

            ItemCardapioEntity entity = new ItemCardapioEntity(
                    itemId,
                    "Prato Executivo",
                    "Arroz, feijão e bife",
                    new BigDecimal("29.90"),
                    true,
                    "/img/prato-executivo.png",
                    restauranteEntity,
                    LocalDateTime.now()
            );

            // Act
            ItemCardapio domain = itemCardapioMapper.toDomain(entity);

            // Assert
            assertThat(domain).isNotNull();
            assertThat(domain.getId()).isEqualTo(itemId);
            assertThat(domain.getNome()).isEqualTo("Prato Executivo");
            assertThat(domain.getDescricao()).isEqualTo("Arroz, feijão e bife");
            assertThat(domain.getPreco()).isEqualByComparingTo("29.90");
            assertThat(domain.getDisponibilidadeLocal()).isTrue();
            assertThat(domain.getFotoPath()).isEqualTo("/img/prato-executivo.png");
            assertThat(domain.getRestauranteId()).isEqualTo(restauranteId);
        }

        @Test
        @DisplayName("deve retornar null quando o ItemCardapioEntity for null")
        void deveRetornarNullQuandoEntityNulo() {
            // Arrange & Act
            ItemCardapio domain = itemCardapioMapper.toDomain(null);

            // Assert
            assertThat(domain).isNull();
        }
    }

    /**
     * Implementação de teste do mapper, evitando o uso da infraestrutura do Spring/MapStruct.
     */
    private static class ItemCardapioMapperTestImpl implements ItemCardapioMapper {

        @Override
        public ItemCardapio toDomain(ItemCardapioEntity entity) {
            // Reutiliza a lógica de criação definida no default method da interface.
            return ItemCardapioMapper.super.createItemCardapio(entity);
        }

        @Override
        public ItemCardapioEntity toEntity(ItemCardapio itemCardapio, RestauranteEntity restaurante) {
            if (itemCardapio == null) {
                return null;
            }

            return new ItemCardapioEntity(
                    itemCardapio.getId(),
                    itemCardapio.getNome(),
                    itemCardapio.getDescricao(),
                    itemCardapio.getPreco(),
                    itemCardapio.getDisponibilidadeLocal(),
                    itemCardapio.getFotoPath(),
                    restaurante,
                    LocalDateTime.now()
            );
        }
    }
}

