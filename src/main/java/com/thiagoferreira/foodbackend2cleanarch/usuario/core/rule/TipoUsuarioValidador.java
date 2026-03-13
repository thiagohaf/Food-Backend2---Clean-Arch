package com.thiagoferreira.foodbackend2cleanarch.usuario.core.rule;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.util.exception.ValidacaoRegraNegocioException;

import static com.thiagoferreira.foodbackend2cleanarch.util.rule.ValidadorBase.validarCampoObrigatorio;

public class TipoUsuarioValidador {

    private TipoUsuarioValidador() {
        /* esta classe não deve ser instanciada */
    }

    public static void validar(TipoUsuario tipoUsuario) {
        if (tipoUsuario == null) {
            throw new ValidacaoRegraNegocioException("O tipo de usuário não pode ser nulo.");
        }

        validarCampoObrigatorio(tipoUsuario.getNome(), "Nome do Tipo");
    }
}


