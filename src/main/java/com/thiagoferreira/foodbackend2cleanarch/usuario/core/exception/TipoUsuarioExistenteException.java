package com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception;

import com.thiagoferreira.foodbackend2cleanarch.util.exception.SystemBaseException;

public class TipoUsuarioExistenteException extends SystemBaseException {
    private static final String CODE = "usuario.tipoUsuarioJaExiste";//NOSONAR
    private static final String MESSAGE = "Já existe um Tipo de Usuário com este nome.";//NOSONAR
    private static final Integer HTTP_STATUS = 422;//NOSONAR

    public TipoUsuarioExistenteException() { }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public Integer getHttpStatus() {
        return HTTP_STATUS;
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
