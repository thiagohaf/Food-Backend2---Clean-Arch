package com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.repository;

import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.entity.TipoUsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuarioEntity, UUID> {

    boolean existsByNome(String nome);
}
