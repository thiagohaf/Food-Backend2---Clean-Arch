package com.thiagoferreira.foodbackend2cleanarch.usuario.infra.config;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.TipoUsuarioGateway;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.gateway.UsuarioGateway;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.mapper.TipoUsuarioCoreMapper;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.mapper.UsuarioCoreMapper;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.AtualizarTipoUsuarioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.AtualizarUsuarioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.BuscarTipoUsuarioPorIdUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.BuscarUsuarioPorIdUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.CriarTipoUsuarioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.CriarUsuarioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.ExcluirTipoUsuarioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.ExcluirUsuarioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.ListarTiposUsuarioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.ListarUsuariosUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioConfig {

    @Bean
    public TipoUsuarioCoreMapper tipoUsuarioCoreMapper() {
        return new TipoUsuarioCoreMapper();
    }

    @Bean
    public UsuarioCoreMapper usuarioCoreMapper() {
        return new UsuarioCoreMapper();
    }

    @Bean
    public CriarTipoUsuarioUseCase criarTipoUsuarioUseCase(TipoUsuarioGateway tipoUsuarioGateway,
                                                           TipoUsuarioCoreMapper tipoUsuarioCoreMapper) {
        return new CriarTipoUsuarioUseCase(tipoUsuarioGateway, tipoUsuarioCoreMapper);
    }

    @Bean
    public BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase(TipoUsuarioGateway tipoUsuarioGateway) {
        return new BuscarTipoUsuarioPorIdUseCase(tipoUsuarioGateway);
    }

    @Bean
    public ListarTiposUsuarioUseCase listarTiposUsuarioUseCase(TipoUsuarioGateway tipoUsuarioGateway) {
        return new ListarTiposUsuarioUseCase(tipoUsuarioGateway);
    }

    @Bean
    public AtualizarTipoUsuarioUseCase atualizarTipoUsuarioUseCase(TipoUsuarioGateway tipoUsuarioGateway) {
        return new AtualizarTipoUsuarioUseCase(tipoUsuarioGateway);
    }

    @Bean
    public ExcluirTipoUsuarioUseCase excluirTipoUsuarioUseCase(TipoUsuarioGateway tipoUsuarioGateway) {
        return new ExcluirTipoUsuarioUseCase(tipoUsuarioGateway);
    }

    @Bean
    public CriarUsuarioUseCase criarUsuarioUseCase(UsuarioGateway usuarioGateway,
                                                  TipoUsuarioGateway tipoUsuarioGateway,
                                                  UsuarioCoreMapper usuarioCoreMapper) {
        return new CriarUsuarioUseCase(usuarioGateway, tipoUsuarioGateway, usuarioCoreMapper);
    }

    @Bean
    public BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase(UsuarioGateway usuarioGateway) {
        return new BuscarUsuarioPorIdUseCase(usuarioGateway);
    }

    @Bean
    public ListarUsuariosUseCase listarUsuariosUseCase(UsuarioGateway usuarioGateway) {
        return new ListarUsuariosUseCase(usuarioGateway);
    }

    @Bean
    public AtualizarUsuarioUseCase atualizarUsuarioUseCase(TipoUsuarioGateway tipoUsuarioGateway,
                                                            UsuarioGateway usuarioGateway) {
        return new AtualizarUsuarioUseCase(tipoUsuarioGateway, usuarioGateway);
    }

    @Bean
    public ExcluirUsuarioUseCase excluirUsuarioUseCase(UsuarioGateway usuarioGateway) {
        return new ExcluirUsuarioUseCase(usuarioGateway);
    }
}
