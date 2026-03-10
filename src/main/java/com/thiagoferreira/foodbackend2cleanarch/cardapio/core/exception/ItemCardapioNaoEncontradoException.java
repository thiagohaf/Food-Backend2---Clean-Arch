package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception;

import com.thiagoferreira.foodbackend2cleanarch.util.exception.SystemBaseException;

public class ItemCardapioNaoEncontradoException extends SystemBaseException {

    private static final String CODE = "cardapio.itemCardapioNaoEncontrado";//NOSONAR
    private static final String MESSAGE = "Item do cardápio não encontrado.";//NOSONAR
    private static final Integer HTTP_STATUS = 422;//NOSONAR

    public ItemCardapioNaoEncontradoException() {
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
