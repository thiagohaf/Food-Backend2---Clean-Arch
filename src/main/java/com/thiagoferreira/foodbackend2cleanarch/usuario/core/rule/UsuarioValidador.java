package com.thiagoferreira.foodbackend2cleanarch.usuario.core.rule;

import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Usuario;
import com.thiagoferreira.foodbackend2cleanarch.util.exception.ValidacaoRegraNegocioException;

import static com.thiagoferreira.foodbackend2cleanarch.util.rule.ValidadorBase.validarCampoObrigatorio;

public class UsuarioValidador {

    private UsuarioValidador() {
        /* esta classe não deve ser instanciada */
    }

    public static void validar(Usuario usuario) {
        if (usuario == null) {
            throw new ValidacaoRegraNegocioException("O usuário não pode ser nulo.");
        }

        validarCampoObrigatorio(usuario.getNome(), "Nome do Usuário");

        if (usuario.getTipoUsuario() == null) {
            throw new ValidacaoRegraNegocioException("O usuário deve obrigatoriamente estar associado a um Tipo de Usuário.");
        }
    }
}
