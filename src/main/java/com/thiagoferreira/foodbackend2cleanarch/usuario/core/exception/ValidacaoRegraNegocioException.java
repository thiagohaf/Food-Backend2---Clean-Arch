package com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception;

public class ValidacaoRegraNegocioException extends SystemBaseException {
    private static final long serialVersionUID = 1L;

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
