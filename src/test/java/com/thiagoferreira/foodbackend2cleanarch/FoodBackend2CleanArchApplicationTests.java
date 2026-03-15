package com.thiagoferreira.foodbackend2cleanarch;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.TipoUsuarioGateway;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.UsuarioGateway;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.mock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@Import(FoodBackend2CleanArchApplicationTests.TestBeansConfig.class)
class FoodBackend2CleanArchApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * Gateways do módulo Usuário ainda não possuem implementação na infra;
     * fornece mocks para o contexto de teste subir.
     */
    @TestConfiguration
    static class TestBeansConfig {
        @Bean
        UsuarioGateway usuarioGateway() {
            return mock(UsuarioGateway.class);
        }

        @Bean
        TipoUsuarioGateway tipoUsuarioGateway() {
            return mock(TipoUsuarioGateway.class);
        }
    }
}
