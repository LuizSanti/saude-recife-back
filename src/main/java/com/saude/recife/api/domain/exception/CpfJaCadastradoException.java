package com.saude.recife.api.domain.exception;

public class CpfJaCadastradoException extends RuntimeException {

    public CpfJaCadastradoException(String cpf) {
        super("CPF já cadastrado: " + cpf);
    }
}