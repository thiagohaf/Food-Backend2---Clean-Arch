package com.thiagoferreira.foodbackend2cleanarch.usuario.core.rule;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Restaurante;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.ValidacaoRegraNegocioException;

import java.time.LocalTime;

import static com.thiagoferreira.foodbackend2cleanarch.usuario.core.rule.ValidadorBase.validarCampoObrigatorio;

public class RestauranteValidador {

    public static void validar(Restaurante restaurante) {
        validarCampoObrigatorio(restaurante.getNome(), "Nome do Restaurante");
        validarCampoObrigatorio(restaurante.getEndereco(), "Endereco");
        validarCampoObrigatorio(restaurante.getTipoCozinha(),  "Tipo de Cozinha");
        validarCampoObrigatorio(restaurante.getHorarioFuncionamento(), "Horario de Funcionamento");

        if (restaurante.getDonoId() == null) {
            throw new ValidacaoRegraNegocioException("O restaurante deve ter um dono (usuário) associado.");
        }

        validarFormatoHorario(restaurante.getHorarioFuncionamento());
    }

    private static void validarFormatoHorario(String horario) {
        try {
            String[] partes = horario.split("-");
            if (partes.length != 2) throw new Exception();
            LocalTime.parse(partes[0].trim());
            LocalTime.parse(partes[1].trim());
        } catch (Exception e) {
            throw new ValidacaoRegraNegocioException("Formato de horário inválido. Use 'HH:mm - HH:mm'.");
        }
    }
}
