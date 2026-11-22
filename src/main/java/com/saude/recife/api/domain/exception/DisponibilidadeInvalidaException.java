package com.saude.recife.api.domain.exception;

public class DisponibilidadeInvalidaException extends RuntimeException {
    public DisponibilidadeInvalidaException(String mensagem) {
        super(mensagem);
    }
}
