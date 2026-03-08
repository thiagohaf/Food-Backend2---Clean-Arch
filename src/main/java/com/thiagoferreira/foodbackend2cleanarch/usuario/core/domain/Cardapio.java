package com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain;

import java.util.List;
import java.util.UUID;

public class Cardapio {
    private List<ItemCardapio> itens;

    public List<ItemCardapio> getItens() {
        return itens;
    }

    public Cardapio(List<ItemCardapio> itens) {
        this.itens = itens;
    }
}
