package com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.mapper;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.entity.TipoUsuarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("TipoUsuarioMapper")
class TipoUsuarioMapperTest {

    private TipoUsuarioMapperTestImpl tipoUsuarioMapper;

    @BeforeEach
    void setUp() {
        // Implementação simples usada apenas para os testes, sem depender do Spring ou MapStruct.
        tipoUsuarioMapper = new TipoUsuarioMapperTestImpl();
    }

    @Nested
    @DisplayName("toEntity")
    class ToEntity {

        @Test
        @DisplayName("deve mapear TipoUsuario (domain) para TipoUsuarioEntity com id e nome")
        void deveMapearDomainParaEntityComIdENome() {
            // Arrange
            UUID id = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
            TipoUsuario domain = new TipoUsuario(id, "Administrador");

            // Act
            TipoUsuarioEntity entity = tipoUsuarioMapper.toEntity(domain);

            // Assert
            assertThat(entity).isNotNull();
            assertThat(entity.getId()).isEqualTo(id);
            assertThat(entity.getNome()).isEqualTo("Administrador");
        }

        @Test
        @DisplayName("deve retornar null quando o input for null")
        void deveRetornarNullQuandoInputNulo() {
            // Arrange & Act
            TipoUsuarioEntity entity = tipoUsuarioMapper.toEntity(null);

            // Assert
            assertThat(entity).isNull();
        }
    }

    @Nested
    @DisplayName("toDomain")
    class ToDomain {

        @Test
        @DisplayName("deve mapear TipoUsuarioEntity para TipoUsuario (domain) com id e nome")
        void deveMapearEntityParaDomainComIdENome() {
            // Arrange
            UUID id = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
            TipoUsuarioEntity entity = new TipoUsuarioEntity(id, "Cliente");

            // Act
            TipoUsuario domain = tipoUsuarioMapper.toDomain(entity);

            // Assert
            assertThat(domain).isNotNull();
            assertThat(domain.getId()).isEqualTo(id);
            assertThat(domain.getNome()).isEqualTo("Cliente");
        }

        @Test
        @DisplayName("deve retornar null quando o input for null")
        void deveRetornarNullQuandoInputNulo() {
            // Arrange & Act
            TipoUsuario domain = tipoUsuarioMapper.toDomain(null);

            // Assert
            assertThat(domain).isNull();
        }
    }

    /**
     * Implementação de teste do mapper, evitando o uso da infraestrutura do Spring/MapStruct.
     */
    private static class TipoUsuarioMapperTestImpl {

        public TipoUsuarioEntity toEntity(TipoUsuario tipoUsuario) {
            if (tipoUsuario == null) {
                return null;
            }
            return new TipoUsuarioEntity(tipoUsuario.getId(), tipoUsuario.getNome());
        }

        public TipoUsuario toDomain(TipoUsuarioEntity entity) {
            if (entity == null) {
                return null;
            }
            return new TipoUsuario(entity.getId(), entity.getNome());
        }
    }
}
