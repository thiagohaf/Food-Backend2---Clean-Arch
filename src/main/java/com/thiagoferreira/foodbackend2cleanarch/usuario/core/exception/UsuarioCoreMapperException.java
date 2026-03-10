package com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception;

import com.thiagoferreira.foodbackend2cleanarch.util.exception.SystemBaseException;

public class UsuarioCoreMapperException extends SystemBaseException {
    private static final String CODE = "usuario.usuarioDtoNulo";//NOSONAR
    private static final String MESSAGE = "O input de Usuário não pode ser nulo.";//NOSONAR
    private static final Integer HTTP_STATUS = 422;//NOSONAR

    public UsuarioCoreMapperException() {
    }

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

