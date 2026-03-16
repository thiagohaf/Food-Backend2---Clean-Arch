package com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.persistence.repository;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.persistence.entity.ItemCardapioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemCardapioRepository extends JpaRepository<ItemCardapioEntity, UUID> {

    @Query("SELECT i FROM ItemCardapioEntity i JOIN FETCH i.restaurante WHERE i.id = :id")
    Optional<ItemCardapioEntity> findByIdWithRestaurante(@Param("id") UUID id);

    List<ItemCardapioEntity> findByRestaurante_Id(UUID restauranteId);

    boolean existsByNomeAndRestaurante_Id(String nome, UUID restauranteId);
}
