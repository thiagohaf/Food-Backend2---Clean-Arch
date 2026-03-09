package com.thiagoferreira.foodbackend2cleanarch.util.exception;

public class ValidacaoRegraNegocioException extends SystemBaseException {
    private final String message;

    @Override
    public String getCode() {
        return "REGRA_DE_NEGOCIO_VIOLADA";
    }

    @Override
    public Integer getHttpStatus() {
        return 422;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public ValidacaoRegraNegocioException(String message) {
        this.message = message;
    }
}
