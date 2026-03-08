package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain;

import java.util.List;

public class Cardapio {
    private List<ItemCardapio> itens;

    public List<ItemCardapio> getItens() {
        return itens;
    }

    public Cardapio(List<ItemCardapio> itens) {
        this.itens = itens;
    }
}
