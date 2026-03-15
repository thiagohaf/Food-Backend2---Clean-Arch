package com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.repository;

import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {

    @Query("SELECT u FROM UsuarioEntity u JOIN FETCH u.tipoUsuario WHERE u.id = :id")
    Optional<UsuarioEntity> findByIdWithTipoUsuario(@Param("id") UUID id);
}
