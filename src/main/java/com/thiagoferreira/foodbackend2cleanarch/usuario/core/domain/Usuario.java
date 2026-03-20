package com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.rule.UsuarioValidador;

import java.util.UUID;

/**
 * Entidade de domínio que representa um usuário do sistema, sempre associado a um {@link TipoUsuario}
 * (ex.: perfil de acesso ou papel de negócio).
 */
public class Usuario {
    private UUID id;
    private String nome;
    private TipoUsuario tipoUsuario;

    /**
     * @return identificador do usuário
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return nome do usuário
     */
    public String getNome() { return nome; }

    /**
     * @return tipo (perfil) vinculado ao usuário
     */
    public TipoUsuario getTipoUsuario() { return tipoUsuario; }

    /**
     * @param id identificador do usuário
     * @param nome nome exibido ou cadastral
     * @param tipoUsuario perfil associado
     */
    public Usuario(UUID id, String nome, TipoUsuario tipoUsuario) {
        this.id = id;
        this.nome = nome;
        this.tipoUsuario = tipoUsuario;
        validar();
    }

    /**
     * Reaplica as regras de consistência da entidade (nome, tipo, etc.).
     */
    public void validar() {
        UsuarioValidador.validar(this);
    }

    /**
     * @param nome nome atualizado
     * @param tipoUsuario novo tipo associado
     */
    public void atualizar(String nome, TipoUsuario tipoUsuario) {
        this.nome = nome;
        this.tipoUsuario = tipoUsuario;
        validar();
    }
}
