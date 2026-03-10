package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception;

import com.thiagoferreira.foodbackend2cleanarch.util.exception.SystemBaseException;

public class RestauranteNaoEncontradoException extends SystemBaseException {
    private static final String CODE = "cardapio.restauranteNaoEncontrado";//NOSONAR
    private static final String MESSAGE = "Não é possível cadastrar o item. O restaurante informado não existe.";//NOSONAR
    private static final Integer HTTP_STATUS = 422;//NOSONAR

    public RestauranteNaoEncontradoException() {
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
