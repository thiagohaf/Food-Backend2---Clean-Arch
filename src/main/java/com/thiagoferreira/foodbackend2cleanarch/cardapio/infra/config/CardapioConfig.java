package com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.config;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.gateway.ItemCardapioGateway;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.mapper.ItemCardapioCoreMapper;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase.AtualizarItemCardapioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase.BuscarItemCardapioPorIdUseCase;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase.CriarItemCardapioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase.ExcluirItemCardapioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase.ListarItensCardapioPorRestauranteUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CardapioConfig {

    @Bean
    public ItemCardapioCoreMapper itemCardapioCoreMapper() {
        return new ItemCardapioCoreMapper();
    }

    @Bean
    public CriarItemCardapioUseCase criarItemCardapioUseCase(ItemCardapioGateway itemCardapioGateway,
                                                             ItemCardapioCoreMapper itemCardapioCoreMapper) {
        return new CriarItemCardapioUseCase(itemCardapioGateway, itemCardapioCoreMapper);
    }

    @Bean
    public BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase(ItemCardapioGateway itemCardapioGateway) {
        return new BuscarItemCardapioPorIdUseCase(itemCardapioGateway);
    }

    @Bean
    public ListarItensCardapioPorRestauranteUseCase listarItensCardapioPorRestauranteUseCase(ItemCardapioGateway itemCardapioGateway) {
        return new ListarItensCardapioPorRestauranteUseCase(itemCardapioGateway);
    }

    @Bean
    public AtualizarItemCardapioUseCase atualizarItemCardapioUseCase(ItemCardapioGateway itemCardapioGateway) {
        return new AtualizarItemCardapioUseCase(itemCardapioGateway);
    }

    @Bean
    public ExcluirItemCardapioUseCase excluirItemCardapioUseCase(ItemCardapioGateway itemCardapioGateway) {
        return new ExcluirItemCardapioUseCase(itemCardapioGateway);
    }
}

