package com.thiagoferreira.foodbackend2cleanarch.restaurante.core.gateway;

import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain.Restaurante;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestauranteGateway {

    Restaurante salvar(Restaurante restaurante);
    boolean existePorNomeEEndereco(String nome, String endereco);
    boolean donoValidoExiste(UUID donoId);
    Optional<Restaurante> buscarPorId(UUID id);
    List<Restaurante> listarTodos();
    void excluir(UUID id);
}
