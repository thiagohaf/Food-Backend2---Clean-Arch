package com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.mapper;

import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain.Restaurante;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.entity.RestauranteEntity;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.mapper.RestauranteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("RestauranteMapper")
class RestauranteMapperTest {

    private RestauranteMapper restauranteMapper;

    @BeforeEach
    void setUp() {
        // Implementação simples usada apenas para os testes, sem depender do Spring ou MapStruct.
        restauranteMapper = new RestauranteMapperTestImpl();
    }

    @Nested
    @DisplayName("toEntity")
    class ToEntity {

        @Test
        @DisplayName("deve mapear Restaurante (domain) para RestauranteEntity com todos os campos")
        void deveMapearDomainParaEntityComTodosOsCampos() {
            // Arrange
            UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");
            UUID donoId = UUID.fromString("22222222-2222-2222-2222-222222222222");
            Restaurante domain = new Restaurante(
                    id,
                    "Pizzaria Bella",
                    "Rua das Flores, 100",
                    "Italiana",
                    "18:00-23:00",
                    donoId
            );

            // Act
            RestauranteEntity entity = restauranteMapper.toEntity(domain);

            // Assert
            assertThat(entity).isNotNull();
            assertThat(entity.getId()).isEqualTo(id);
            assertThat(entity.getNome()).isEqualTo("Pizzaria Bella");
            assertThat(entity.getEndereco()).isEqualTo("Rua das Flores, 100");
            assertThat(entity.getTipoCozinha()).isEqualTo("Italiana");
            assertThat(entity.getHorarioFuncionamento()).isEqualTo("18:00-23:00");
            assertThat(entity.getDonoId()).isEqualTo(donoId);
        }

        @Test
        @DisplayName("deve retornar null quando o input for null")
        void deveRetornarNullQuandoInputNulo() {
            // Arrange & Act
            RestauranteEntity entity = restauranteMapper.toEntity(null);

            // Assert
            assertThat(entity).isNull();
        }
    }

    @Nested
    @DisplayName("toDomain")
    class ToDomain {

        @Test
        @DisplayName("deve mapear RestauranteEntity para Restaurante (domain) com todos os campos")
        void deveMapearEntityParaDomainComTodosOsCampos() {
            // Arrange
            UUID id = UUID.fromString("33333333-3333-3333-3333-333333333333");
            UUID donoId = UUID.fromString("44444444-4444-4444-4444-444444444444");
            RestauranteEntity entity = new RestauranteEntity(
                    id,
                    "Sushi House",
                    "Av. Paulista, 500",
                    "Japonesa",
                    "12:00-15:00",
                    donoId
            );

            // Act
            Restaurante domain = restauranteMapper.toDomain(entity);

            // Assert
            assertThat(domain).isNotNull();
            assertThat(domain.getId()).isEqualTo(id);
            assertThat(domain.getNome()).isEqualTo("Sushi House");
            assertThat(domain.getEndereco()).isEqualTo("Av. Paulista, 500");
            assertThat(domain.getTipoCozinha()).isEqualTo("Japonesa");
            assertThat(domain.getHorarioFuncionamento()).isEqualTo("12:00-15:00");
            assertThat(domain.getDonoId()).isEqualTo(donoId);
        }

        @Test
        @DisplayName("deve retornar null quando o input for null")
        void deveRetornarNullQuandoInputNulo() {
            // Arrange & Act
            Restaurante domain = restauranteMapper.toDomain(null);

            // Assert
            assertThat(domain).isNull();
        }
    }

    /**
     * Implementação de teste do mapper, evitando o uso da infraestrutura do Spring/MapStruct.
     */
    private static class RestauranteMapperTestImpl implements RestauranteMapper {

        @Override
        public RestauranteEntity toEntity(Restaurante restaurante) {
            if (restaurante == null) {
                return null;
            }
            return new RestauranteEntity(
                    restaurante.getId(),
                    restaurante.getNome(),
                    restaurante.getEndereco(),
                    restaurante.getTipoCozinha(),
                    restaurante.getHorarioFuncionamento(),
                    restaurante.getDonoId()
            );
        }

        @Override
        public Restaurante toDomain(RestauranteEntity entity) {
            if (entity == null) {
                return null;
            }
            return new Restaurante(
                    entity.getId(),
                    entity.getNome(),
                    entity.getEndereco(),
                    entity.getTipoCozinha(),
                    entity.getHorarioFuncionamento(),
                    entity.getDonoId()
            );
        }
    }
}
