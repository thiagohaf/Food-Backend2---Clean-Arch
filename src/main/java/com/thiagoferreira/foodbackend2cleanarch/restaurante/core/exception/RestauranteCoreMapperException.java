package com.thiagoferreira.foodbackend2cleanarch.restaurante.core.exception;

import com.thiagoferreira.foodbackend2cleanarch.util.exception.SystemBaseException;

public class RestauranteCoreMapperException extends SystemBaseException {
    private static final String CODE = "restaurante.dtoNulo";//NOSONAR
    private static final String MESSAGE = "O input para criação de restaurante não pode ser nulo.";//NOSONAR
    private static final Integer HTTP_STATUS = 422;//NOSONAR

    public RestauranteCoreMapperException() {
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
