package com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain;

import java.util.UUID;

public class Restaurante {
    private UUID id;
    private String nome;
    private String endereco;
    private String tipoCulinaria;
    private String horarioFuncionamento;
    private UUID donoId;

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTipoCulinaria() {
        return tipoCulinaria;
    }

    public String getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public UUID getDonoId() {
        return donoId;
    }

    public Restaurante(UUID id, String nome, String endereco, String tipoCulinaria, String horarioFuncionamento, UUID donoId) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCulinaria = tipoCulinaria;
        this.horarioFuncionamento = horarioFuncionamento;
        this.donoId = donoId;
    }
}
