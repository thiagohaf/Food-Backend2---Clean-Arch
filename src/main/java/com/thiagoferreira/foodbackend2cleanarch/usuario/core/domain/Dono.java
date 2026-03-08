package com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain;

import java.util.List;
import java.util.UUID;

public class Dono extends UsuarioBase {

    private List<Restaurante> restaurantes;

    public List<Restaurante> getRestaurantes() {
        return restaurantes;
    }

    public Dono(UUID id, String nomeTipo, List<Restaurante> restaurantes) {
        super(id, nomeTipo);
        this.restaurantes = restaurantes;
    }

    public void adicionarRestaurantes(List<Restaurante> restaurantes) {
        this.restaurantes = restaurantes;
    }
}
