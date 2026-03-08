package com.thiagoferreira.foodbackend2cleanarch.util.rule;

import com.thiagoferreira.foodbackend2cleanarch.util.exception.ValidacaoRegraNegocioException;

public class ValidadorBase {

    private ValidadorBase() {
        throw new UnsupportedOperationException("Esta é uma classe utilitária e não pode ser instanciada.");
    }

    public static void validarCampoObrigatorio(String valor, String nomeCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacaoRegraNegocioException("O campo " + nomeCampo + " não pode ser nulo ou vazio.");
        }
    }
}
