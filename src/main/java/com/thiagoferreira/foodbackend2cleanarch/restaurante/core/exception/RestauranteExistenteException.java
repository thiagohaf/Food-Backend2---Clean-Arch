package com.thiagoferreira.foodbackend2cleanarch.restaurante.core.exception;

import com.thiagoferreira.foodbackend2cleanarch.util.exception.SystemBaseException;

public class RestauranteExistenteException extends SystemBaseException {
    private static final String CODE = "restaurante.resturanteJaExiste";//NOSONAR
    private static final String MESSAGE = "Já existe um restaurante com este nome.";//NOSONAR
    private static final Integer HTTP_STATUS = 422;//NOSONAR

    public RestauranteExistenteException() {
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