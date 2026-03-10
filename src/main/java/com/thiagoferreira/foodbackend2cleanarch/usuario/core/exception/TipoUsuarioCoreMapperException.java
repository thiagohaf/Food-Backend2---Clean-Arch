package com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception;

import com.thiagoferreira.foodbackend2cleanarch.util.exception.SystemBaseException;

public class TipoUsuarioCoreMapperException extends SystemBaseException {
    private static final String CODE = "usuario.tipoUsuarioDtoNulo";//NOSONAR
    private static final String MESSAGE = "O input de Tipo de Usuário não pode ser nulo.";//NOSONAR
    private static final Integer HTTP_STATUS = 422;//NOSONAR

    public TipoUsuarioCoreMapperException() {
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
