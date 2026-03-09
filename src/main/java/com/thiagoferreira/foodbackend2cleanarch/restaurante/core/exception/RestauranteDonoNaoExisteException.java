package com.thiagoferreira.foodbackend2cleanarch.restaurante.core.exception;

import com.thiagoferreira.foodbackend2cleanarch.util.exception.SystemBaseException;

public class RestauranteDonoNaoExisteException extends SystemBaseException {
    private static final String CODE = "restaurante.resturanteDonoNaoExiste";//NOSONAR
    private static final String MESSAGE = "O dono informado não existe ou não tem o perfil de Dono de Restaurante.";//NOSONAR
    private static final Integer HTTP_STATUS = 422;//NOSONAR

    public RestauranteDonoNaoExisteException() {
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
