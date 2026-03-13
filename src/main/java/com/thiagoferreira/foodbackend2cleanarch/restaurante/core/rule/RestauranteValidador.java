package com.thiagoferreira.foodbackend2cleanarch.restaurante.core.rule;

import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain.Restaurante;
import com.thiagoferreira.foodbackend2cleanarch.util.exception.ValidacaoRegraNegocioException;

import static com.thiagoferreira.foodbackend2cleanarch.util.rule.ValidadorBase.validarCampoObrigatorio;

public class RestauranteValidador {

    private RestauranteValidador() {
        /* esta classe não deve ser instanciada */
    }

    public static void validar(Restaurante restaurante) {
        validarCampoObrigatorio(restaurante.getNome(), "Nome do Restaurante");
        validarCampoObrigatorio(restaurante.getEndereco(), "Endereco");
        validarCampoObrigatorio(restaurante.getTipoCozinha(),  "Tipo de Cozinha");

        if (restaurante.getDonoId() == null) {
            throw new ValidacaoRegraNegocioException("O restaurante deve ter um dono (usuário) associado.");
        }

        validarFormatoHorario(restaurante.getHorarioFuncionamento());
    }

    private static void validarFormatoHorario(String horario) {
        // Regex para o formato HH:mm-HH:mm
        // ^([01]\d|2[0-3]):([0-5]\d) -> Início: 00:00 até 23:59
        // -                          -> Separador literal
        // ([01]\d|2[0-3]):([0-5]\d)$ -> Fim: 00:00 até 23:59
        String regex = "^([01]\\d|2[0-3]):([0-5]\\d)-([01]\\d|2[0-3]):([0-5]\\d)$";

        if (horario == null || horario.isBlank() || !horario.matches(regex)) {
            throw new ValidacaoRegraNegocioException("O horário de funcionamento deve estar no formato HH:mm-HH:mm.");
        }
    }
}
