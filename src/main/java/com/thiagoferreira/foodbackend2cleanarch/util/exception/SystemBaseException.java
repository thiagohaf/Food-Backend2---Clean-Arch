package com.thiagoferreira.foodbackend2cleanarch.util.exception;

public abstract class SystemBaseException extends RuntimeException {
    public abstract String getCode();
    public abstract Integer getHttpStatus();
    @Override
    public abstract String getMessage();
}
