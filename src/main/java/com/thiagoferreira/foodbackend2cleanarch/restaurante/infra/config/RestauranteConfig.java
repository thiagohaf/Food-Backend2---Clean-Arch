package com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.config;

import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.gateway.RestauranteGateway;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.mapper.RestauranteCoreMapper;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase.AtualizarRestauranteUseCase;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase.BuscarRestaurantePorIdUseCase;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase.CriarRestauranteUseCase;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase.ExcluirRestauranteUseCase;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase.ListarRestaurantesUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestauranteConfig {

    @Bean
    public RestauranteCoreMapper restauranteCoreMapper() {
        return new RestauranteCoreMapper();
    }

    @Bean
    public CriarRestauranteUseCase criarRestauranteUseCase(RestauranteGateway restauranteGateway,
                                                           RestauranteCoreMapper restauranteCoreMapper) {
        return new CriarRestauranteUseCase(restauranteGateway, restauranteCoreMapper);
    }

    @Bean
    public BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase(RestauranteGateway restauranteGateway) {
        return new BuscarRestaurantePorIdUseCase(restauranteGateway);
    }

    @Bean
    public ListarRestaurantesUseCase listarRestaurantesUseCase(RestauranteGateway restauranteGateway) {
        return new ListarRestaurantesUseCase(restauranteGateway);
    }

    @Bean
    public AtualizarRestauranteUseCase atualizarRestauranteUseCase(RestauranteGateway restauranteGateway) {
        return new AtualizarRestauranteUseCase(restauranteGateway);
    }

    @Bean
    public ExcluirRestauranteUseCase excluirRestauranteUseCase(RestauranteGateway restauranteGateway) {
        return new ExcluirRestauranteUseCase(restauranteGateway);
    }
}
