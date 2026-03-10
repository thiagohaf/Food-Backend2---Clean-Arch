package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception;

import com.thiagoferreira.foodbackend2cleanarch.util.exception.SystemBaseException;

public class ItemCardapioCoreMapperException extends SystemBaseException {
    private static final String CODE = "cardapio.dtoNulo";//NOSONAR
    private static final String MESSAGE = "O input para criação de ItemCardapio não pode ser nulo.";//NOSONAR
    private static final Integer HTTP_STATUS = 422;//NOSONAR

    public ItemCardapioCoreMapperException() {
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
