package com.thiagoferreira.foodbackend2cleanarch.infra.persistence.mapper;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.persistence.entity.ItemCardapioEntity;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.persistence.mapper.ItemCardapioMapperImpl;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain.Restaurante;
import com.thiagoferreira.foodbackend2cleanarch.util.exception.ValidacaoRegraNegocioException;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.entity.RestauranteEntity;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.mapper.RestauranteMapperImpl;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Usuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.entity.TipoUsuarioEntity;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.entity.UsuarioEntity;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.mapper.TipoUsuarioMapperImpl;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.mapper.UsuarioMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Exercita as implementações geradas pelo MapStruct ({@code *MapperImpl}), que não são cobertas
 * pelos doubles manuais usados em outros testes de mapper.
 */
@DisplayName("Implementações MapStruct geradas (*MapperImpl)")
class MapStructMapperImplementationsTest {

    @Nested
    @DisplayName("RestauranteMapperImpl")
    class RestauranteMapperImplTests {

        private final RestauranteMapperImpl mapper = new RestauranteMapperImpl();

        @Test
        void toEntityRetornaNullQuandoDomainNulo() {
            assertThat(mapper.toEntity(null)).isNull();
        }

        @Test
        void toDomainRetornaNullQuandoEntityNulo() {
            assertThat(mapper.toDomain(null)).isNull();
        }

        @Test
        void mapeiaIdaEVolta() {
            UUID id = UUID.randomUUID();
            UUID dono = UUID.randomUUID();
            Restaurante domain = new Restaurante(id, "R", "E", "T", "12:00-18:00", dono);
            RestauranteEntity entity = mapper.toEntity(domain);
            assertThat(entity.getNome()).isEqualTo("R");
            assertThat(mapper.toDomain(entity).getTipoCozinha()).isEqualTo("T");
        }
    }

    @Nested
    @DisplayName("TipoUsuarioMapperImpl")
    class TipoUsuarioMapperImplTests {

        private final TipoUsuarioMapperImpl mapper = new TipoUsuarioMapperImpl();

        @Test
        void toEntityRetornaNullQuandoDomainNulo() {
            assertThat(mapper.toEntity(null)).isNull();
        }

        @Test
        void toDomainRetornaNullQuandoEntityNulo() {
            assertThat(mapper.toDomain(null)).isNull();
        }

        @Test
        void mapeiaIdaEVolta() {
            UUID id = UUID.randomUUID();
            TipoUsuario domain = new TipoUsuario(id, "Admin");
            TipoUsuarioEntity entity = mapper.toEntity(domain);
            assertThat(mapper.toDomain(entity).getNome()).isEqualTo("Admin");
        }
    }

    @Nested
    @DisplayName("UsuarioMapperImpl")
    class UsuarioMapperImplTests {

        private UsuarioMapperImpl mapper;

        @BeforeEach
        void setUp() {
            mapper = new UsuarioMapperImpl();
            ReflectionTestUtils.setField(mapper, "tipoUsuarioMapper", new TipoUsuarioMapperImpl());
        }

        @Test
        void toEntityRetornaNullQuandoDomainNulo() {
            assertThat(mapper.toEntity(null)).isNull();
        }

        @Test
        void toDomainRetornaNullQuandoEntityNulo() {
            assertThat(mapper.toDomain(null)).isNull();
        }

        @Test
        void mapeiaUsuarioComTipoAninhado() {
            UUID uid = UUID.randomUUID();
            UUID tid = UUID.randomUUID();
            Usuario domain = new Usuario(uid, "N", new TipoUsuario(tid, "T"));
            UsuarioEntity entity = mapper.toEntity(domain);
            assertThat(entity.getTipoUsuario().getNome()).isEqualTo("T");
            assertThat(mapper.toDomain(entity).getNome()).isEqualTo("N");
        }
    }

    @Nested
    @DisplayName("ItemCardapioMapperImpl")
    class ItemCardapioMapperImplTests {

        private final ItemCardapioMapperImpl mapper = new ItemCardapioMapperImpl();

        @Test
        void toDomainRetornaNullQuandoEntityNulo() {
            assertThat(mapper.toDomain(null)).isNull();
        }

        @Test
        void toEntityRetornaNullQuandoItemERestauranteNulos() {
            assertThat(mapper.toEntity(null, null)).isNull();
        }

        @Test
        void toDomainComRestauranteNuloDisparaValidacaoDoDominio() {
            UUID itemId = UUID.randomUUID();
            ItemCardapioEntity entity = new ItemCardapioEntity(
                    itemId,
                    "N",
                    "D",
                    new BigDecimal("1"),
                    true,
                    null,
                    null,
                    LocalDateTime.now()
            );
            assertThatThrownBy(() -> mapper.toDomain(entity))
                    .isInstanceOf(ValidacaoRegraNegocioException.class);
        }

        @Test
        void toEntityComItemNuloMasRestauranteInformado() {
            RestauranteEntity restaurante = new RestauranteEntity(
                    UUID.randomUUID(), "R", "E", "T", "H", UUID.randomUUID()
            );
            ItemCardapioEntity entity = mapper.toEntity(null, restaurante);
            assertThat(entity).isNotNull();
            assertThat(entity.getRestaurante()).isSameAs(restaurante);
        }
    }
}
