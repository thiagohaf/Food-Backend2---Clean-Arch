package com.thiagoferreira.foodbackend2cleanarch.restaurante.core.mapper;

import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain.Restaurante;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.dto.CriarRestauranteInput;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.exception.RestauranteCoreMapperException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RestauranteCoreMapperTest {
    private final RestauranteCoreMapper mapper = new RestauranteCoreMapper();

    @Test
    @DisplayName("Deve mapear CriarRestauranteInput para Restaurante com sucesso")
    void deveMapearInputParaDomainCorretamente() {
        UUID id = UUID.randomUUID();
        UUID donoId = UUID.randomUUID();
        CriarRestauranteInput input = new CriarRestauranteInput(
                id, "Pizzaria", "Rua A", "Italiana", "18:00-23:00", donoId
        );

        Restaurante restaurante = mapper.toDomain(input);

        assertNotNull(restaurante);
        assertEquals(id, restaurante.getId());
        assertEquals("Pizzaria", restaurante.getNome());
        assertEquals("Rua A", restaurante.getEndereco());
        assertEquals("Italiana", restaurante.getTipoCozinha());
        assertEquals("18:00-23:00", restaurante.getHorarioFuncionamento());
        assertEquals(donoId, restaurante.getDonoId());
    }

    @Test
    @DisplayName("Deve lançar RestauranteCoreMapperException quando o input for nulo")
    void deveLancarExcecaoQuandoInputNulo() {
        assertThrows(RestauranteCoreMapperException.class, () -> mapper.toDomain(null));
    }
}
