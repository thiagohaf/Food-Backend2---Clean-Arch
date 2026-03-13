package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.mapper;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.dto.CriarItemCardapioInput;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.ItemCardapioCoreMapperException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ItemCardapioCoreMapperTest {

    private final ItemCardapioCoreMapper mapper = new ItemCardapioCoreMapper();

    @Test
    @DisplayName("Deve mapear CriarItemCardapioInput para ItemCardapio com sucesso")
    void deveMapearInputParaDomainCorretamente() {
        UUID id = UUID.randomUUID();
        UUID restauranteId = UUID.randomUUID();
        CriarItemCardapioInput input = new CriarItemCardapioInput(
                id,
                "Lasanha",
                "Lasanha à Bolonhesa",
                new BigDecimal("45.90"),
                true,
                "/fotos/lasanha.jpg",
                restauranteId
        );

        ItemCardapio item = mapper.toDomain(input);

        assertNotNull(item);
        assertEquals(id, item.getId());
        assertEquals("Lasanha", item.getNome());
        assertEquals("Lasanha à Bolonhesa", item.getDescricao());
        assertEquals(new BigDecimal("45.90"), item.getPreco());
        assertTrue(item.getDisponibilidadeLocal());
        assertEquals("/fotos/lasanha.jpg", item.getFotoPath());
        assertEquals(restauranteId, item.getRestauranteId());
    }

    @Test
    @DisplayName("Deve lançar ItemCardapioCoreMapperException quando o input for nulo")
    void deveLancarExcecaoQuandoInputNulo() {
        assertThrows(ItemCardapioCoreMapperException.class, () -> mapper.toDomain(null));
    }

}
