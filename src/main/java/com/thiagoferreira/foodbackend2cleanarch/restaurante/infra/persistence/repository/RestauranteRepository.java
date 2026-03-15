package com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.repository;

import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.persistence.entity.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RestauranteRepository extends JpaRepository<RestauranteEntity, UUID> {

    boolean existsByNomeAndEndereco(String nome, String endereco);

    Optional<RestauranteEntity> findById(UUID id);
}
