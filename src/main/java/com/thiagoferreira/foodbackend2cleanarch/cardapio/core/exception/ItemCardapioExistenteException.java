package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception;

import com.thiagoferreira.foodbackend2cleanarch.util.exception.SystemBaseException;

public class ItemCardapioExistenteException extends SystemBaseException {
    private static final String CODE = "cardapio.itemCardapioJaExistente";//NOSONAR
    private static final String MESSAGE = "Este restaurante já possui um item com este nome no cardápio.";//NOSONAR
    private static final Integer HTTP_STATUS = 422;//NOSONAR

    public ItemCardapioExistenteException() {} //NOSONAR

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
