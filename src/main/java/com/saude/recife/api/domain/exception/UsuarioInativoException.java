package com.saude.recife.api.domain.exception;

public class UsuarioInativoException extends RuntimeException {

    public UsuarioInativoException() {
        super("Usuario inativo");
    }
}
