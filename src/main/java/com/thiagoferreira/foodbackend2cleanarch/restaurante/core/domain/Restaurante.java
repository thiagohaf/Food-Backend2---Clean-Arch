package com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain;

import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.rule.RestauranteValidador;

import java.util.UUID;

/**
 * Entidade de domínio do restaurante: identificação, localização, tipo de culinária e vínculo obrigatório
 * com o usuário dono ({@code donoId}) para garantir rastreabilidade de propriedade no negócio.
 */
public class Restaurante {
    private UUID id;
    private String nome;
    private String endereco;
    private String tipoCozinha;
    private String horarioFuncionamento;
    private UUID donoId;

    /**
     * @return identificador do restaurante
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return nome fantasia ou comercial
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return endereço físico utilizado na regra de unicidade nome+endereço
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * @return categoria culinária (texto de negócio)
     */
    public String getTipoCozinha() {
        return tipoCozinha;
    }

    /**
     * @return descrição textual do horário de funcionamento
     */
    public String getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    /**
     * @return identificador do usuário proprietário
     */
    public UUID getDonoId() {
        return donoId;
    }

    /**
     * @param id identificador do restaurante
     * @param nome nome do estabelecimento
     * @param endereco endereço
     * @param tipoCozinha tipo de cozinha
     * @param horarioFuncionamento horário de funcionamento
     * @param donoId usuário dono cadastrado no sistema
     */
    public Restaurante(UUID id, String nome, String endereco, String tipoCozinha, String horarioFuncionamento, UUID donoId) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha;
        this.horarioFuncionamento = horarioFuncionamento;
        this.donoId = donoId;

        validar();
    }

    /**
     * Reexecuta validações de invariantes do restaurante.
     */
    public void validar() {
        RestauranteValidador.validar(this);
    }

    /**
     * @param nome nome atualizado
     * @param endereco endereço atualizado
     * @param tipoCozinha tipo de cozinha atualizado
     * @param horarioFuncionamento horário atualizado (identificação do dono não muda)
     */
    public void atualizar(String nome, String endereco, String tipoCozinha, String horarioFuncionamento) {
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha;
        this.horarioFuncionamento = horarioFuncionamento;

        validar();
    }
}
