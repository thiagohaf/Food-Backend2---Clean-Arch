package com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.rule.TipoUsuarioValidador;

import java.util.UUID;

/**
 * Entidade de domínio que representa um tipo (perfil/categoria) de usuário, usado para classificar permissões ou papéis.
 */
public class TipoUsuario {
    private UUID id;
    private String nome;

    /**
     * @param id identificador do tipo
     * @param nome rótulo único do tipo no catálogo
     */
    public TipoUsuario(UUID id, String nome) {
        this.id = id;
        this.nome = nome;
        validar();
    }

    private void validar() {
        TipoUsuarioValidador.validar(this);
    }

    /**
     * @param nome novo nome do tipo
     */
    public void atualizar(String nome) {
        this.nome = nome;
        validar();
    }

    /**
     * @return identificador do tipo
     */
    public UUID getId() { return id; }

    /**
     * @return nome do tipo
     */
    public String getNome() { return nome; }
}
