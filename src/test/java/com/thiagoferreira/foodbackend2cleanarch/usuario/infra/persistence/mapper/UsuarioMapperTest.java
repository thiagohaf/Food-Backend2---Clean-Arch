package com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.mapper;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Usuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.entity.TipoUsuarioEntity;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.entity.UsuarioEntity;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.mapper.TipoUsuarioMapperImpl;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.mapper.UsuarioMapper;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.mapper.UsuarioMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UsuarioMapper")
class UsuarioMapperTest {

    private UsuarioMapper usuarioMapper;

    @BeforeEach
    void setUp() {
        UsuarioMapperImpl impl = new UsuarioMapperImpl();
        ReflectionTestUtils.setField(impl, "tipoUsuarioMapper", new TipoUsuarioMapperImpl());
        usuarioMapper = impl;
    }

    @Nested
    @DisplayName("toEntity")
    class ToEntity {

        @Test
        @DisplayName("deve mapear Usuario (domain) para UsuarioEntity com TipoUsuario aninhado mapeado")
        void deveMapearDomainParaEntityComTipoUsuarioAninhado() {
            // Arrange
            UUID usuarioId = UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc");
            UUID tipoId = UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd");
            TipoUsuario tipoUsuario = new TipoUsuario(tipoId, "Gerente");
            Usuario domain = new Usuario(usuarioId, "João Silva", tipoUsuario);

            // Act
            UsuarioEntity entity = usuarioMapper.toEntity(domain);

            // Assert
            assertThat(entity).isNotNull();
            assertThat(entity.getId()).isEqualTo(usuarioId);
            assertThat(entity.getNome()).isEqualTo("João Silva");
            assertThat(entity.getTipoUsuario()).isNotNull();
            assertThat(entity.getTipoUsuario().getId()).isEqualTo(tipoId);
            assertThat(entity.getTipoUsuario().getNome()).isEqualTo("Gerente");
        }

        @Test
        @DisplayName("deve retornar null quando o input for null")
        void deveRetornarNullQuandoInputNulo() {
            // Arrange & Act
            UsuarioEntity entity = usuarioMapper.toEntity(null);

            // Assert
            assertThat(entity).isNull();
        }
    }

    @Nested
    @DisplayName("toDomain")
    class ToDomain {

        @Test
        @DisplayName("deve mapear UsuarioEntity para Usuario (domain) com composição TipoUsuario reconstruída")
        void deveMapearEntityParaDomainComComposicaoTipoUsuario() {
            // Arrange
            UUID usuarioId = UUID.fromString("eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee");
            UUID tipoId = UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff");
            TipoUsuarioEntity tipoEntity = new TipoUsuarioEntity(tipoId, "Atendente");
            UsuarioEntity entity = new UsuarioEntity(usuarioId, "Maria Santos", tipoEntity);

            // Act
            Usuario domain = usuarioMapper.toDomain(entity);

            // Assert
            assertThat(domain).isNotNull();
            assertThat(domain.getId()).isEqualTo(usuarioId);
            assertThat(domain.getNome()).isEqualTo("Maria Santos");
            assertThat(domain.getTipoUsuario()).isNotNull();
            assertThat(domain.getTipoUsuario().getId()).isEqualTo(tipoId);
            assertThat(domain.getTipoUsuario().getNome()).isEqualTo("Atendente");
        }

        @Test
        @DisplayName("deve retornar null quando o input for null")
        void deveRetornarNullQuandoInputNulo() {
            // Arrange & Act
            Usuario domain = usuarioMapper.toDomain(null);

            // Assert
            assertThat(domain).isNull();
        }
    }
}
