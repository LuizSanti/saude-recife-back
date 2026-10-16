package com.saude.recife.api.domain.exception;

public class CnpjJaCadastradoException extends RuntimeException {

    public CnpjJaCadastradoException(String cnpj) {
        super("CNPJ já cadastrado: " + cnpj);
    }
}